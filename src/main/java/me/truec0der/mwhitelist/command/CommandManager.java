package me.truec0der.mwhitelist.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager {
    private final List<CommandEntity> commandEntities = new ArrayList<>();

    public void command(CommandEntity entity) {
        commandEntities.add(entity);
    }

    public List<CommandEntity> findEmptyCommands() {
        return commandEntities.stream().filter(command -> command.getName().get().isEmpty()).collect(Collectors.toList());
    }

    public List<CommandEntity> findCommands(String commandName) {
        return commandEntities.stream()
                .filter(command -> command.getName().get().equals(commandName))
                .collect(Collectors.toList());
    }

    public List<String> findNames(CommandSender player) {
        return commandEntities
                .stream()
                .filter(command ->
                        player.hasPermission(command.getPermission().get())
                                && !command.getName().get().isEmpty())
                .map(entity -> entity.getName().get())
                .collect(Collectors.toList());
    }
}
