package cc.irori.scaffold.minecraft;

import cc.irori.scaffold.discord.Scaffold;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MinecraftEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Scaffold.getInstance().bot().onPlayerJoin(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Scaffold.getInstance().bot().onPlayerQuit(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Scaffold.getInstance().bot().onPlayerChat(event.getPlayer().getName(), PlainTextComponentSerializer.plainText().serialize(event.message()));
    }
}
