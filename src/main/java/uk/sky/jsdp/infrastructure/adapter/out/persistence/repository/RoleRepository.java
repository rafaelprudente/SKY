package uk.sky.jsdp.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
