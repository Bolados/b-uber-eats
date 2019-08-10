package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Payment;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = PaymentRepository.PATH,
        path = PaymentRepository.PATH,
        exported = true
)
public interface PaymentRepository extends MetaRepository<Payment, Long> {
    String PATH = "Payments";

    @Query("SELECT l from Payment l " +
            "left join l.orders "
    )
    @Override
    List<Payment> findAll();


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Payment> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Payment> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Payment> findAllById(Iterable<Long> iterable);
}
