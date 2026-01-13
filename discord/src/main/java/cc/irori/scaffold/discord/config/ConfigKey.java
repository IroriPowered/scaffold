package cc.irori.scaffold.discord.config;

public record ConfigKey<T>(String id, Class<T> type) {

    public static final ConfigKey<String> BOT_TOKEN = new ConfigKey<>("bot_token", String.class);
    public static final ConfigKey<String> CHAT_CHANNEL_ID = new ConfigKey<>("chat_channel_id", String.class);

    public static final ConfigKey<Boolean> USE_JAPANIZE = new ConfigKey<>("use_japanize", Boolean.class);

    public static final ConfigKey<String> JOIN_MESSAGE_FORMAT = new ConfigKey<>("join_message_format", String.class);
    public static final ConfigKey<String> QUIT_MESSAGE_FORMAT = new ConfigKey<>("quit_message_format", String.class);
    public static final ConfigKey<String> CHAT_MESSAGE_FORMAT = new ConfigKey<>("chat_message_format", String.class);
    public static final ConfigKey<String> CHAT_MESSAGE_FORMAT_JAPANIZE = new ConfigKey<>("chat_message_format_japanize", String.class);
}
