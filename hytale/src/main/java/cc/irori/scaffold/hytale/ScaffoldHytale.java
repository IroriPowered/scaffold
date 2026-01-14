package cc.irori.scaffold.hytale;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigProvider;
import cc.irori.scaffold.discord.util.Logs;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import org.slf4j.Logger;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScaffoldHytale extends Scaffold {

    private static final Color DISCORD_PREFIX_COLOR = new Color(0x7070FF);

    private static final Logger LOGGER = Logs.logger();

    private final ScheduledExecutorService eventExecutor = Executors.newScheduledThreadPool(1);
    private final HytaleConfig config;

    public ScaffoldHytale(HytalePlugin plugin, HytaleConfig config) {
        this.config = config;

        plugin.getEventRegistry().register(PlayerConnectEvent.class, event -> {
            eventExecutor.schedule(() -> {
                int players = Universe.get().getPlayerCount();
                this.bot().onPlayerJoin(event.getPlayerRef().getUsername(), players);
                this.bot().setPlayerCount(players);
            }, 2L, TimeUnit.SECONDS);
        });
        plugin.getEventRegistry().register(PlayerDisconnectEvent.class, event -> {
            eventExecutor.schedule(() -> {
                int players = Universe.get().getPlayerCount();
                this.bot().onPlayerQuit(event.getPlayerRef().getUsername(), players);
                this.bot().setPlayerCount(players);
            }, 2L, TimeUnit.SECONDS);
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

    @Override
    public int getCurrentPlayers() {
        return Universe.get().getPlayerCount();
    }

    @Override
    public int getMaxPlayers() {
        return HytaleServer.get().getConfig().getMaxPlayers();
    }

    @Override
    public void disable() {
        super.disable();
        eventExecutor.shutdown();
    }
}
