package tech.omeganumeric.api.ubereats.controllers;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.omeganumeric.api.ubereats.configs.AppConfig;
import tech.omeganumeric.api.ubereats.exceptions.ControllerException;
import tech.omeganumeric.api.ubereats.exceptions.OTPException;
import tech.omeganumeric.api.ubereats.services.otp.OTPService;

import java.net.URI;

/**
 * @author BSCAKO
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = OTPResourceController.PATH)
@Api(
        value = OTPResourceController.PATH,
        consumes = AppConfig.API_CONSUMES,
        produces = AppConfig.API_PRODUCES,
        tags = {"Authentication "},
        description = " All services relating to authentication users in oubereats"
)
@PreAuthorize("hasRole('ROLE_USER')")
public class OTPResourceController extends ControllerException {


    public static final String PATH = "auth/otp";
    public static final String GENERATE = "generate";
    public static final String VALIDATE = "validate";

    private final OTPService otpService;


    public OTPResourceController(OTPService otpService) {
        this.otpService = otpService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public ResponseEntity generateOTP(@RequestParam String number) {
        boolean isGenerated = otpService.generateOtp(number);
        if (!isGenerated) {
            log.warn("Otp exception for number  {} - {}", number);
            throw new OTPException("Otp exception for number " + number);
        }

        log.info("OTP successfully generated. Please check your phone!");

        // success message
        String message = "OTP successfully generated. Please check your phone!";

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(message);
    }

    @RequestMapping(
            method = RequestMethod.POST
    )
    public ResponseEntity<Object> validateOTP(@RequestParam String number, @RequestParam Integer otp) {
        // validate provided OTP.
        Boolean isValid = otpService.validateOTP(number, otp);
        if (!isValid) {
            log.warn("Otp exception for number  {} - Not valid Otp {}", number, otp);
            throw new OTPException("Otp exception for number " + number);
        }

        log.info("Entered OTP is valid!");

        // success message
        String message = "Entered OTP is valid!";

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(message);
    }
}
