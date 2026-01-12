package cc.irori.scaffold.discord;

import cc.irori.scaffold.discord.bot.BotService;
import cc.irori.scaffold.discord.config.ConfigProvider;

public abstract class Scaffold {

    private static Scaffold instance;

    private boolean enabled;
    private BotService bot;

    public Scaffold() {
        instance = this;
    }

    public void enable() {
        if (this.enabled) {
            return;
        }
        this.enabled = true;
        this.bot = new BotService(this);
    }

    public void disable() {
        if (!this.enabled) {
            return;
        }

        this.enabled = false;
        bot.shutdown();
    }

    public BotService bot() {
        return bot;
    }

    public abstract ConfigProvider config();

    public abstract void sendChatMessage(String sender, String message);

    public static Scaffold getInstance() {
        return instance;
    }
}
