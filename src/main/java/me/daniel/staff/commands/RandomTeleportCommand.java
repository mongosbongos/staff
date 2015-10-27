package me.daniel.staff.commands;

import me.daniel.staff.Core;
import me.daniel.staff.listeners.StaffListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTeleportCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("srtp")) {
			if(!(sender instanceof Player)) {
				Core.sendMessage(sender, "You cannot use this command from the console.");
				return true;
			}		
			if(!sender.hasPermission("staff.on") && !sender.hasPermission("staff.toggle") && !sender.hasPermission("staff.others")) {
				Core.sendError(sender, "You do not have permission to use this command.");
				return true;
			}
			StaffListener.randomTeleportToPlayer((Player) sender);
		}
		return true;
	}
	
}