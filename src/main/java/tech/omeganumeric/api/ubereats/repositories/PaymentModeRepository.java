package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.PaymentMode;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = PaymentModeRepository.PATH,
        path = PaymentModeRepository.PATH,
        exported = true
)
public interface PaymentModeRepository extends MetaRepository<PaymentMode, Long> {
    String PATH = "payments/modes";

    @Query("SELECT l from PaymentMode l left join l.payments")
    @Override
    List<PaymentMode> findAll();


    @Query("SELECT l from PaymentMode l " +
            "left join l.payments " +
            "where lower(l.mode) = lower(:mode) " +
            "")
    Optional<PaymentMode> findByMode(@Param("mode") String mode);
}
