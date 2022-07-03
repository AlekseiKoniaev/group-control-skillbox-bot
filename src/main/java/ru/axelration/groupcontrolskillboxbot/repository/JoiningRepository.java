package ru.axelration.groupcontrolskillboxbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.axelration.groupcontrolskillboxbot.model.Joining;

@Repository
public interface JoiningRepository extends CrudRepository<Joining, Long> {
}
