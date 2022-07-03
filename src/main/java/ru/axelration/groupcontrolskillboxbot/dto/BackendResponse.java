package ru.axelration.groupcontrolskillboxbot.dto;

import ru.axelration.groupcontrolskillboxbot.dto.enums.BotStatus;

public record BackendResponse(boolean ok, BotStatus status) {
}
