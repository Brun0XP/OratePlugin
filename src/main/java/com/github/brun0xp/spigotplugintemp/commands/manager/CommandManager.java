package com.github.brun0xp.spigotplugintemp.commands.manager;

import com.github.brun0xp.spigotplugintemp.Main;
import com.github.brun0xp.spigotplugintemp.commands.Example;
import com.github.brun0xp.spigotplugintemp.resource.Message;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class CommandManager implements CommandExecutor, TabCompleter {

    private Main main;

    private Set<AbstractCommand> abstractCommands = new HashSet<>();

    public CommandManager(Main main){
        this.main = main;
        this.getAbstractCommands().add(new Example());

        for (AbstractCommand abstractCommand : this.getAbstractCommands()) {
            this.getMain().getCommand(abstractCommand.getName()).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        AbstractCommand abstractCommand = this.getCommand(command.getName());
        if (abstractCommand == null)
            return false;

        try {
            if (commandSender.hasPermission(abstractCommand.getPermission())) {
                if (!abstractCommand.onCommand(commandSender, strings))
                    new Message("messages.error.usage").set("usage", abstractCommand.getUsage())
                            .colored().send(commandSender);
            } else {
                new Message("messages.error.permission").set("permission", abstractCommand.getPermission())
                        .colored().send(commandSender);
            }
        } catch (Exception e){
            new Message("messages.error.unknown").colored().send(commandSender);
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings) {
        AbstractCommand abstractCommand = this.getCommand(command.getName());
        if (abstractCommand == null) {
            return null;
        }
        try {
            if (commandSender.hasPermission(abstractCommand.getPermission())) {
                return abstractCommand.onTabComplete(commandSender, strings);
            }
        } catch (Exception e){
            new Message("messages.error.unknown").colored().send(commandSender);
            e.printStackTrace();
        }
        return null;
    }

    private AbstractCommand getCommand(String name) {
        for (AbstractCommand command : this.getAbstractCommands()) {
            if (command.getName().equalsIgnoreCase(name))
                return command;
            for (String aliases : command.getAliases())
                if (name.equalsIgnoreCase(aliases))
                    return command;
        }
        return null;
    }

}
