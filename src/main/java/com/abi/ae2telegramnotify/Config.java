package com.abi.ae2telegramnotify;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLED;
    public static final ModConfigSpec.ConfigValue<String> BOT_TOKEN;
    public static final ModConfigSpec.ConfigValue<String> DEFAULT_CHAT_ID;
    public static final ModConfigSpec.ConfigValue<String> API_BASE_URL;
    public static final ModConfigSpec.IntValue REQUEST_TIMEOUT_SECONDS;

    public static final ModConfigSpec.BooleanValue ONLY_WHEN_PLAYER_OFFLINE;
    public static final ModConfigSpec.BooleanValue IGNORE_FAKE_PLAYERS;
    public static final ModConfigSpec.IntValue MIN_ELAPSED_SECONDS;
    public static final ModConfigSpec.LongValue MIN_AMOUNT;
    public static final ModConfigSpec.ConfigValue<String> LANGUAGE;
    public static final ModConfigSpec.BooleanValue INCLUDE_CPU_NAME;
    public static final ModConfigSpec.BooleanValue INCLUDE_DURATION;
    public static final ModConfigSpec.BooleanValue INCLUDE_DIMENSION;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("telegram");

        ENABLED = BUILDER
                .comment("Enable or disable Telegram notifications globally.")
                .define("enabled", true);

        BOT_TOKEN = BUILDER
                .comment("Telegram Bot API token from @BotFather (e.g. 123456789:ABCdef...).")
                .define("botToken", "");

        DEFAULT_CHAT_ID = BUILDER
                .comment("Optional fallback Telegram chat_id used when a player has not bound their own chat_id via /aenotify.")
                .define("defaultChatId", "");

        API_BASE_URL = BUILDER
                .comment("Base URL for Telegram Bot API. Change only if you use a custom Bot API server or reverse proxy.")
                .define("apiBaseUrl", "https://api.telegram.org");

        REQUEST_TIMEOUT_SECONDS = BUILDER
                .comment("HTTP timeout in seconds for Telegram API requests.")
                .defineInRange("requestTimeoutSeconds", 10, 2, 60);

        BUILDER.pop();

        BUILDER.push("notifications");

        ONLY_WHEN_PLAYER_OFFLINE = BUILDER
                .comment("If true, send Telegram notifications only when the ordering player is currently offline.")
                .define("onlyWhenPlayerOffline", false);

        IGNORE_FAKE_PLAYERS = BUILDER
                .comment("Ignore orders submitted by fake players (automated machines masquerading as players).")
                .define("ignoreFakePlayers", true);

        MIN_ELAPSED_SECONDS = BUILDER
                .comment("Minimum crafting duration in seconds required to send a notification (0 = notify for all orders).")
                .defineInRange("minElapsedSeconds", 0, 0, 86400);

        MIN_AMOUNT = BUILDER
                .comment("Minimum requested output amount required to send a notification.")
                .defineInRange("minAmount", 1L, 1L, Long.MAX_VALUE);

        LANGUAGE = BUILDER
                .comment("Notification message language: 'ru' or 'en'.")
                .define("language", "ru");

        INCLUDE_CPU_NAME = BUILDER
                .comment("Include the AE2 Crafting CPU name (if set) in the notification.")
                .define("includeCpuName", true);

        INCLUDE_DURATION = BUILDER
                .comment("Include elapsed crafting time in the notification.")
                .define("includeDuration", true);

        INCLUDE_DIMENSION = BUILDER
                .comment("Include the world dimension ID where the Crafting CPU is located.")
                .define("includeDimension", false);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    private Config() {
    }
}
