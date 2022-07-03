package ru.axelration.groupcontrolskillboxbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {
    
    private final GroupRepository repository;
    
}
