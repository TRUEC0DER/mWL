package me.truec0der.mwhitelist.command.subcommand;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.command.Command;
import me.truec0der.mwhitelist.command.CommandContext;
import me.truec0der.mwhitelist.command.CommandEntity;
import me.truec0der.mwhitelist.interfaces.repository.PlayerRepository;
import me.truec0der.mwhitelist.service.ServiceContainer;
import me.truec0der.mwhitelist.util.UUIDUtil;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubCommandAdd implements Command {
    ServiceContainer serviceContainer;

    @Override
    public CommandEntity getEntity() {
        return CommandEntity.builder()
                .name(() -> "add")
                .regex(() -> "^(\\S+)$")
                .completeArgs(() -> new String[0])
                .permission(() -> "mwl.command.add")
                .handler(this::handle)
                .build();
    }

    @Override
    public boolean handle(CommandContext context) {
        CommandSender commandSender = context.getSender();
        String nickname = context.getArgs()[0];

        serviceContainer.getWhitelistActionService().addPlayer(commandSender, nickname);

        return true;
    }
}
