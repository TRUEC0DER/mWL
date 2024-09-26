package me.truec0der.mwhitelist.command;

public interface Command {
    CommandEntity getEntity();

    boolean handle(CommandContext commandContext);
}
