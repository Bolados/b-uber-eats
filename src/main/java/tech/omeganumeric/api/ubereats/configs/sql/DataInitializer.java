package tech.omeganumeric.api.ubereats.configs.sql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.omeganumeric.api.ubereats.domains.entities.*;
import tech.omeganumeric.api.ubereats.repositories.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PaymentModeRepository paymentModeRepository;
    private final PhoneRepository phoneRepository;
    private final CountryRepository countryRepository;




    DataInitializer(
            RoleRepository roleRepository,
            RegionRepository regionRepository,
            UserRepository userRepository,
            MediaRepository mediaRepository,
            OrderStatusRepository orderStatusRepository,
            PaymentModeRepository paymentModeRepository,
            PhoneRepository phoneRepository,
            CountryRepository countryRepository

    ) {
        this.roleRepository = roleRepository;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.paymentModeRepository = paymentModeRepository;
        this.phoneRepository = phoneRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        // phones
        final List<Phone> phones = Stream.of(
                Phone.builder()
                        .number("78985612")
                        .user(null)
                        .restaurant(null)
                        .build()

        ).collect(Collectors.toList());
        this.phoneRepository.saveAll(phones);

        // paymment mode
        final List<PaymentMode> paymentModes = Stream.of(
                PaymentMode.builder()
                        .mode("CARD_VISA")
                        .description("")
                        .payments(new HashSet<>())
                        .build(),
                PaymentMode.builder()
                        .mode("CASH")
                        .description("")
                        .payments(new HashSet<>())
                        .build(),
                PaymentMode.builder()
                        .mode("PAY_PAL")
                        .description("")
                        .payments(new HashSet<>())
                        .build(),
                PaymentMode.builder()
                        .mode("MOBILE_BANK")
                        .description("")
                        .payments(new HashSet<>())
                        .build()

        ).collect(Collectors.toList());
        this.paymentModeRepository.saveAll(paymentModes);

        // orderstatus
        final List<OrderStatus> orderStatuses = Stream.of(
                OrderStatus.builder()
                        .status("DELIVERED")
                        .description("")
                        .orders(new HashSet<>())
                        .build(),
                OrderStatus.builder()
                        .status("PENDING")
                        .description("")
                        .orders(new HashSet<>())
                        .build(),
                OrderStatus.builder()
                        .status("ACCEPTED")
                        .description("")
                        .orders(new HashSet<>())
                        .build(),
                OrderStatus.builder()
                        .status("ON_ROAD")
                        .description("")
                        .orders(new HashSet<>())
                        .build()
        ).collect(Collectors.toList());
        this.orderStatusRepository.saveAll(orderStatuses);

        // roles
        final List<Role> roles = Stream.of(
                Role.builder().name("SUPER_ADMIN").description("Super Admin").users(new HashSet<>()).build(),
                Role.builder().name("ADMIN").description("Admin").users(new HashSet<>()).build(),
                Role.builder().name("ADMIN_RESTAURANT").description("Admin Restaurant").users(new HashSet<>()).build(),
                Role.builder().name("EATER").description("Eater").users(new HashSet<>()).build(),
                Role.builder().name("DRIVER").description("Driver").users(new HashSet<>()).build()
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

        Region region = this.regionRepository.findByCode("AF")
                .orElseThrow(() -> new IllegalArgumentException("data initialiser countries"));

        // phones
        final List<Country> countries = Stream.of(
                Country.builder()
                        .name("Benin")
                        .code2("BJ")
                        .code3("BJ")
                        .density(12.0d)
                        .population(1L)
                        .phoneCode("229")
                        .region(region)
                        .departments(new HashSet<>())
                        .variant("danxome")
                        .build(),
                Country.builder()
                        .name("nigeria")
                        .code2("NG")
                        .code3(null)
                        .density(null)
                        .population(null)
                        .phoneCode(null)
                        .region(region)
                        .departments(new HashSet<>())
                        .variant(null)
                        .build()

        ).collect(Collectors.toList());
        this.countryRepository.saveAll(countries);
    }
}
