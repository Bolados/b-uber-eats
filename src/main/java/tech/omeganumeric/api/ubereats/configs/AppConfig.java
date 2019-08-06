package tech.omeganumeric.api.ubereats.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
public class AppConfig {

    public static final String API_PRODUCES = "Application/Json";
    public static final String API_CONSUMES = API_PRODUCES;

    @Bean
    public ObjectMapper objectMapperBean() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }


}
