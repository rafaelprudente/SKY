package uk.sky.jsdp.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.ExternalProjectEntity;

import java.util.List;

@Repository
public interface ExternalProjectRepository extends CrudRepository<ExternalProjectEntity, String> {

    List<ExternalProjectEntity> findByUser_Id(long id);
}
