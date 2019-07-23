package com.github.brun0xp.orateplugin;

import com.github.brun0xp.orateplugin.commands.manager.CommandManager;
import com.github.brun0xp.orateplugin.resource.CooldownFile;
import com.github.brun0xp.orateplugin.resource.LanguageFile;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class Main extends JavaPlugin {

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private static Main main;

    private LanguageFile language;
    private CooldownFile cooldown;
    private CommandManager commandManager;
    @Getter
    protected static Economy econ = null;
    @Getter
    private static WorldGuardPlugin worldGuard;
    private WorldGuardWrapper worldGuardWrapper;

    @Override
    public void onEnable() {
        this.setMain(this);
        this.setupFiles();
        this.setupWorldGuard();
        this.setupEconomy();
        this.setCommandManager(new CommandManager(Main.getMain()));
        this.setWorldGuardWrapper(new WorldGuardWrapper());
    }

    @Override
    public void onDisable() {

    }

    private void setupFiles() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.setLanguage(new LanguageFile(Main.getMain(), "lang-" + this.getConfig().getString("language") + ".yml"));
        this.getLanguage().getFile().options().copyDefaults(true);
        this.getLanguage().saveFile();
        this.setCooldown(new CooldownFile(Main.getMain(), "cooldown.yml"));
        this.getCooldown().getFile().options().copyDefaults(true);
        this.getCooldown().saveFile();
    }

    protected boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy) rsp.getProvider();
        return econ != null;
    }

    private boolean setupWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            worldGuard = null; // Maybe you want throw an exception instead
        }
        worldGuard =  (WorldGuardPlugin) plugin;
        return worldGuard != null;
    }

}
