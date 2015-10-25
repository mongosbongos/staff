package me.daniel.staff.objects;

import java.util.ArrayList;
import java.util.List;

import me.daniel.staff.Core;
import me.daniel.staff.listeners.StaffListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffPlayer {

	public static List<Player> staffPlayers = new ArrayList<Player>();
	
	public static void staffPlayer(Player p) {
		Core.sendMessage(p, "You are now in staff mode.");
		staffPlayers.add(p);
		PlayerInformation.newPlayerInfo(p);
		handlePlayer(p, true);
	}
	
	public static void unstaffPlayer(Player p) {
		Core.sendMessage(p, "You are no longer in staff mode.");
		handlePlayer(p, false);
		PlayerInformation.removePlayer(p);
		staffPlayers.remove(p);
	}
	
	public static boolean isStaff(Player p) {
		return staffPlayers.contains(p);
	}
	
	private static void handlePlayer(Player p, boolean kek) {
		p.setCanPickupItems(kek);
		p.setAllowFlight(kek);
		setPlayersCanSeePlayer(p, kek);
		if(kek) {
			StaffListener.setStaffInventory(p);
		} else {
			StaffListener.resetPlayerInventory(p);
			p.teleport(PlayerInformation.getPreviousLocation(p));
			PlayerInformation.removePlayer(p);
		}
	}
	
	private static void setPlayersCanSeePlayer(Player subject, boolean kek) {
		for(Player a : Bukkit.getOnlinePlayers()) {
			if(kek) {
				a.hidePlayer(subject);
			} else {
				a.showPlayer(subject);
			}
		}
	}

}