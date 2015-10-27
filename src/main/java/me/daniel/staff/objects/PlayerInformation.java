package me.daniel.staff.objects;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInformation {
	
	public static HashMap<Player, Location> playerLocInfo = new HashMap<Player, Location>(); 				//Couldn't think of a clever way of doing this, just went the easy way
	public static HashMap<Player, ItemStack[]> playerInvContents = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, ItemStack[]> playerArmorContents = new HashMap<Player, ItemStack[]>();
	
	public static void newPlayerInfo(Player p) {
		playerLocInfo.put(p, p.getLocation());
		playerInvContents.put(p, p.getInventory().getContents());
		playerArmorContents.put(p, p.getInventory().getArmorContents());
	}
	
	public static Location getPreviousLocation(Player p) {
		if(playerLocInfo.containsKey(p)) {
			return playerLocInfo.get(p);
		}
		return null;
	}
	
	public static ItemStack[] getPreviousInventory(Player p) {
		if(playerInvContents.containsKey(p)) {
			return playerInvContents.get(p);
		}
		return null;
	}
	
	public static ItemStack[] getArmorInventory(Player p) {
		if(playerArmorContents.containsKey(p)) {
			return playerArmorContents.get(p);
		}
		return null;
	}
	
	public static void removePlayer(Player p) {
		if(playerLocInfo.containsKey(p)) {
			playerLocInfo.remove(p);
		}
		if(playerInvContents.containsKey(p)) {
			playerInvContents.remove(p);
		}
		if(playerArmorContents.containsKey(p)) {
			playerArmorContents.remove(p);
		}
	}
	
}