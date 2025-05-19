package edu.unlp.medflash.med_unp_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import edu.unlp.medflash.med_unp_backend.model.User;
import edu.unlp.medflash.med_unp_backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints para gerenciamento de usuários")
public class UserController {
    private final UserRepository repo;
    
    public UserController(UserRepository repo) { 
        this.repo = repo; 
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os usuários",
               description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public List<User> list() { 
        return repo.findAll(); 
    }
}