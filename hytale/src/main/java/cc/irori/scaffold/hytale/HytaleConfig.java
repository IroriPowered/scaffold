package cc.irori.scaffold.hytale;

import cc.irori.scaffold.discord.config.ConfigKey;
import cc.irori.scaffold.discord.config.ConfigProvider;
import com.google.common.collect.ImmutableMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HytaleConfig implements ConfigProvider {

    public static final BuilderCodec<HytaleConfig> CODEC;

    private static final Map<ConfigKey<?>, Object> DEFAULT_CONFIG = ImmutableMap.<ConfigKey<?>, Object>builder()
            .put(ConfigKey.BOT_TOKEN, "YOUR_TOKEN_HERE")
            .put(ConfigKey.CHAT_CHANNEL_ID, "YOUR_CHANNEL_ID_HERE")
            .put(ConfigKey.USE_JAPANIZE, true)
            .put(ConfigKey.JOIN_MESSAGE_FORMAT, "**{PLAYER}** has joined the game.")
            .put(ConfigKey.QUIT_MESSAGE_FORMAT, "**{PLAYER}** has left the game.")
            .put(ConfigKey.CHAT_MESSAGE_FORMAT, "**{PLAYER}**: {MESSAGE}")
            .put(ConfigKey.CHAT_MESSAGE_FORMAT_JAPANIZE, "**{PLAYER}**: {MESSAGE} (`{JAPANESE}`)")
            .build();

    static {
        BuilderCodec.Builder<HytaleConfig> codec = BuilderCodec.builder(HytaleConfig.class, HytaleConfig::new);
        for (Map.Entry<ConfigKey<?>, Object> entry : DEFAULT_CONFIG.entrySet()) {
            ConfigKey<?> key = entry.getKey();
            Object value = entry.getValue();

            //noinspection unchecked
            codec.append(new KeyedCodec<Object>(key.id(), (Codec<Object>) toHytaleCodec(key.type())),
                            (config, newValue, extraInfo) -> config.values.put(key.id(), newValue),
                            (config, extraInfo) -> config.values.getOrDefault(key.id(), value)
                    )
                    .add();
        }
        CODEC = codec.build();
    }

    private final Map<String, Object> values = new HashMap<>();

    private static Codec<?> toHytaleCodec(Class<?> type) {
        if (type == String.class) {
            return Codec.STRING;
        } else if (type == Boolean.class) {
            return Codec.BOOLEAN;
        }
        throw new IllegalArgumentException("Unsupported config key type: " + type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ConfigKey<T> key) {
        return (T) values.get(key.id());
    }
}
