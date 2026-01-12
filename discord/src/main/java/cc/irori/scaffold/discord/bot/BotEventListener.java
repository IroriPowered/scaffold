package cc.irori.scaffold.discord.bot;

import cc.irori.scaffold.discord.Scaffold;
import cc.irori.scaffold.discord.config.ConfigKey;
import cc.irori.scaffold.discord.util.Logs;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class BotEventListener implements EventListener {

    private static final Logger LOGGER = Logs.logger();

    private final Scaffold scaffold;

    public BotEventListener(Scaffold scaffold) {
        this.scaffold = scaffold;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent readyEvent) {
            LOGGER.info("Bot is ready! Logged in as: {}", readyEvent.getJDA().getSelfUser().getAsTag());
        } else if (event instanceof MessageReceivedEvent messageEvent) {
            if (messageEvent.getChannel().getId().equals(scaffold.config().get(ConfigKey.CHAT_CHANNEL_ID))) {
                if (messageEvent.getAuthor().isBot()) {
                    return;
                }
                Member member = messageEvent.getMember();
                if (member == null) {
                    return;
                }
                scaffold.sendChatMessage(member.getEffectiveName(), messageEvent.getMessage().getContentDisplay());
            }
        }
    }
}
