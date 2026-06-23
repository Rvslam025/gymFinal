package com.prueba.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cliente Service API")
                        .version("1.0")
                        .description("Microservicio encargado de gestionar los clientes del gimnasio."));
    }
}
