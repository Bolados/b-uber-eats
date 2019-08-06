package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Menu;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = MenuRepository.PATH,
        path = MenuRepository.PATH,
        exported = false
)
public interface MenuRepository extends MetaRepository<Menu, Long> {
    String PATH = Menu.FIELD_ENTITY;
}
