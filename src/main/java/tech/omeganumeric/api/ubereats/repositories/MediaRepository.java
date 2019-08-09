package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Media;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = MediaRepository.PATH,
        path = MediaRepository.PATH,
        exported = false
)
public interface MediaRepository extends MetaRepository<Media, Long> {
    String PATH = "medias";

    @Query("SELECT l from Media l " +
            "left join l.menu" +
            ""
    )
    @Override
    List<Media> findAll();


    @Query("SELECT l from Media l " +
            "left join l.menu " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Media> findByName(@Param("name") String name);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Media> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Media> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Media> findAllById(Iterable<Long> iterable);
}
