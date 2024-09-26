package me.truec0der.mwhitelist.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandEntity {
    @NonNull
    Supplier<String> name;
    @NonNull
    Supplier<String> regex;
    @NonNull
    Supplier<String[]> completeArgs;
    @NonNull
    Supplier<String> permission;
    @NonNull
    Function<CommandContext, Boolean> handler;

    public boolean execute(CommandContext context) {
        return getHandler().apply(context);
    }

    public <T extends CommandSender> boolean hasPermission(T sender) {
        return sender.hasPermission(permission.get());
    }
}
