package me.daniel.staff.commands;

import me.daniel.staff.Core;
import me.daniel.staff.objects.StaffPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((!sender.hasPermission("staff.toggle") && (!sender.hasPermission("staff.others")))) {
			Core.sendError(sender, "You do not have permission to use this command.");
			return true;
		}
		switch(args.length) {
			//Toggle staff mode for that player
			case 0:
				if(!(sender instanceof Player)) {
					Core.sendError(sender, "You can not enter staff mode from console.");
					return true;
				}
				Player p = (Player) sender;
				if(StaffPlayer.isStaff(p)) {
					StaffPlayer.unstaffPlayer(p);
					return true;
				}
				StaffPlayer.staffPlayer(p);
				break;
			//Toggle staff mode for other players
			case 1:
				p = Bukkit.getPlayer(args[0]);
				if(p==null) {
					Core.sendError(sender, "Player not found: " + args[0]);
					return true;
				}
				if(StaffPlayer.isStaff(p)) {
					StaffPlayer.unstaffPlayer(p);
					Core.sendMessage(sender, "Player " + p.getName() + " is no longer in staff mode.");
				} else {
					StaffPlayer.staffPlayer(p);
					Core.sendMessage(sender, "Player " + p.getName() + " is now in staff mode.");
				}
				break;
			default:
				Core.sendError(sender, "Usage: /staff [player]");
				break;
		}
		return true;
	}
	
}