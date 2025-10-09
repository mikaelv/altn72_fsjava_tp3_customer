package org.efrei.altn72.customer.resource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@OpenAPIDefinition(
        info = @Info(
                title = "Customer API",
                version = "1.0.0",
                description = "REST API for managing customers"
        ),
        servers = @io.swagger.v3.oas.annotations.servers.Server(url = "/customer")
)
public class CustomerApplication extends Application {

}