package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
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
    String PATH = "payments_modes";

    @Query("SELECT l from PaymentMode l left join l.payments")
    @Override
    List<PaymentMode> findAll();


    @Query("SELECT l from PaymentMode l " +
            "left join l.payments " +
            "where lower(l.mode) = lower(:mode) " +
            "")
    Optional<PaymentMode> findByMode(@Param("mode") String mode);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends PaymentMode> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends PaymentMode> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<PaymentMode> findAllById(Iterable<Long> iterable);
}
