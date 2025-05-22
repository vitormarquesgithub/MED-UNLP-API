package edu.unlp.medflash.med_unp_backend.controller;

import edu.unlp.medflash.med_unp_backend.Enum.Role;
import edu.unlp.medflash.med_unp_backend.model.User;
import edu.unlp.medflash.med_unp_backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) { 
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all users", description = "Returns a list of all registered users")
    @ApiResponse(responseCode = "200", description = "User list returned successfully")
    public ResponseEntity<List<User>> list() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    @ApiResponse(responseCode = "200", description = "User found and returned")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> get(@PathVariable UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided data. ID is automatically generated.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.Request.class)))
    public ResponseEntity<User> create(@RequestBody User newUser) {
        newUser.setId(null);
        
        if (repository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        
        LocalDateTime now = LocalDateTime.now();
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (newUser.getRole() == null) {
            newUser.setRole(Role.ROLE_CUSTOMER);
        }

        User savedUser = repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Update a user", description = "Updates an existing user with the provided data. ID in path is used, request body ID is ignored.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.Request.class)))
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody User updatedUser) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        if (!existingUser.getEmail().equals(updatedUser.getEmail()) && 
            repository.findByEmail(updatedUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        User savedUser = repository.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Partially update a user", description = "Updates specific fields of an existing user. ID in path is used, request body ID is ignored.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> partialUpdate(@PathVariable UUID id, @RequestBody User partialUser) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        if (partialUser.getName() != null) {
            existingUser.setName(partialUser.getName());
        }
        if (partialUser.getEmail() != null && !partialUser.getEmail().equals(existingUser.getEmail())) {
            if (repository.findByEmail(partialUser.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
            }
            existingUser.setEmail(partialUser.getEmail());
        }
        if (partialUser.getRole() != null) {
            existingUser.setRole(partialUser.getRole());
        }
        if (partialUser.getPassword() != null && !partialUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(partialUser.getPassword()));
        }

        existingUser.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = repository.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Delete a user", description = "Deletes an existing user by their ID")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}