package ru.axelration.groupcontrolskillboxbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.axelration.groupcontrolskillboxbot.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
