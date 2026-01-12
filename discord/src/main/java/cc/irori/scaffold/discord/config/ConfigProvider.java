package cc.irori.scaffold.discord.config;

public interface ConfigProvider {

    <T> T get(ConfigKey<T> key);
}
