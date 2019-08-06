package tech.omeganumeric.api.ubereats.services.otp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.rest.dto.EmailDTO;
import tech.omeganumeric.api.ubereats.services.EmailService;
import tech.omeganumeric.api.ubereats.services.entities.PhoneService;

import java.util.ArrayList;
import java.util.List;

@Description(value = "Service responsible for handling OTP related functionality.")
@Service
public class OTPService {

    private final Logger LOGGER = LoggerFactory.getLogger(OTPService.class);

    private final OTPGenerator otpGenerator;
    private final EmailService emailService;
    private final PhoneService phoneService;

    /**
     * Constructor dependency injector
     *
     * @param phoneService - phone service dependency
     * @param otpGenerator - otpGenerator dependency
     * @param emailService - email service dependency
     * @param phoneService
     */
    public OTPService(OTPGenerator otpGenerator, EmailService emailService, PhoneService phoneService) {
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    /**
     * Method for generate OTP number
     *
     * @param key - provided key (username in this case)
     * @return boolean value (true|false)
     */
    public boolean generateOtp(String key) {
        // generate otp
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1) {
            LOGGER.error("OTP generator is not working...");
            return false;
        }

        LOGGER.info("Generated OTP: {}", otpValue);

        // fetch user e-mail from database
        String userEmail = "test@test.com"; //phoneService.findPhoneByNumber(key).getUser().getEmail();
        List<String> recipients = new ArrayList<>();
        recipients.add(userEmail);

        // generate emailDTO object
        EmailDTO emailDTO = EmailDTO.builder()
                .subject("Spring Boot OTP Password.")
                .body(
                        String.format("OTP Password: %s", otpValue)
                )
                .recipients(recipients)
                .build();

        // send generated e-mail
        return emailService.sendSimpleMessage(emailDTO);
    }

    /**
     * Method for validating provided OTP
     *
     * @param key       - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
    public Boolean validateOTP(String key, Integer otpNumber) {
        // get OTP from cache
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP.equals(otpNumber)) {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}
