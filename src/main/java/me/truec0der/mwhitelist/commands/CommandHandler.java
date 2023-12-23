package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Audience audience = (Audience) sender;
        if (!sender.hasPermission("mwl.admin")) {
            audience.sendMessage(MessageUtil.createWithPrefix(ConfigModel.getMessageNotPerms()));
            return true;
        }
        if (args.length == 0) return new CommandHelp().execute(audience);
        String firstArgument = args[0].toLowerCase();
        switch (firstArgument) {
            case "toggle":
                return new CommandToggle().execute(audience, args);
            case "add":
                return new CommandAdd().execute(audience, args);
            case "remove":
                return new CommandRemove().execute(audience, args);
            case "list":
                return new CommandList().execute(audience);
            case "reload":
                return new CommandReload().execute(audience);
            default:
                return new CommandHelp().execute(audience);
        }
    }
}
