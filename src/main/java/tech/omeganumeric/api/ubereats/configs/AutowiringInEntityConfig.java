package tech.omeganumeric.api.ubereats.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.omeganumeric.api.ubereats.providers.ApplicationContextProvider;

@Configuration
public class AutowiringInEntityConfig {

    @Bean
    public static ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }
}
