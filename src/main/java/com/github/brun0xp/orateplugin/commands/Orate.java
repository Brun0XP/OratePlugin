package com.github.brun0xp.orateplugin.commands;

import com.github.brun0xp.orateplugin.Main;
import com.github.brun0xp.orateplugin.commands.manager.AbstractCommand;
import com.github.brun0xp.orateplugin.resource.Message;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter(value = AccessLevel.PRIVATE)
public class Orate extends AbstractCommand {

    private Main main;

    public Orate(Main main) {
        super("orar", "orateplugin.orar", new String[]{}, new String[]{});
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player)) {
            new Message("messages.error.console").colored().send(commandSender);
            return true;
        }
        Player player = (Player) commandSender;
        if (!this.getMain().getWorldGuardWrapper().getRegionsName(player.getLocation())
                .contains(this.getMain().getConfig().getString("region-name"))) {
            new Message("messages.error.out-of-area").colored().send(player);
            return true;
        }
        if (this.getMain().getCooldown().isInCooldown(player)) {
            new Message("messages.error.cooldown").colored().send(player);
            return true;
        }

        if (this.getMain().getConfig().getBoolean("money.enable")) {
            double value = this.getMain().getConfig().getDouble("money.amount");
            new Message("messages.orate.money-received").set("value", this.getMain().getEcon().format(value))
                    .colored().send(player);
            this.getMain().getEcon().depositPlayer(player, value);
        }

        if (this.getMain().getConfig().getBoolean("item.enable")) {
            new Message("messages.orate.item-received").colored().send(player);
            ItemStack item = new ItemStack(Material.valueOf(this.getMain().getConfig().getString("item.material")),
                    this.getMain().getConfig().getInt("item.amount"),
                    (short) this.getMain().getConfig().getInt("item.data"));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    this.getMain().getConfig().getString("item.display-name")));
            List<String> lore = new ArrayList<>();
            for (String str : this.getMain().getConfig().getStringList("item.lore"))
                lore.add(ChatColor.translateAlternateColorCodes('&', str));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            player.getInventory().addItem(item);
        }
        this.getMain().getCooldown().putInCooldown(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return null;
    }
}
