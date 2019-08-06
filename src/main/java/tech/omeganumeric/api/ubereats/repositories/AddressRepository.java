package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Address;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = AddressRepository.PATH,
        path = AddressRepository.PATH,
        exported = false
)
public interface AddressRepository extends MetaRepository<Address, Long> {
    String PATH = Address.FIELD_ENTITY;
}
