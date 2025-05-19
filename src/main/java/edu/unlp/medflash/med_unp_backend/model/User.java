package edu.unlp.medflash.med_unp_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
@Schema(description = "Entidade que representa um usuário no sistema")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do usuário", example = "1")
    private Long id;
    
    @Schema(description = "Email do usuário", example = "user@example.com")
    private String email;
    
    @Schema(description = "Senha criptografada", example = "$2a$10$N9qo8uLOickgx2ZMRZoMy...")
    private String password;
    
    @Schema(description = "Papel do usuário", example = "ADMIN")
    private String role;
    
    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String name;
}