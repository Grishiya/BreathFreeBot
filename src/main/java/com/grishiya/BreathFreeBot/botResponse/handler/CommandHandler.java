package com.grishiya.BreathFreeBot.botResponse.handler;

import com.grishiya.BreathFreeBot.botResponse.command.Command;
import com.grishiya.BreathFreeBot.botResponse.command.QuitSmockingCommand;
import com.grishiya.BreathFreeBot.botResponse.command.StartCommand;
import com.grishiya.BreathFreeBot.botResponse.command.UnknownCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final StartCommand startCommand;
    private final QuitSmockingCommand quitSmokingCommand;
    private final UnknownCommand unkownCommand;

    private Map<String, Command> commandMap;

    public void init() {
        commandMap = new HashMap<>();
        commandMap.put("/start", startCommand);
        commandMap.put("quit_smocking", quitSmokingCommand);
    }

    public void handleUpdate(Update update) {
        String key = getKey(update);
        Command command = commandMap.getOrDefault(key, unkownCommand);
        command.execute(update);
    }

    private String getKey(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }
        return "";
    }

}
