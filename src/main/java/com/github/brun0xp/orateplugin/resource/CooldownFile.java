package com.github.brun0xp.orateplugin.resource;

import com.github.brun0xp.orateplugin.Main;
import org.bukkit.entity.Player;

public class CooldownFile extends AbstractFile {

    public CooldownFile(Main main, String resource) {
        super(main, resource);
    }

    public boolean isInCooldown(Player player) {
        if (!this.getFile().contains(player.getUniqueId().toString())) return false;
        long cooldown = this.getFile().getLong(player.getUniqueId().toString());
        return System.currentTimeMillis() < cooldown;
    }

    public void putInCooldown(Player player) {
        long cooldown = System.currentTimeMillis() + (this.getMain().getConfig().getInt("command-cooldown") * 60000);
        this.getFile().set(player.getUniqueId().toString(), cooldown);
        this.saveFile();
    }
}
