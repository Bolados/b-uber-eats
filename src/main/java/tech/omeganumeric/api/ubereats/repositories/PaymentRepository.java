package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Payment;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = PaymentRepository.PATH,
        path = PaymentRepository.PATH,
        exported = false
)
public interface PaymentRepository extends MetaRepository<Payment, Long> {
    String PATH = Payment.FIELD_ENTITY;
}
