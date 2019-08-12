package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.OrderStatus;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = OrderStatusRepository.PATH,
        path = OrderStatusRepository.PATH,
        exported = true
)
public interface OrderStatusRepository extends MetaRepository<OrderStatus, Long> {
    String PATH = "orders_statuses";

    @Query("SELECT l from OrderStatus l " +
            "left join fetch  l.orders"
    )
    @Override
    List<OrderStatus> findAll();


    @Query("SELECT l from OrderStatus l " +
            "left join fetch  l.orders " +
            "where lower(l.status) = lower(:status) " +
            "")
    Optional<OrderStatus> findByStatus(@Param("status") String status);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends OrderStatus> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends OrderStatus> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<OrderStatus> findAllById(Iterable<Long> iterable);
}
