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
public class SubCommandToggle implements Command {
    ServiceContainer serviceContainer;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "toggle")
                .regex(() -> "^(enable|disable)$")
                .completeArgs(() -> new String[]{"enable|disable"})
                .permission(() -> "mwl.command.toggle")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();
        String action = context.getArgs()[0];

        serviceContainer.getWhitelistActionService().switchWhitelist(commandSender, action);

        return true;
    }
}
