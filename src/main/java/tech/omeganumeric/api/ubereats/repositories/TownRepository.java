package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Town;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = TownRepository.PATH,
        path = TownRepository.PATH,
        exported = true
)
public interface TownRepository extends MetaRepository<Town, Long> {
    String PATH = "towns";

    @Query("SELECT l from Town l " +
            "left join fetch  l.district " +
            "left join fetch  l.addresses"
    )
    @Override
    List<Town> findAll();


    @Query("SELECT l from Town l " +
            "left join fetch  l.district " +
            "left join fetch  l.addresses " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Town> findByName(@Param("name") String name);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Town> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Town> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Town> findAllById(Iterable<Long> iterable);
}
