package tech.omeganumeric.api.ubereats.configs.sql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.omeganumeric.api.ubereats.domains.entities.Region;
import tech.omeganumeric.api.ubereats.domains.entities.Role;
import tech.omeganumeric.api.ubereats.repositories.RegionRepository;
import tech.omeganumeric.api.ubereats.repositories.RoleRepository;
import tech.omeganumeric.api.ubereats.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;


    DataInitializer(
            RoleRepository roleRepository,
            RegionRepository regionRepository,
            UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // roles
        final List<Role> roles = Stream.of(
                new Role("SUPER_ADMIN", "Super Admin", new HashSet<>()),
                new Role("ADMIN", "Admin", new HashSet<>()),
                new Role("ADMIN_RESTAURANT", "Chef of restaurant", new HashSet<>()),
                new Role("EATER", "Eater", new HashSet<>()),
                new Role("DRIVER", "Driver", new HashSet<>())
        ).collect(Collectors.toList());
        this.roleRepository.saveAll(roles);


        // regions
        final List<Region> regions = Stream.of(
                Region.builder()
                        .code("AF")
                        .name("Africa")
//                        .countries(new HashSet<>())
                        .build(),
                Region.builder()
                        .code("EU")
                        .name("Europe")
//                        .countries(new HashSet<>())
                        .build(),
                Region.builder().code("NA").name("North America")
//                        .countries(new HashSet<>())
                        .build(),
                Region.builder().code("OC").name("Oceanic")
//                        .countries(new HashSet<>())
                        .build(),
                Region.builder().code("SA").name("South America")
//                        .countries(new HashSet<>())
                        .build()
        ).collect(Collectors.toList());
        this.regionRepository.saveAll(regions);
    }
}
