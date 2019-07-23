package com.github.brun0xp.spigotplugintemp.commands;

import com.github.brun0xp.spigotplugintemp.commands.manager.AbstractCommand;
import com.github.brun0xp.spigotplugintemp.resource.Message;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Example extends AbstractCommand {

    public Example() {
        super("example", "template.example", new String[]{}, new String[]{"ex"});
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] strings) {
        new Message("messages.example").colored().send(commandSender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return null;
    }
}
