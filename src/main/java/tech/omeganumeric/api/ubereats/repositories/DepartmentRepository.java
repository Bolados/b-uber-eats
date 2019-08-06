package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Department;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = DepartmentRepository.PATH,
        path = DepartmentRepository.PATH,
        exported = false
)
public interface DepartmentRepository extends MetaRepository<Department, Long> {
    String PATH = Department.FIELD_ENTITY;
}
