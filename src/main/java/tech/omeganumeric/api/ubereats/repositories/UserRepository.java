package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Phone;
import tech.omeganumeric.api.ubereats.domains.entities.User;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = UserRepository.PATH,
        path = UserRepository.PATH,
        exported = true
)
public interface UserRepository extends MetaRepository<User, Long> {
    String PATH = "users";

    @Query("select l from User l " +
            "left join fetch l.phone p " +
            "left join fetch l.roles " +
            "left join fetch l.residence " +
            "left join fetch l.delivery " +
            "left join fetch l.location " +
            "left join fetch l.orders " +
            "left join fetch l.savedAddresses " +
            "where l.phone =:phone")
    Optional<User> findByPhone(Phone phone);

    @Query("select l from User l " +
            "left join fetch l.phone p " +
            "left join fetch l.roles " +
            "left join fetch l.residence " +
            "left join fetch l.delivery " +
            "left join fetch l.location " +
            "left join fetch l.orders " +
            "left join fetch l.savedAddresses " +
            "where l.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select l from User l " +
            "left join fetch l.phone p " +
            "left join fetch l.roles " +
            "left join fetch l.residence " +
            "left join fetch l.delivery " +
            "left join fetch l.location " +
            "left join fetch l.orders " +
            "left join fetch l.savedAddresses " +
            "where l.login =:login")
    Optional<User> findByLogin(@Param("login") String login);


}
