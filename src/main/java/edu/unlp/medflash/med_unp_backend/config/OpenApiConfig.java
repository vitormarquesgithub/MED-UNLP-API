package edu.unlp.medflash.med_unp_backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MED-UNLP Backend API")
                .version("1.0")
                .description("REST API for the MED-UNLP study app")
                .termsOfService("https://med-unlp.edu.ar/terms")
                .contact(new Contact()
                    .name("API Support")
                    .url("https://med-unlp.edu.ar/contact")
                    .email("dev@med-unlp.edu.ar"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")))
            .externalDocs(new ExternalDocumentation()
                .description("MED-UNLP Documentation")
                .url("https://med-unlp.edu.ar/docs"));
    }
    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("med-unlp-public")
            .pathsToMatch("/api/**")
            .build();
    }
}