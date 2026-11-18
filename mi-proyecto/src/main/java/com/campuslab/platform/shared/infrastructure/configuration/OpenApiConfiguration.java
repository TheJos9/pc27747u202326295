package com.campuslab.platform.shared.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI campusLabOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("CampusLab Equipment Reservations API")
                        .description("RESTful API for university laboratory equipment reservation management.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Jose Huayra")
                                .email("u202326295@upc.edu.pe")));
    }
}
