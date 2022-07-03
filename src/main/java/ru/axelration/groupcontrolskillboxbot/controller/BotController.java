package ru.axelration.groupcontrolskillboxbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.axelration.groupcontrolskillboxbot.dto.BackendResponse;
import ru.axelration.groupcontrolskillboxbot.service.BotService;

@RestController
@RequestMapping("/backend")
@RequiredArgsConstructor
public class BotController {
    
    private final BotService botService;
    
    @GetMapping("/start")
    private BackendResponse startBotBackend() {
        return botService.start();
    }
    
    @GetMapping("/stop")
    private BackendResponse stopBotBackend() {
        return botService.stop();
    }
    
}
