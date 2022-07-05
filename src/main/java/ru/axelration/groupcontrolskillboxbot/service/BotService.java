package ru.axelration.groupcontrolskillboxbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.ChatJoinRequest;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetChatMemberCount;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetChatMemberCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.config.AppConfig;
import ru.axelration.groupcontrolskillboxbot.dto.BackendResponse;
import ru.axelration.groupcontrolskillboxbot.dto.enums.BotStatus;

@Service
@RequiredArgsConstructor
public class BotService {
    
    private final AppConfig appConfig;
    private final TelegramBot bot;
    private final BotInitializerService botInitializerService;
    private final GroupService groupService;
    private final JoiningService joiningService;
    private final UserService userService;
    
    
    public BackendResponse start() {
        botInitializerService.initialiseBot();
        GetUpdates getUpdates = new GetUpdates().timeout(appConfig.getLongPollingTimeout());
        bot.setUpdatesListener(updates -> {
                    updates.forEach(this::process);
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                },
                getUpdates);
        return new BackendResponse(true, BotStatus.START);
    }
    
    public BackendResponse stop() {
        bot.removeGetUpdatesListener();
        return new BackendResponse(true, BotStatus.STOP);
    }
    
    
    
    private int count(Long chatId) {
        GetChatMemberCount getChatMemberCount = new GetChatMemberCount(chatId);
        GetChatMemberCountResponse getChatMemberCountResponse = bot.execute(getChatMemberCount);
        return getChatMemberCountResponse.count();
    }
    
    private void extracted(int count) {
        int multiplicity = appConfig.getMultiplicity();
        int incrementalSaves = appConfig.getIncrementalSaves();
        if (count % multiplicity <= incrementalSaves) {
        
        }
    }
    
    private void process(Update update) {
        update.updateId();
        ChatJoinRequest chatJoinRequest = update.chatJoinRequest();
    
    }
    
}
