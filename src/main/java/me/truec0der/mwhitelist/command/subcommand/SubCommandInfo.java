package me.truec0der.mwhitelist.command.subcommand;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.command.Command;
import me.truec0der.mwhitelist.command.CommandContext;
import me.truec0der.mwhitelist.command.CommandEntity;
import me.truec0der.mwhitelist.service.ServiceRegister;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubCommandInfo implements Command {
    ServiceRegister serviceRegister;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "info")
                .regex(() -> "")
                .completeArgs(() -> new String[0])
                .permission(() -> "mwl.command.info")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();

        serviceRegister.getWhitelistInfoService().sendWhitelistInfo(commandSender);

        return true;
    }
}
