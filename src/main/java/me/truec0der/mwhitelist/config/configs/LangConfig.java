package me.truec0der.mwhitelist.config.configs;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.config.ConfigHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Getter
public class LangConfig extends ConfigHolder {
    private static Component prefix;
    private Component needCorrectArgs;
    private Component notInWhitelist;
    private Component whitelistTimeExpired;
    private Component notPerms;
    private Command command;

    public LangConfig(Plugin plugin, File filePath, String file, String defaultFile) {
        super(plugin, filePath, file, defaultFile);
        loadAndSave();
        init();
    }

    @Override
    public void init() {
        YamlConfiguration config = this.getConfig();

        prefix = getComponent(config.getString("prefix"));
        needCorrectArgs = getComponent(config.getString("need-correct-args"));
        notInWhitelist = getComponent(config.getString("not-in-whitelist"));
        whitelistTimeExpired = getComponent(config.getString("whitelist-time-expired"));
        notPerms = getComponent(config.getString("not-perms"));

        command = Command.serialize(config.getConfigurationSection("command"));
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Command {
        CommandHelp help;
        CommandInfo info;
        CommandToggle toggle;
        CommandAdd add;
        CommandAddTemp addTemp;
        CommandRemove remove;
        CommandList list;
        CommandCheck check;
        CommandReload reload;

        public static Command serialize(ConfigurationSection section) {
            return Command.builder()
                    .help(CommandHelp.serialize(section.getConfigurationSection("help")))
                    .info(CommandInfo.serialize(section.getConfigurationSection("info")))
                    .toggle(CommandToggle.serialize(section.getConfigurationSection("toggle")))
                    .add(CommandAdd.serialize(section.getConfigurationSection("add")))
                    .addTemp(CommandAddTemp.serialize(section.getConfigurationSection("add-temp")))
                    .remove(CommandRemove.serialize(section.getConfigurationSection("remove")))
                    .list(CommandList.serialize(section.getConfigurationSection("list")))
                    .check(CommandCheck.serialize(section.getConfigurationSection("check")))
                    .reload(CommandReload.serialize(section.getConfigurationSection("reload")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandHelp {
        Component info;

        public static CommandHelp serialize(ConfigurationSection section) {
            return CommandHelp.builder()
                    .info(getComponent(section.getString("info")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandInfo {
        Component info;
        Component enabledStatus;
        Component disabledStatus;

        public static CommandInfo serialize(ConfigurationSection section) {
            return CommandInfo.builder()
                    .info(getComponent(section.getString("info")))
                    .enabledStatus(getComponent(section.getString("status.enabled")))
                    .disabledStatus(getComponent(section.getString("status.disabled")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandToggle {
        Component invalidAction;
        Component enabled;
        Component disabled;

        public static CommandToggle serialize(ConfigurationSection section) {
            return CommandToggle.builder()
                    .invalidAction(getComponent(section.getString("invalid-action")))
                    .enabled(getComponent(section.getString("enabled")))
                    .disabled(getComponent(section.getString("disabled")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandAdd {
        Component alreadyAdded;
        Component added;

        public static CommandAdd serialize(ConfigurationSection section) {
            return CommandAdd.builder()
                    .alreadyAdded(getComponent(section.getString("already-added")))
                    .added(getComponent(section.getString("added")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandAddTemp {
        Component invalidTime;
        Component alreadyAdded;
        Component added;

        public static CommandAddTemp serialize(ConfigurationSection section) {
            return CommandAddTemp.builder()
                    .invalidTime(getComponent(section.getString("invalid-time")))
                    .alreadyAdded(getComponent(section.getString("already-added")))
                    .added(getComponent(section.getString("added")))
                    .build();
        }
    }


    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandRemove {
        Component notIn;
        Component removed;

        public static CommandRemove serialize(ConfigurationSection section) {
            return CommandRemove.builder()
                    .notIn(getComponent(section.getString("not-in")))
                    .removed(getComponent(section.getString("removed")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandList {
        Component info;
        Component empty;
        Component player;
        Component separator;

        public static CommandList serialize(ConfigurationSection section) {
            return CommandList.builder()
                    .info(getComponent(section.getString("info")))
                    .empty(getComponent(section.getString("empty")))
                    .player(getComponent(section.getString("player")))
                    .separator(getComponent(section.getString("separator")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandCheck {
        Component notIn;
        Component info;
        Component historyNickname;
        Component historySeparator;
        Component timeInfinityInfo;
        Component timeInfinityAbout;
        Component timeExpiredAbout;
        Component timeActiveAbout;

        public static CommandCheck serialize(ConfigurationSection section) {
            return CommandCheck.builder()
                    .notIn(getComponent(section.getString("not-in")))
                    .info(getComponent(section.getString("info")))
                    .historyNickname(getComponent(section.getString("nickname-history.nickname")))
                    .historySeparator(getComponent(section.getString("nickname-history.separator")))
                    .timeInfinityInfo(getComponent(section.getString("time.infinity.info")))
                    .timeInfinityAbout(getComponent(section.getString("time.infinity.about")))
                    .timeExpiredAbout(getComponent(section.getString("time.expired.about")))
                    .timeActiveAbout(getComponent(section.getString("time.active.about")))
                    .build();
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommandReload {
        Component info;

        public static CommandReload serialize(ConfigurationSection section) {
            return CommandReload.builder()
                    .info(getComponent(section.getString("info")))
                    .build();
        }
    }

    private static Component getComponent(String string) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(string)
                .replaceText(text -> text.match("%prefix%").replacement(prefix));
    }
}
