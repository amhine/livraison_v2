package com.livraison.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Livraison API",
        version = "v2",
        description = "Documentation OpenAPI pour les endpoints de l'application Livraison",
        contact = @Contact(name = "Equipe Livraison", email = "support@example.com"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
    ),
    servers = {
        @Server(url = "http://localhost:${server.port}", description = "Local"),
        @Server(url = "http://127.0.0.1:${server.port}", description = "Loopback")
    }
)
public class OpenApiConfig {
}
