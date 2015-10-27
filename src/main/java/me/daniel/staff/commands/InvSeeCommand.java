package me.daniel.staff.commands;

import me.daniel.staff.Core;
import me.daniel.staff.listeners.InventoryViewer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sinvsee")) {
			if(!sender.hasPermission("staff.on") && !sender.hasPermission("staff.toggle") && !sender.hasPermission("staff.others")) {
				Core.sendError(sender, "You do not have permission to use this command.");
				return true;
			}
			if(!(sender instanceof Player)) {
				Core.sendError(sender, "You cannot use this command from the console.");
				return true;
			}
			Player psender = (Player) sender;
			switch(args.length) {
				case 0:
					Core.sendError(sender, "Usage: /sinvsee <player>");
					break;
				case 1:
					Player p = Bukkit.getPlayer(args[0]);
					if(p==null) {
							Core.sendError(sender, "Player not found: " + args[0]);
							return true;
					}
					InventoryViewer.handleViewInventoryEvent(psender, p);
					break;
				default:
					Core.sendError(sender, "Usage: /sinvsee <player>");
					break;
			}
		}
		return true;
	}
}