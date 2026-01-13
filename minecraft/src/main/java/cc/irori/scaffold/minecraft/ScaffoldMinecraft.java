package cc.irori.scaffold.minecraft;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class ScaffoldMinecraft extends Scaffold {

    private final MinecraftPlugin plugin;
    private final ConfigProvider config;

    public ScaffoldMinecraft(MinecraftPlugin plugin) {
        this.plugin = plugin;
        this.config = new MinecraftConfigProvider(plugin.getConfig());

        plugin.saveDefaultConfig();
    }

    @Override
    public ConfigProvider config() {
        return config;
    }

    @Override
    public void sendChatMessage(String sender, String message) {
        Bukkit.getServer().broadcast(Component.textOfChildren(
                Component.text("⇄ @" + sender, NamedTextColor.BLUE),
                Component.space(),
                Component.text(message, NamedTextColor.GRAY)
        ));
    }

    @Override
    public int getCurrentPlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public int getMaxPlayers() {
        return Bukkit.getMaxPlayers();
    }
}
