package ru.axelration.groupcontrolskillboxbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.dto.BackendResponse;
import ru.axelration.groupcontrolskillboxbot.dto.enums.BotStatus;

@Service
@RequiredArgsConstructor
public class BotService {
    
    private final TelegramBot bot;
    private final GroupService groupService;
    private final JoiningService joiningService;
    private final UserService userService;
    
    
    public BackendResponse start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        return new BackendResponse(true, BotStatus.START);
    }
    
    public BackendResponse stop() {
        bot.removeGetUpdatesListener();
        return new BackendResponse(true, BotStatus.STOP);
    }
    
    
    private void process(Update update) {
        update.chatJoinRequest();
        
        Message message = update.message();
        
        if (message != null) {
            long chatId = message.chat().id();
            bot.execute(new SendMessage(chatId, message.text() + " from Backend!"));
        }
    }
    
}
