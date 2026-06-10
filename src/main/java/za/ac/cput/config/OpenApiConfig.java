package za.ac.cput.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI metadata for the Swagger UI, available at /swagger-ui.html
 * (raw spec at /v3/api-docs).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI buzzCarSalesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Buzz Car Sales API")
                        .description("REST API for managing a multi-branch car dealership "
                                + "franchise: inventory, sales, customers, employees, "
                                + "SMS confirmations and PDF reports.")
                        .version("1.0")
                        .contact(new Contact().name("Buzz Car Sales").email("support@buzz.co.za"))
                        .license(new License().name("CPUT")));
    }
}
