package com.github.brun0xp.spigotplugintemp;

import com.github.brun0xp.spigotplugintemp.commands.manager.CommandManager;
import com.github.brun0xp.spigotplugintemp.database.Database;
import com.github.brun0xp.spigotplugintemp.database.MySQL;
import com.github.brun0xp.spigotplugintemp.database.SQLite;
import com.github.brun0xp.spigotplugintemp.resource.LanguageFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class Main extends JavaPlugin {

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private static Main main;

    private LanguageFile language;
    private CommandManager commandManager;
    private Database database;

    @Override
    public void onEnable() {
        this.setMain(this);
        this.setupFiles();
        this.setupDatabase();
        this.setCommandManager(new CommandManager(Main.getMain()));
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
    }

    private boolean setupDatabase() {
        if (this.getConfig().getBoolean("mysql.enable")) {
            String host = this.getConfig().getString("mysql.host");
            String port = this.getConfig().getString("mysql.port");
            String database = this.getConfig().getString("mysql.database");
            String username = this.getConfig().getString("mysql.username");
            String password = this.getConfig().getString("mysql.password");
            this.setDatabase(new MySQL(host, port, database, username, password));
            return true;
        } else {
            this.setDatabase(new SQLite(Main.getMain(), "database.db"));
            return false;
        }
    }

}
