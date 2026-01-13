package cc.irori.scaffold.minecraft;

import cc.irori.scaffold.discord.Scaffold;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MinecraftEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int players = Bukkit.getOnlinePlayers().size();
        Scaffold.getInstance().bot().onPlayerJoin(event.getPlayer().getName(), players);
        Scaffold.getInstance().bot().setPlayerCount(players);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        int players = Bukkit.getOnlinePlayers().size() - 1;
        Scaffold.getInstance().bot().onPlayerQuit(event.getPlayer().getName(), players);
        Scaffold.getInstance().bot().setPlayerCount(players);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Scaffold.getInstance().bot().onPlayerChat(event.getPlayer().getName(), PlainTextComponentSerializer.plainText().serialize(event.message()));
    }
}
