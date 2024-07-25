package et.store.api_demo.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // Habilita la configuración MVC (si no estás usando Spring Boot autoconfigurado)
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:4200") // Reemplaza con el puerto correcto de tu frontend Angular
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}