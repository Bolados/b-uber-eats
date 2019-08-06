package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Order;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.OrderId;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = OrderRepository.PATH,
        path = OrderRepository.PATH,
        exported = false
)
public interface OrderRepository extends MetaRepository<Order, OrderId> {
    String PATH = Order.FIELD_ENTITY;
}
