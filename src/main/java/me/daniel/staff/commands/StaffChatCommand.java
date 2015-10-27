package me.daniel.staff.commands;

import java.util.ArrayList;
import java.util.List;

import me.daniel.staff.Core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
	
	private static List<Player> staffChatPlayers = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sc")) {
			if(!sender.hasPermission("staff.sc")) {
				Core.sendError(sender, "You do not have permission to use this command.");
				return true;
			}
			switch(args.length) {
				case 0:
					if(!(sender instanceof Player)) {
						Core.sendError(sender, "You cannot enter staff chat as console. Try /sc <message> instead.");
						return true;
					}
					Player p = (Player) sender;
					if(isInStaffChat(p)) {
						Core.sendMessage(p, "You are now talking in normal chat.");
						staffChatPlayers.remove(p);
						return true;
					}
					Core.sendMessage(p, "You are now talking in staff chat. Use /sc to return to normal chat.");
					staffChatPlayers.add(p);
					break;
				default:
					String msg = "";
					for(int i = 0; i<args.length; i++) {
						msg = msg + args[i] + " ";
					}
					sendStaffChatMessage(sender, msg);
					break;
			}
		}
		return true;
	}
	
	public static void sendStaffChatMessage(CommandSender sender, String msg) {
		String name = "CONSOLE";
		if(sender instanceof Player) name = sender.getName();
		String finalMessage = ChatColor.LIGHT_PURPLE + "StaffChat" + ChatColor.DARK_PURPLE + ">> " + ChatColor.GOLD + name + ChatColor.RESET + ": " + ChatColor.YELLOW + msg;
		for(Player a : Bukkit.getOnlinePlayers()) {
			if(a.hasPermission("staff.sc")) {
				a.sendMessage(finalMessage);
			}
		}
		Core.getInstance().getServer().getConsoleSender().sendMessage(finalMessage);
	}
	/**
	 * @deprecated see boolean isInStaffChat(Player p)
	 */
	public List<Player> getStaffChat() {
		return staffChatPlayers;
	}
	
	public static boolean isInStaffChat(Player p) {
		return staffChatPlayers.contains(p);
	}
	
}