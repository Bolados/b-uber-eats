package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.RestaurantMenu;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.RestaurantMenuId;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RestaurantMenuRepository.PATH,
        path = RestaurantMenuRepository.PATH,
        exported = true)
public interface RestaurantMenuRepository
        extends MetaRepository<RestaurantMenu, RestaurantMenuId> {
    String PATH = "restaurants_menus";

    @Query("SELECT l from RestaurantMenu l " +
            "left join l.restaurant " +
            "left join l.menu "
    )
    @Override
    List<RestaurantMenu> findAll();


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends RestaurantMenu> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends RestaurantMenu> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<RestaurantMenu> findAllById(Iterable<RestaurantMenuId> iterable);
}
