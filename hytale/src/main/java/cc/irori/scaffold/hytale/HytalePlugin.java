package cc.irori.scaffold.hytale;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HytalePlugin extends JavaPlugin {

    private final Config<HytaleConfig> config;

    private ScaffoldHytale scaffold;

    public HytalePlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        this.config = withConfig("Scaffold-Hytale", HytaleConfig.CODEC);
    }

    @Override
    protected void start() {
        // Maybe I don't want these blocking?
        config.load().join();
        config.save().join();

        this.scaffold = new ScaffoldHytale(this, config.get());
        this.scaffold.enable();
    }

    @Override
    protected void shutdown() {
        this.scaffold.disable();
    }
}
