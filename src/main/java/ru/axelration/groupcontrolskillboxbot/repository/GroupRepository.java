package ru.axelration.groupcontrolskillboxbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.axelration.groupcontrolskillboxbot.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
}
