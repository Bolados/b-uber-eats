package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Country;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = CountryRepository.PATH,
        path = CountryRepository.PATH,
        exported = false
)
public interface CountryRepository extends MetaRepository<Country, Long> {
    String PATH = Country.FIELD_ENTITY;
}
