package ru.axelration.groupcontrolskillboxbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.repository.JoiningRepository;

@Service
@RequiredArgsConstructor
public class JoiningService {
    
    private final JoiningRepository repository;
}
