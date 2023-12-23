package me.truec0der.mwhitelist.utils;

import me.truec0der.mwhitelist.models.ConfigModel;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.Map;

public class MessageUtil {
    private static String prefix = ConfigModel.getMessagePrefix();
    private static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component createWithPrefix(String message) {
        String[] lines = message.split("\\r?\\n");

        ComponentBuilder<TextComponent, TextComponent.Builder> resultBuilder = Component.text();
        for (int i = 0; i < lines.length; i++) {
            resultBuilder.append(miniMessage.deserialize(prefix + lines[i]));
            if (i < lines.length - 1) {
                resultBuilder.append(Component.newline());
            }
        }

        return resultBuilder.build();
    }

    public static Component createWithPrefix(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            String replacement = entry.getValue();
            message = message.replace(placeholder, replacement);
        }

        String[] lines = message.split("\\r?\\n");

        ComponentBuilder<TextComponent, TextComponent.Builder> resultBuilder = Component.text();
        for (int i = 0; i < lines.length; i++) {
            resultBuilder.append(miniMessage.deserialize(prefix + lines[i]));
            if (i < lines.length - 1) {
                resultBuilder.append(Component.newline());
            }
        }

        return resultBuilder.build();
    }

    public static Component createWithoutPrefix(String message) {
        return miniMessage.deserialize(message);
    }

    public static Component createWithoutPrefix(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            String replacement = entry.getValue();
            message = message.replace(placeholder, replacement);
        }

        return miniMessage.deserialize(message);
    }
}
