package me.truec0der.mwhitelist.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandContext {
    @NonNull CommandSender sender;
    @NonNull String[] args;
}
