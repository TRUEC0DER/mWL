package me.truec0der.mwhitelist.command.subcommand;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.command.Command;
import me.truec0der.mwhitelist.command.CommandContext;
import me.truec0der.mwhitelist.command.CommandEntity;
import me.truec0der.mwhitelist.service.ServiceContainer;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubCommandRemove implements Command {
    ServiceContainer serviceContainer;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "remove")
                .regex(() -> "^(\\S+)$")
                .completeArgs(() -> new String[]{String.join("|", serviceContainer.getWhitelistInfoService().getWhitelistNicknames())})
                .permission(() -> "mwl.command.remove")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();
        String nickname = context.getArgs()[0];

        serviceContainer.getWhitelistActionService().removePlayer(commandSender, nickname);

        return true;
    }
}
