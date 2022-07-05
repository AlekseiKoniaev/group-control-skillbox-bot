package ru.axelration.groupcontrolskillboxbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.ChatAdministratorRights;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.GetMyDefaultAdministratorRights;
import com.pengrad.telegrambot.request.SetMyDefaultAdministratorRights;
import com.pengrad.telegrambot.response.GetChatResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axelration.groupcontrolskillboxbot.config.GroupConfig;
import ru.axelration.groupcontrolskillboxbot.model.Group;
import ru.axelration.groupcontrolskillboxbot.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotInitializerService {
    
    private final GroupConfig groupConfig;
    private final TelegramBot bot;
    private final GroupService groupService;
    @Getter
    private List<Group> groups;
    
    
    public void initialiseBot() {
        initialiseAdministratorRights();
        initialiseGroups();
    }
    
    private void initialiseAdministratorRights() {
        if (!administratorRightsIsCorrect()) {
            setAdministratorRights();
        }
    }
    
    private boolean administratorRightsIsCorrect() {
        var getMyDefaultAdministratorRights = new GetMyDefaultAdministratorRights();
        var response = bot.execute(getMyDefaultAdministratorRights);
        var rights = response.result();
        return rights.canInviteUsers() && rights.canEditMessages();
    }
    
    private void setAdministratorRights() {
        var chatAdministratorRights = new ChatAdministratorRights();    // TODO add isAnonymous(boolean)
        chatAdministratorRights.canInviteUsers(true);
        chatAdministratorRights.canEditMessages(true);
        
        var setMyDefaultAdministratorRights = new SetMyDefaultAdministratorRights();
        setMyDefaultAdministratorRights.rights(chatAdministratorRights);
        bot.execute(setMyDefaultAdministratorRights);
    }
    
    
    private void initialiseGroups() {
        long count = groupService.count();
        
        List<Group> groupList = parse();
        if (count <= 0) {
            groupList.forEach(this::getGroup);
            groups = groupService.saveAll(groups);
        } else {
            groupList.stream()
                    .filter(group -> !groupService.exists(group))
                    .forEach(groupService::save);
        }
    }
    
    private List<Group> parse() {
        
        List<Group> list = new ArrayList<>();
        
        for (GroupConfig.ModeratorGroup moderatorGroup : groupConfig.getGroups()) {
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
            
            list.addAll(groupList);
        }
        
        return list;
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
    
}
