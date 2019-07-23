package com.github.brun0xp.spigotplugintemp.commands.manager;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class AbstractCommand {

    protected String name;
    protected String permission;
    protected String[] args;
    protected String[] aliases;

    public AbstractCommand(String name, String permission, String[] args, String[] aliases) {
        this.name = name;
        this.permission = permission;
        this.args = args;
        this.aliases = aliases;
    }

    public abstract boolean onCommand(CommandSender commandSender, String[] strings);

    public abstract List<String> onTabComplete(CommandSender commandSender, String[] strings);

    public String getUsage() {
        return "/" + this.getName() + String.join(" ", args);
    }
}
