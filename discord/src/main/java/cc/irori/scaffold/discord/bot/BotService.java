package cc.irori.scaffold.discord.bot;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigKey;
import cc.irori.scaffold.discord.japanize.Japanizer;
import cc.irori.scaffold.discord.util.Logs;
import cc.irori.scaffold.discord.util.StringUtil;
import cc.irori.shodo.ShodoAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotService {

    private static final Color EMBED_JOIN_COLOR = new Color(0xAAFF55);
    private static final Color EMBED_QUIT_COLOR = new Color(0xFF5555);

    private static final Logger LOGGER = Logs.logger();

    private final ExecutorService chatExecutor = Executors.newFixedThreadPool(1);
    private final ScheduledExecutorService statusExecutor = Executors.newScheduledThreadPool(1);

    private final Scaffold scaffold;
    private final JDA jda;
    private final StatusCacheAction statusCacheAction;

    private int cachedPlayerCount = -1;

    public BotService(Scaffold scaffold) {
        this.scaffold = scaffold;

        LOGGER.info("Starting Scaffold Discord bot...");

        this.jda = JDABuilder.createDefault(scaffold.config().get(ConfigKey.BOT_TOKEN))
                .enableIntents(Set.of(
                        GatewayIntent.MESSAGE_CONTENT
                ))
                .addEventListeners(new BotEventListener(scaffold))
                .build();

        this.statusCacheAction = new StatusCacheAction(count -> {
            String id = scaffold.config().get(ConfigKey.STATUS_CHANNEL_ID);
            if (id.isEmpty()) {
                return;
            }
            VoiceChannel channel = jda.getVoiceChannelById(id);
            if (channel == null) {
                LOGGER.warn("Tried to update status in an invalid channel");
                return;
            }

            String statusFormat = scaffold.config().get(ConfigKey.STATUS_FORMAT)
                    .replace("{ONLINE}", String.valueOf(count))
                    .replace("{MAX}", String.valueOf(scaffold.getMaxPlayers()));
            channel.getManager().setName(statusFormat).queue();
        });
    }

    public void login() {
        scaffold.bot().setPlayerCount(0);
        statusExecutor.scheduleAtFixedRate(() -> {
            scaffold.bot().setPlayerCount(scaffold.getCurrentPlayers());
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void shutdown() {
        LOGGER.info("Shutting down Scaffold Discord bot...");
        jda.shutdown();

        chatExecutor.shutdown();
        statusExecutor.shutdown();
        statusCacheAction.shutdown();
    }

    public void onPlayerJoin(String playerName, int players) {
        String id = scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID);
        if (id.isEmpty()) {
            return;
        }
        TextChannel channel = jda.getTextChannelById(id);
        if (channel == null) {
            LOGGER.warn("Tried to send message to an invalid channel");
            return;
        }

        String message = scaffold.config().get(ConfigKey.JOIN_MESSAGE_FORMAT)
                .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName));
        String footer = scaffold.config().get(ConfigKey.SMALL_STATUS_FORMAT)
                .replace("{ONLINE}", String.valueOf(players))
                .replace("{MAX}", String.valueOf(scaffold.getMaxPlayers()));

        MessageEmbed embed = new EmbedBuilder()
                .setColor(EMBED_JOIN_COLOR)
                .setDescription(message)
                .setFooter(footer)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    public void onPlayerQuit(String playerName, int players) {
        String id = scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID);
        if (id.isEmpty()) {
            return;
        }
        TextChannel channel = jda.getTextChannelById(id);
        if (channel == null) {
            LOGGER.warn("Tried to send message to an invalid channel");
            return;
        }

        String message = scaffold.config().get(ConfigKey.QUIT_MESSAGE_FORMAT)
                .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName));
        String footer = scaffold.config().get(ConfigKey.SMALL_STATUS_FORMAT)
                .replace("{ONLINE}", String.valueOf(players))
                .replace("{MAX}", String.valueOf(scaffold.getMaxPlayers()));

        MessageEmbed embed = new EmbedBuilder()
                .setColor(EMBED_QUIT_COLOR)
                .setDescription(message)
                .setFooter(footer)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    public void onPlayerChat(String playerName, String message) {
        chatExecutor.submit(() -> {
            String id = scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID);
            if (id.isEmpty()) {
                return;
            }
            TextChannel channel = jda.getTextChannelById(id);
            if (channel == null) {
                LOGGER.warn("Tried to send message to an invalid channel");
                return;
            }

            String formattedMessage = scaffold.config().get(ConfigKey.CHAT_MESSAGE_FORMAT)
                    .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName))
                    .replace("{MESSAGE}", StringUtil.escapeDiscordMarkdown(message));
            if (scaffold.config().get(ConfigKey.USE_JAPANIZE)) {
                String japanized = Japanizer.japanizeString(message);
                if (japanized != null) {
                    formattedMessage = scaffold.config().get(ConfigKey.CHAT_MESSAGE_FORMAT_JAPANIZE)
                            .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName))
                            .replace("{MESSAGE}", StringUtil.escapeDiscordMarkdown(message))
                            .replace("{JAPANESE}", StringUtil.escapeDiscordMarkdown(japanized));

                    if (scaffold.isShodoAvailable()) {
                        ShodoAPI.getInstance().broadcastMessage(playerName + ": " + japanized);
                    }
                } else {
                    if (scaffold.isShodoAvailable()) {
                        ShodoAPI.getInstance().broadcastMessage(playerName + ": " + message);
                    }
                }
            }

            channel.sendMessage(formattedMessage).queue();
        });
    }

    public void setPlayerCount(int count) {
        this.statusCacheAction.setPlayerCount(count);
    }
}
