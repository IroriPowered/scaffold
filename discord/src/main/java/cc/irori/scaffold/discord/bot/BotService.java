package cc.irori.scaffold.discord.bot;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigKey;
import cc.irori.scaffold.discord.util.Logs;
import cc.irori.scaffold.discord.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;

import java.awt.*;
import java.util.Set;

public class BotService {

    private static final Color EMBED_JOIN_COLOR = new Color(0xAAFF55);
    private static final Color EMBED_QUIT_COLOR = new Color(0xFF5555);

    private static final Logger LOGGER = Logs.logger();

    private final Scaffold scaffold;
    private final JDA jda;

    public BotService(Scaffold scaffold) {
        this.scaffold = scaffold;

        LOGGER.info("Starting Scaffold Discord bot...");
        this.jda = JDABuilder.createDefault(scaffold.config().get(ConfigKey.BOT_TOKEN))
                .enableIntents(Set.of(
                        GatewayIntent.MESSAGE_CONTENT
                ))
                .addEventListeners(new BotEventListener(scaffold))
                .build();
    }

    public void shutdown() {
        LOGGER.info("Shutting down Scaffold Discord bot...");
        jda.shutdownNow();
    }

    public void onPlayerJoin(String playerName) {
        TextChannel channel = jda.getTextChannelById(scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID));
        if (channel == null) {
            LOGGER.warn("Tried to send message to an invalid channel");
            return;
        }

        String message = scaffold.config().get(ConfigKey.JOIN_MESSAGE_FORMAT)
                .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName));
        MessageEmbed embed = new EmbedBuilder()
                .setColor(EMBED_JOIN_COLOR)
                .setDescription(message)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    public void onPlayerQuit(String playerName) {
        TextChannel channel = jda.getTextChannelById(scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID));
        if (channel == null) {
            LOGGER.warn("Tried to send message to an invalid channel");
            return;
        }

        String message = scaffold.config().get(ConfigKey.QUIT_MESSAGE_FORMAT)
                .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName));
        MessageEmbed embed = new EmbedBuilder()
                .setColor(EMBED_QUIT_COLOR)
                .setDescription(message)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    public void onPlayerChat(String playerName, String message) {
        TextChannel channel = jda.getTextChannelById(scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID));
        if (channel == null) {
            LOGGER.warn("Tried to send message to an invalid channel");
            return;
        }

        String formattedMessage = scaffold.config().get(ConfigKey.CHAT_MESSAGE_FORMAT)
                .replace("{PLAYER}", StringUtil.escapeDiscordMarkdown(playerName))
                .replace("{MESSAGE}", StringUtil.escapeDiscordMarkdown(message));
        channel.sendMessage(formattedMessage).queue();
    }
}
