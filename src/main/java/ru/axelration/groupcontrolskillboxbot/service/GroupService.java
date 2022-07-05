package ru.axelration.groupcontrolskillboxbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.model.Group;
import ru.axelration.groupcontrolskillboxbot.repository.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    
    private final GroupRepository repository;
    
    public Group save(Group group) {
        return repository.save(group);
    }
    
    public List<Group> saveAll(List<Group> groups) {
        return (List<Group>) repository.saveAll(groups);
    }
    
    public List<Group> getAll() {
        return (List<Group>) repository.findAll();
    }
    
    public long count() {
        return repository.count();
    }
    
    public boolean exists(Group group) {
        return repository.existsById(group.getId());
    }
    
}
