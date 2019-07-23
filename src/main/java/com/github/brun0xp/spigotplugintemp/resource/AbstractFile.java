package com.github.brun0xp.spigotplugintemp.resource;

import com.github.brun0xp.spigotplugintemp.Main;
import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public abstract class AbstractFile {

    private final Main main;
    private final String resource;
    private final File file;
    private FileConfiguration newFile;

    AbstractFile(Main main, String resource) {
        this.main = main;
        this.resource = resource;
        this.file = new File(this.main.getDataFolder(), resource);
    }

    private void reloadFile() {
        this.newFile = YamlConfiguration.loadConfiguration(this.file);

        final InputStream defLangStream = this.main.getResource(this.resource);
        if (defLangStream == null) {
            return;
        }

        this.newFile.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defLangStream, Charsets.UTF_8)));
    }

    public void saveFile() {
        try {
            this.getFile().save(this.file);
        } catch (IOException e) {
            this.main.getLogger().log(Level.SEVERE, "Could not save config to " + this.file, e);
        }
    }

    public FileConfiguration getFile() {
        if (this.newFile == null) {
            this.reloadFile();
        }
        return this.newFile;
    }

}
