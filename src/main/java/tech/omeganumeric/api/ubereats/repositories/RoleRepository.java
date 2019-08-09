package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Role;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = RoleRepository.PATH,
        path = RoleRepository.PATH,
        exported = true)
public interface RoleRepository extends MetaRepository<Role, Long> {
    String PATH = "users_roles";

    @Query("SELECT l from Role l left join l.users")
    @Override
    List<Role> findAll();


    @Query("SELECT l from Role l " +
            "left join l.users " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Role> findByName(@Param("name") String name);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Role> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Role> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Role> findAllById(Iterable<Long> iterable);
}
