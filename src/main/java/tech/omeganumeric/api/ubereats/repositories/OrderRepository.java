package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Order;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.OrderId;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = OrderRepository.PATH,
        path = OrderRepository.PATH,
        exported = true
)
public interface OrderRepository extends MetaRepository<Order, OrderId> {
    String PATH = "Orders";

    @Query("SELECT l from Order l " +
            "left join l.status " +
            "left join l.payment " +
            "left join l.restaurant " +
            "left join l.menu " +
            "left join l.user "
    )
    @Override
    List<Order> findAll();


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Order> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Order> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Order> findAllById(Iterable<OrderId> iterable);
}
