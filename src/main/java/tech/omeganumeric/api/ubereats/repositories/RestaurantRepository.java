package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Restaurant;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RestaurantRepository.PATH,
        path = RestaurantRepository.PATH,
        exported = false)
public interface RestaurantRepository extends MetaRepository<Restaurant, Long> {
    String PATH = Restaurant.FIELD_ENTITY;
}
