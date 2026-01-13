package cc.irori.scaffold.hytale;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigProvider;
import cc.irori.scaffold.discord.util.Logs;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import org.slf4j.Logger;

import java.awt.*;

public class ScaffoldHytale extends Scaffold {

    private static final Color DISCORD_PREFIX_COLOR = new Color(0x7070FF);

    private static final Logger LOGGER = Logs.logger();

    private final HytaleConfig config;

    public ScaffoldHytale(HytalePlugin plugin, HytaleConfig config) {
        this.config = config;

        plugin.getEventRegistry().register(PlayerConnectEvent.class, event -> {
            this.bot().onPlayerJoin(event.getPlayerRef().getUsername());
        });
        plugin.getEventRegistry().register(PlayerDisconnectEvent.class, event -> {
            this.bot().onPlayerQuit(event.getPlayerRef().getUsername());
        });
        plugin.getEventRegistry().registerAsyncGlobal(PlayerChatEvent.class, future -> future.thenApply(event -> {
            this.bot().onPlayerChat(event.getSender().getUsername(), event.getContent());
            return event;
        }));
    }

    @Override
    public ConfigProvider config() {
        return config;
    }

    @Override
    public void sendChatMessage(String sender, String message) {
        Universe.get().sendMessage(Message.join(
                Message.raw("[D] @" + sender + ": ").color(DISCORD_PREFIX_COLOR),
                Message.raw(message)
        ));
    }
}
