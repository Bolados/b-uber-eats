package tech.omeganumeric.api.ubereats.services.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.domains.entities.Phone;
import tech.omeganumeric.api.ubereats.repositories.PhoneRepository;

@Slf4j
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;


    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Phone findPhoneByNumber(String number) {
        return phoneRepository.findByNumber(number).orElseThrow(
                () -> new IllegalArgumentException("Not found phone number :" + number)
        );
    }
}
