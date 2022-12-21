package appLibrary.configs.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String API_KEY = "Bearer Token";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecuritySchema()))
                .info(new Info().title("Library Rest API")
                        .description("The project "))
                .security(List.of(new SecurityRequirement().addList("Bearer Token")));
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization")
                .description("Just put the token")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer");
    }
}
