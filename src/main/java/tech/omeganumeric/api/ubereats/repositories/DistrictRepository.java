package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.District;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = DistrictRepository.PATH,
        path = DistrictRepository.PATH,
        exported = false
)
public interface DistrictRepository extends MetaRepository<District, Long> {
    String PATH = District.FIELD_ENTITY;
}
