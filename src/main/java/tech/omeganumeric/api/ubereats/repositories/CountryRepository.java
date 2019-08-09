package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Country;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = CountryRepository.PATH,
        path = CountryRepository.PATH,
        exported = true
)
public interface CountryRepository extends MetaRepository<Country, Long> {
    String PATH = "countries";

    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments"
    )
    @Override
    List<Country> findAll();


    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Country> findByName(@Param("name") String name);

    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments " +
            "where lower(l.code2) = lower(:code2) " +
            "")
    Optional<Country> findByCode2(@Param("code2") String code2);

    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments " +
            "where lower(l.code3) = lower(:code3) " +
            "")
    Optional<Country> findByCode3(@Param("code3") String code3);

    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments " +
            "where lower(l.phoneCode) = lower(:phoneCode) " +
            "")
    Optional<Country> findByPhoneCode(@Param("phoneCode") String phoneCode);

    @Query("SELECT l from Country l " +
            "left join l.region " +
            "left join l.departments " +
            "where lower(l.domain) = lower(:domain) " +
            "")
    Optional<Country> findByDomain(@Param("domain") String domain);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Country> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Country> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Country> findAllById(Iterable<Long> iterable);
}
