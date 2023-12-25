package me.truec0der.mwhitelist.utils;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.models.ConfigModel;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.Map;

public class MessageUtil {
    private ConfigModel configModel;
    private MiniMessage miniMessage;
    private String prefix;

    public MessageUtil(ConfigModel configModel) {
        this.configModel = configModel;
        this.miniMessage = MiniMessage.miniMessage();
        this.prefix = configModel.getMessagePrefix();
    }

    public Component create(String message) {
        return miniMessage.deserialize(message.replaceAll("%prefix%", prefix));
    }

    public Component create(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            String replacement = entry.getValue();
            message = message.replace(placeholder, replacement);
        }

        return miniMessage.deserialize(message.replaceAll("%prefix%", prefix));
    }
}
