package ru.axelration.groupcontrolskillboxbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.config.AppConfig;
import ru.axelration.groupcontrolskillboxbot.config.GroupConfig;
import ru.axelration.groupcontrolskillboxbot.dto.BackendResponse;
import ru.axelration.groupcontrolskillboxbot.dto.enums.BotStatus;
import ru.axelration.groupcontrolskillboxbot.model.Group;
import ru.axelration.groupcontrolskillboxbot.model.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BotService {
    
    private final AppConfig appConfig;
    private final GroupConfig groupConfig;
    private final TelegramBot bot;
    private final GroupService groupService;
    private final JoiningService joiningService;
    private final UserService userService;
    
    private List<Group> groups;
    
    
    public BackendResponse start() {
        
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
    
    private void init() {
        long count = groupService.count();
        if (count <= 0) {
            List<GroupConfig.ModeratorGroup> groupsId = groupConfig.getGroups();
            List<Group> groups = parseConfig(groupsId);
            this.groups = groupService.saveAll(groups);
        }
    }
    
    private List<Group> parseConfig(List<GroupConfig.ModeratorGroup> groupsId) {
        List<Group> groups = new ArrayList<>();
        for (GroupConfig.ModeratorGroup moderatorGroup : groupsId) {
            List<Group> groupList = new ArrayList<>();
        
            Group adminGroup = new Group(moderatorGroup.id());
            adminGroup.setRole(Role.ADMIN);
            groupList.add(adminGroup);
        
            for (GroupConfig.UserGroup userGroup : moderatorGroup.userGroups()) {
                Group memberGroup = new Group(userGroup.id());
                memberGroup.setGroup(adminGroup);
                memberGroup.setRole(Role.USER);
                groupList.add(memberGroup);
            }
        
            groups.addAll(groupList);
        }
        groups.forEach(this::getGroup);
        return groups;
    }
    
    private void getGroup(Group group) {
        if (group == null) {
            return;
        }
        
        GetChat getChat = new GetChat(group.getId());
        GetChatResponse getChatResponse = bot.execute(getChat);
        if (getChatResponse.isOk()) {
            return;
        }
        
        Chat chat = getChatResponse.chat();
        group.setTitle(chat.title());
    }
    
    
    private void process(Update update) {
    
    
    }
    
}
