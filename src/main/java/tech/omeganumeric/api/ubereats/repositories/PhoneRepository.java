package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Phone;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = PhoneRepository.PATH,
        path = PhoneRepository.PATH,
        exported = true
)
public interface PhoneRepository extends MetaRepository<Phone, Long> {
    String PATH = "phones";

    @Query("select l from Phone l " +
            "left join fetch l.user " +
            "where l.number = :number ")
    Optional<Phone> findByNumber(String number);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Phone> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Phone> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Phone> findAllById(Iterable<Long> iterable);
}
