package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.RestaurantMenu;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.RestaurantMenuId;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RestaurantMenuRepository.PATH,
        path = RestaurantMenuRepository.PATH,
        exported = false)
public interface RestaurantMenuRepository
        extends MetaRepository<RestaurantMenu, RestaurantMenuId> {
    String PATH = RestaurantMenu.FIELD_ENTITY;
}
