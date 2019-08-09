package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Town;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = TownRepository.PATH,
        path = TownRepository.PATH,
        exported = false
)
public interface TownRepository extends MetaRepository<Town, Long> {
    String PATH = Town.FIELD_ENTITY;
}
