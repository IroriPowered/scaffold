package cc.irori.scaffold.discord.config;

public record ConfigKey<T>(String id, Class<T> type) {

    public static final ConfigKey<String> BOT_TOKEN = new ConfigKey<>("BotToken", String.class);
    public static final ConfigKey<String> CHAT_CHANNEL_ID = new ConfigKey<>("ChatChannelId", String.class);
    public static final ConfigKey<String> STATUS_CHANNEL_ID = new ConfigKey<>("StatusChannelId", String.class);

    public static final ConfigKey<String> STATUS_FORMAT = new ConfigKey<>("StatusFormat", String.class);
    public static final ConfigKey<String> SMALL_STATUS_FORMAT = new ConfigKey<>("SmallStatusFormat", String.class);
    public static final ConfigKey<String> JOIN_MESSAGE_FORMAT = new ConfigKey<>("JoinMessageFormat", String.class);
    public static final ConfigKey<String> QUIT_MESSAGE_FORMAT = new ConfigKey<>("QuitMessageFormat", String.class);
    public static final ConfigKey<String> CHAT_MESSAGE_FORMAT = new ConfigKey<>("ChatMessageFormat", String.class);
    public static final ConfigKey<String> CHAT_MESSAGE_FORMAT_JAPANIZE = new ConfigKey<>("ChatMessageFormatJapanize", String.class);

    public static final ConfigKey<Boolean> USE_JAPANIZE = new ConfigKey<>("UseJapanize", Boolean.class);
}
