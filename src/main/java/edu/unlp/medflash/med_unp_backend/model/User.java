package edu.unlp.medflash.med_unp_backend.model;

import edu.unlp.medflash.med_unp_backend.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Schema(description = "Entity that represents a user in the system")
public class User extends AbstractEntity {
    
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
    
    @Schema(description = "User's full name", example = "Vitor Marques")
    @Column(nullable = false)
    private String name;
}