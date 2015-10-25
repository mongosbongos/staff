package me.daniel.staff.commands;

import me.daniel.staff.Core;
import me.daniel.staff.listeners.FreezePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("freeze")) {
			switch(args.length) {
				case 0:
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(FreezePlayer.isFrozen(p)) {
							if(p.hasPermission("staff.freeze")) {
								FreezePlayer.unfreezePlayer(p);
							} else {
								Core.sendError(p, "You do not have permission to use this command.");
							}
						} else {
							Core.sendError(p, "Usage: /freeze [player]");
						}
					} else {
						Core.sendError(sender, "Incorrect use of /freeze. Usage: /freeze [player]");
					}
					break;
				case 1:
					if(!sender.hasPermission("staff.freeze")) {
						Core.sendError(sender, "You do not have permission to use this command.");
					}
					Player p = Bukkit.getPlayer(args[0]);
					if(p==null) {
						Core.sendError(sender, "Player not found: " + args[0]);
						return true;
					}
					boolean status = FreezePlayer.toggleFreezePlayer(p);
					if(status) {
						Core.sendMessage(sender, "You have frozen " + args[0]);
					} else {
						Core.sendMessage(sender, "You have unfrozen " + args[0]);
					}
					break;
				default:
					if(!sender.hasPermission("staff.freeze")) {
						Core.sendError(sender, "You do not have permission to use this command.");
						return true;
					}
					Core.sendMessage(sender, "Usage: /freeze [player]");
					break;
					
			}
		}
		return true;
	}
	
}