package cc.irori.scaffold.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftPlugin extends JavaPlugin {

    private ScaffoldMinecraft scaffold;

    @Override
    public void onEnable() {
        this.scaffold = new ScaffoldMinecraft(this);
        this.scaffold.enable();

        Bukkit.getPluginManager().registerEvents(new MinecraftEventListener(), this);
    }

    @Override
    public void onDisable() {
        this.scaffold.disable();
    }
}
