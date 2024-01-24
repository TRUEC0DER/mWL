package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    private ConfigManager configManager;
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;

    public CommandHandler(ConfigManager configManager, ConfigModel configModel, Database database, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mwl.admin")) {
            sender.sendMessage(messageUtil.create(configModel.getMessageNotPerms()));
            return true;
        }
        if (args.length == 0) return new CommandHelp(configModel, messageUtil).execute(sender);
        String firstArgument = args[0].toLowerCase();
        switch (firstArgument) {
            case "toggle":
                return new CommandToggle(configManager, configModel, messageUtil).execute(sender, args);
            case "add":
                return new CommandAdd(configModel, database, messageUtil).execute(sender, args);
            case "addtemp":
                return new CommandAddTemp(configModel, database, messageUtil).execute(sender, args);
            case "remove":
                return new CommandRemove(configModel, database, messageUtil).execute(sender, args);
            case "list":
                return new CommandList(configModel, database, messageUtil).execute(sender);
            case "reload":
                return new CommandReload(configManager, configModel, messageUtil).execute(sender);
            default:
                return new CommandHelp(configModel, messageUtil).execute(sender);
        }
    }
}
