package tech.omeganumeric.api.ubereats.webservice.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.AuthorityApi;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.UserApi;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.AuthorityApiRepository;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.UserApiRepository;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.UserApiDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserApiService {

    private final UserApiRepository userApiRepository;
    private final AuthorityApiRepository authorityApiRepository;
    private final PasswordEncoder passwordEncoder;

    public UserApiService(UserApiRepository userApiRepository, AuthorityApiRepository authorityApiRepository, PasswordEncoder passwordEncoder) {
        this.userApiRepository = userApiRepository;
        this.authorityApiRepository = authorityApiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserApi> findAll() {
        return userApiRepository.findAll();
    }

    public UserApi findByUsername(String username) {
        return userApiRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new
                        UsernameNotFoundException("Not found user with username : " + username));

    }


    public UserApi convertDtoToEntity(UserApiDto dto) {
        UserApi user = UserApi.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authorities(
                        dto.getAuthorities().stream()
                                .map(authority -> authorityApiRepository.findFirstByName(authority))
                                .collect(Collectors.toSet())
                )
                .build();
        return user;
    }

    public UserApiDto convertEntityToDto(UserApi entity) {
        UserApiDto user = UserApiDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .authorities(
                        entity.getAuthorities().stream()
                                .map(AuthorityApi::getName)
                                .collect(Collectors.toSet())
                )
                .build();
        return user;
    }


    public UserApiDto save(UserApiDto dto) {
        return convertEntityToDto(userApiRepository.save(convertDtoToEntity(dto)));
    }

    public UserApiDto update(String username, UserApiDto dto) {
        UserApi found = findByUsername(username);
        UserApi userDto = convertDtoToEntity(dto);
        userDto.setId(found.getId());
        userDto.setVersion(found.getVersion());
        return convertEntityToDto(userApiRepository.save(userDto));
    }

    public void delete(String username) {
        UserApi user = userApiRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username : " + username));
        userApiRepository.delete(user);
    }


}
