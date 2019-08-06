package tech.omeganumeric.api.ubereats.webservice.security.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.AuthorityApiRepository;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.UserApiRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

@Slf4j
@Component
public class DataSecurityInitializer implements CommandLineRunner {

    private final UserApiRepository userApiRepository;
    private final AuthorityApiRepository authorityApiRepository;
    private final PasswordEncoder passwordEncoder;
    private final Connection datasource;

    public DataSecurityInitializer(
            UserApiRepository userRepository,
            AuthorityApiRepository authorityApiRepository,
            PasswordEncoder passwordEncoder,
            Connection datasource) {
        this.userApiRepository = userRepository;
        this.authorityApiRepository = authorityApiRepository;
        this.passwordEncoder = passwordEncoder;
        this.datasource = datasource;
    }


    @Override
    public void run(String... args) throws Exception {

        File file = ResourceUtils
                .getFile("classpath:static/scripts/api/data.sql");

        ScriptRunner scriptRunner = new ScriptRunner(
                datasource
        );
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));
//
//
//        final String USER_ROLE = "ROLE_USER";
//        final String ADMIN_ROLE = "ADMIN_USER";
//
//        AuthorityApi userRole = AuthorityApi.builder()
//                .name(USER_ROLE)
//                .description("User role")
//                .users(
//                        new HashSet<>(
//                                Arrays.asList(
//                                        UserApi.builder()
//                                                .firstName("user")
//                                                .lastName("user")
//                                                .username("user")
//                                                .email("user@api.api")
//                                                .password(passwordEncoder.encode("user"))
//                                                .build(),
//                                        UserApi.builder()
//                                                .firstName("admin")
//                                                .lastName("admin")
//                                                .username("admin")
//                                                .email("admin@api.api")
//                                                .password(passwordEncoder.encode("admin"))
//                                                .build()
//                                )
//                        )
//                )
//                .build();
//
//        userRole = authorityApiRepository.save(userRole);
//
//        UserApi adminApi = userApiRepository
//                .findByUsernameOrEmail("admin", null)
//                .get();
//
//        log.warn(adminApi.toString());
//
////        UserApi u = new UserApi();
////        u.setId(adminApi.getId());
////        u.set
//        AuthorityApi adminRole = AuthorityApi.builder()
//                .name(ADMIN_ROLE)
//                .description("Admin role")
//                .build();
//
//        adminRole.setUsers(
//                new HashSet<UserApi>(
//                        Arrays.asList(adminApi)
//                )
//        );
//
//        authorityApiRepository.save(userRole);
//
    }
}
