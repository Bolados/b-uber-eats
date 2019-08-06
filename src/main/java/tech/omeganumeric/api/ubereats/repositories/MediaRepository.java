package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Media;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = MediaRepository.PATH,
        path = MediaRepository.PATH,
        exported = true
)
public interface MediaRepository extends MetaRepository<Media, Long> {
    String PATH = "media";

    @Query("SELECT l from Media l " +
            "left join l.menu" +
            ""
    )
    @Override
    List<Media> findAll();


    @Query("SELECT l from Media l " +
            "left join l.menu " +
            "where lower(l.src) = lower(:src) " +
            "")
    Optional<Media> findBySrc(@Param("src") String src);
}
