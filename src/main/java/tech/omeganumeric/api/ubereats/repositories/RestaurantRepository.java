package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Restaurant;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RestaurantRepository.PATH,
        path = RestaurantRepository.PATH,
        exported = true)
public interface RestaurantRepository extends MetaRepository<Restaurant, Long> {
    String PATH = "restaurants";

    @Query("SELECT l from Restaurant l " +
            "left join fetch  l.address " +
            "left join fetch  l.phones " +
            "left join fetch  l.menus " +
            "left join fetch  l.orders "
    )
    @Override
    List<Restaurant> findAll();


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Restaurant> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Restaurant> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Restaurant> findAllById(Iterable<Long> iterable);
}
