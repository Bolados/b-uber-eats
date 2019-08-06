package tech.omeganumeric.api.ubereats.services.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.repositories.UserRepository;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
