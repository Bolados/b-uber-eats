package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Region;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RegionRepository.PATH,
        path = RegionRepository.PATH,
        exported = true
)
public interface RegionRepository extends MetaRepository<Region, Long> {
    String PATH = "regions";

    @Query("SELECT l from Region l left join l.countries")
    @Override
    List<Region> findAll();


    @Query("SELECT l from Region l " +
            "left join l.countries " +
            "where lower(l.code) = lower(:code) " +
            "")
    Optional<Region> findByCode(@Param("code") String code);

    @Query("SELECT l from Region l " +
            "left join l.countries " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Region> findByName(@Param("name") String name);


}
