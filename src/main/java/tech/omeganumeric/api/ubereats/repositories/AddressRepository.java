package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Address;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = AddressRepository.PATH,
        path = AddressRepository.PATH,
        exported = true
)
public interface AddressRepository extends MetaRepository<Address, Long> {
    String PATH = "addresses";

    @Query("SELECT l from Address l " +
            "left join l.town " +
            "left join l.residents " +
            "left join l.locatedUsers " +
            "left join l.deliveryUsers " +
            "left join l.restaurants " +
            "left join l.addressSavedBy "
    )
    @Override
    List<Address> findAll();


    @Query("SELECT l from Address l " +
            "left join l.town " +
            "left join l.residents " +
            "left join l.locatedUsers " +
            "left join l.deliveryUsers " +
            "left join l.restaurants " +
            "left join l.addressSavedBy " +
            "where lower(l.street) = lower(:street) " +
            "")
    List<Address> findByStreet(@Param("street") String street);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Address> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Address> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Address> findAllById(Iterable<Long> iterable);
}
