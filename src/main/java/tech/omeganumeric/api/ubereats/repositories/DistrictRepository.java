package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.District;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = DistrictRepository.PATH,
        path = DistrictRepository.PATH,
        exported = true
)
public interface DistrictRepository extends MetaRepository<District, Long> {
    String PATH = "districts";

    @Query("SELECT l from District l " +
            "left join fetch  l.department " +
            "left join fetch  l.towns"
    )
    @Override
    List<District> findAll();


    @Query("SELECT l from District l " +
            "left join fetch  l.department " +
            "left join fetch  l.towns " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<District> findByName(@Param("name") String name);

    @Query("SELECT l from District l " +
            "left join fetch  l.department " +
            "left join fetch  l.towns " +
            "where lower(l.code) = lower(:code) " +
            "")
    Optional<District> findByCode(@Param("code") String code);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends District> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends District> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<District> findAllById(Iterable<Long> iterable);
}
