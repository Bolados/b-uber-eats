package tech.omeganumeric.api.ubereats.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.omeganumeric.api.ubereats.domains.entities.Department;
import tech.omeganumeric.api.ubereats.repositories.meta.MetaRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestResource(
        collectionResourceRel = DepartmentRepository.PATH,
        path = DepartmentRepository.PATH,
        exported = true
)
public interface DepartmentRepository extends MetaRepository<Department, Long> {
    String PATH = "departments";

    @Query("SELECT l from Department l " +
            "left join l.country " +
            "left join l.districts"
    )
    @Override
    List<Department> findAll();


    @Query("SELECT l from Department l " +
            "left join l.country " +
            "left join l.districts " +
            "where lower(l.name) = lower(:name) " +
            "")
    Optional<Department> findByName(@Param("name") String name);

    @Query("SELECT l from Department l " +
            "left join l.country " +
            "left join l.districts " +
            "where lower(l.code) = lower(:code) " +
            "")
    Optional<Department> findByCode(@Param("code") String code);


    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Department> List<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    <S extends Department> Optional<S> findOne(Example<S> example);

    @Override
    @RestResource(exported = false)
    List<Department> findAllById(Iterable<Long> iterable);
}
