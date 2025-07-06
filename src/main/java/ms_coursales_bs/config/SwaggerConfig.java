package ms_coursales_bs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Course Sales BS API")
                        .version("1.0")
                        .description("API de lógica de negocio para ventas de cursos, incluyendo integración con pasarela de pagos."));
    }
}