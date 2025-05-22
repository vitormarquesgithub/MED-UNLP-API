package edu.unlp.medflash.med_unp_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Schema<String> emailSchema = new StringSchema().example("user@example.com");
        Schema<String> passwordSchema = new StringSchema().example("securepassword");
        Schema<String> roleSchema = new StringSchema().example("ADMIN");
        Schema<String> nameSchema = new StringSchema().example("John Doe");

        Map<String, Schema<?>> properties = Map.of(
            "email", emailSchema,
            "password", passwordSchema,
            "role", roleSchema,
            "name", nameSchema
        );

        Schema<Object> userRequestSchema = new Schema<>()
            .type("object")
            .properties(properties);

        final String securitySchemeName = "Bearer Auth";
        
        return new OpenAPI()
            .components(new Components()
                .addSchemas("UserRequest", userRequestSchema)
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .info(createApiInfo())
            .externalDocs(createExternalDocs());
    }

    private Info createApiInfo() {
        return new Info()
            .title("MED-UNLP API")
            .version("1.0")
            .description("MED-UNLP API is a RESTful backend for the MED-UNLP study app. It provides user authentication, role-based access control, and CRUD endpoints for academic years, curricula, subjects, modules, and study methods (flashcards, summaries, mind maps, quizzes). Built with Spring Boot, JPA, Flyway, PostgreSQL, and Docker.")
            .contact(new Contact()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0"));
    }

    private ExternalDocumentation createExternalDocs() {
        return new ExternalDocumentation()
            .description("MED-UNLP Documentation")
            .url("https://github.com/vitormarquesgithub/MED-UNLP-API");
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("med-unlp-public")
            .pathsToMatch("/api/**")
            .build();
    }
}