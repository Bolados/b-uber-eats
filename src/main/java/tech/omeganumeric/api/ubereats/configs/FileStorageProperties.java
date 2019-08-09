package tech.omeganumeric.api.ubereats.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage.file")
public class FileStorageProperties {

    private String path;

    private String extensions;
}
