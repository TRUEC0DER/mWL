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
public class SubCommandReload implements Command {
    ServiceContainer serviceContainer;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "reload")
                .regex(() -> "")
                .completeArgs(() -> new String[0])
                .permission(() -> "mwl.command.reload")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();

        serviceContainer.getPluginReloadService().reload(commandSender);

        return true;
    }
}
