package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandAddTemp {
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;

    public CommandAddTemp(ConfigModel configModel, Database database, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.database = database;
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgs(sender);
            return true;
        }

        String playerName = args[1];
        long time = parseTime(args, 2);

        if (time < 0) {
            sendNeedMoreArgs(sender);
            return true;
        }

        addInWhitelist(sender, playerName, time);

        return true;
    }

    private void sendNeedMoreArgs(Audience sender) {
        Component needMoreArgs = messageUtil.create(configModel.getMessageWhitelistAddTempNeedMoreArgs());
        sender.sendMessage(needMoreArgs);
    }

    private void addInWhitelist(Audience sender, String playerName, long time) {
        CompletableFuture<UserModel> findUserInWhitelist = database.getUser(playerName);

        findUserInWhitelist.thenComposeAsync(user -> {
            long currentTimeMillis = System.currentTimeMillis();
            long expirationTimeMillis = currentTimeMillis + time;

            if (user == null) {
                return database.createUser(playerName)
                        .thenComposeAsync(createdUser -> database.setUserTime(playerName, expirationTimeMillis)
                                .thenApplyAsync(updatedUser -> createdUser));
            } else {
                return database.setUserTime(playerName, expirationTimeMillis)
                        .thenApplyAsync(updatedUser -> user);
            }
        }).thenAcceptAsync(updatedUser -> {
            Component addInWhitelist = messageUtil.create(configModel.getMessageWhitelistAddTempInfo(),
                    formatPlaceholders(playerName, calculateDuration(time)));
            sender.sendMessage(addInWhitelist);
        });
    }

    private Duration calculateDuration(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        return Duration.between(Instant.EPOCH, instant);
    }

    private Map<String, String> formatPlaceholders(String playerName, Duration duration) {
        List<String> messageTime = configModel.getMessageWhitelistAddTempTime();

        return Map.of(
                "player_name", playerName,
                "time_years", duration.toDays() / 365 + messageTime.get(0),
                "time_months", (duration.toDays() % 365) / 30 + messageTime.get(1),
                "time_weeks", (duration.toDays() % 365) / 7 + messageTime.get(2),
                "time_days", duration.toDays() % 7 + messageTime.get(3),
                "time_hours", duration.toHours() % 24 + messageTime.get(4),
                "time_minutes", duration.toMinutes() % 60 + messageTime.get(5),
                "time_seconds", duration.getSeconds() % 60 + messageTime.get(6)
        );
    }

    private long parseTime(String[] args, int startIndex) {
        long totalMillis = 0;

        for (int i = startIndex; i < args.length; i++) {
            String arg = args[i];

            Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z]+)");
            Matcher matcher = pattern.matcher(arg);

            if (matcher.matches()) {
                int number = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2);

                totalMillis += convertToMillis(number, unit);
            }
        }

        return totalMillis;
    }

    private long convertToMillis(int number, String unit) {
        switch (unit) {
            case "y":
                return number * 31556952000L;
            case "mo":
                return number * 2629746000L;
            case "w":
                return number * 604800000L;
            case "d":
                return number * 86400000L;
            case "h":
                return number * 3600000L;
            case "m":
                return number * 60000L;
            case "s":
                return number * 1000L;
            default:
                return -1;
        }
    }
}

