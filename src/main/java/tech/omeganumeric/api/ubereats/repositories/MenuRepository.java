package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Menu;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = MenuRepository.PATH,
        path = MenuRepository.PATH,
        exported = true
)
public interface MenuRepository extends MetaRepository<Menu, Long> {
    String PATH = "Menus";

    @Query("SELECT l from Menu l " +
            "left join l.media " +
            "left join l.restaurants " +
            "left join l.orders"
    )
    @Override
    List<Menu> findAll();


    @Query("SELECT l from Menu l " +
            "left join l.media " +
            "left join l.restaurants " +
            "left join l.orders " +
            "where lower(l.code) = lower(:code) " +
            "")
    Optional<Menu> findByCode(@Param("code") String code);

    @Query("SELECT l from Menu l " +
            "left join l.media " +
            "left join l.restaurants " +
            "left join l.orders " +
            "where lower(l.name) = lower(:name) " +
            "")
    List<Menu> findByName(@Param("name") String name);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Menu> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Menu> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Menu> findAllById(Iterable<Long> iterable);
}
