package tech.omeganumeric.api.ubereats.repositories.meta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface MetaRepository<Entity, ID>
        extends JpaRepository<Entity, ID>, QueryByExampleExecutor<Entity> {

}
