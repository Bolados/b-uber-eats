package tech.omeganumeric.api.ubereats.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FileStorageProperties {

    @Value("${storage.file.path }")
    private String path;

}
