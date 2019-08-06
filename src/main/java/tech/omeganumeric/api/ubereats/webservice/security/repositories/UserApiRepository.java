package tech.omeganumeric.api.ubereats.webservice.security.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.repositories.MetaRepository;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.UserApi;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = UserApiRepository.PATH,
        path = UserApiRepository.PATH,
        exported = true
)
public interface UserApiRepository extends MetaRepository<UserApi, Long> {
    String PATH = "api/users";

    @RestResource(path = "byUsernameOrEmail", rel = "customFindMethod")
    @Query("select l from UserApi l left join fetch l.authorities " +
            "where l.username =:username or l.email=:email")
    Optional<UserApi> findByUsernameOrEmail(String username, String email);

    @RestResource(path = "all", rel = "customFindMethod")
    @Query("select l from UserApi l left join fetch l.authorities ")
    List<UserApi> findAll();
}
