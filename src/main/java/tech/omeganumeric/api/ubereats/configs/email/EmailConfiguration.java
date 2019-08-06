package tech.omeganumeric.api.ubereats.configs.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    private final EmailProviderConfiguration provider;

    public EmailConfiguration(EmailProviderConfiguration provider) {
        this.provider = provider;
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(provider.getHost());
        javaMailSender.setPort(provider.getPort());

        javaMailSender.setUsername(provider.getUsername());
        javaMailSender.setPassword(provider.getPassword());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", provider.getDebug().toString());

        return javaMailSender;
    }

}
