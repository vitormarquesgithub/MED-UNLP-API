package edu.unlp.medflash.med_unp_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import edu.unlp.medflash.med_unp_backend.Enum.Role;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Schema(description = "Entity that represents a user in the system")
public class User extends AbstractEntity implements UserDetails {
    
    @Schema(description = "Automatically generated user ID", 
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Schema(description = "User email", example = "user@example.com")
    @Column(unique = true, nullable = false)
    private String email;
    
    @Schema(description = "Encrypted password", example = "$2a$10$N9qo8uLOickgx2ZMRZoMy")
    @Column(nullable = false)
    private String password;
    
    @Schema(description = "User Role", example = "ADMIN")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    @Schema(description = "User's full name", example = "John Doe")
    @Column(nullable = false)
    private String name;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Schema(name = "UserRequest", description = "User data for creation or update")
    public static class Request {
        @Schema(description = "User email", example = "user@example.com", required = true)
        public String email;
        
        @Schema(description = "Password", example = "securepassword", required = true)
        public String password;
        
        @Schema(description = "User Role", example = "ADMIN", required = true)
        public Role role;
        
        @Schema(description = "User's full name", example = "John Doe", required = true)
        public String name;
    }
}