package ru.axelration.groupcontrolskillboxbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.axelration.groupcontrolskillboxbot.model.Group;
import ru.axelration.groupcontrolskillboxbot.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "telegram.groupsId")
public class GroupConfig {
    
    private List<ModeratorGroup> groups;
    
    
    
    public record ModeratorGroup(long id, List<UserGroup> userGroups) {
    }
    
    public record UserGroup(long id) {
    }
}
