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
public class SubCommandRemove implements Command {
    ServiceRegister serviceRegister;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "remove")
                .regex(() -> "^(\\S+)$")
                .completeArgs(() -> new String[]{String.join("|", serviceRegister.getWhitelistInfoService().getWhitelistNicknames())})
                .permission(() -> "mwl.command.remove")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();
        String nickname = context.getArgs()[0];

        serviceRegister.getWhitelistActionService().removePlayer(commandSender, nickname);

        return true;
    }
}
