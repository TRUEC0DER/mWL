package me.truec0der.mwhitelist.utils;

import me.truec0der.mwhitelist.models.ConfigModel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Map;

public class MessageUtil {
    private ConfigModel configModel;
    private MiniMessage miniMessage;

    public MessageUtil(ConfigModel configModel) {
        this.configModel = configModel;
        this.miniMessage = MiniMessage.miniMessage();
    }

    public Component create(String message) {
        String prefix = configModel.getMessagePrefix();
        return miniMessage.deserialize(message.replaceAll("%prefix%", prefix));
    }

    public Component create(String message, Map<String, String> placeholders) {
        String prefix = configModel.getMessagePrefix();

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            String replacement = entry.getValue();
            message = message.replace(placeholder, replacement);
        }

        return miniMessage.deserialize(message.replaceAll("%prefix%", prefix));
    }
}
