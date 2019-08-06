package tech.omeganumeric.api.ubereats.webservice.security.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.repositories.MetaRepository;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.AuthorityApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = AuthorityApiRepository.PATH,
        path = AuthorityApiRepository.PATH,
        exported = true
)
public interface AuthorityApiRepository extends MetaRepository<AuthorityApi, Long> {
    String PATH = "api/authorities";

    @RestResource(path = "byName", rel = "customFindMethod")
    @Query("select l from AuthorityApi l left join fetch l.users " +
            "where lower(l.name) =lower(:name)")
    AuthorityApi findFirstByName(String name);
}
