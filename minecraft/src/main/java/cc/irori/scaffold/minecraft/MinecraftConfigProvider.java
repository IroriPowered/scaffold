package cc.irori.scaffold.minecraft;

import cc.irori.scaffold.discord.config.ConfigKey;
import cc.irori.scaffold.discord.config.ConfigProvider;
import org.bukkit.configuration.file.FileConfiguration;

public class MinecraftConfigProvider implements ConfigProvider {

    private final FileConfiguration config;

    public MinecraftConfigProvider(FileConfiguration config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ConfigKey<T> key) {
        return (T) config.get(key.id());
    }
}
