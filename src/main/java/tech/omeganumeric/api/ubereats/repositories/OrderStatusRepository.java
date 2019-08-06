package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.OrderStatus;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = OrderStatusRepository.PATH,
        path = OrderStatusRepository.PATH,
        exported = true
)
public interface OrderStatusRepository extends MetaRepository<OrderStatus, Long> {
    String PATH = "orders/status";

    @Query("SELECT l from OrderStatus l " +
            "left join l.orders"
    )
    @Override
    List<OrderStatus> findAll();


    @Query("SELECT l from OrderStatus l " +
            "left join l.orders " +
            "where lower(l.status) = lower(:status) " +
            "")
    Optional<OrderStatus> findByStatus(@Param("status") String status);
}
