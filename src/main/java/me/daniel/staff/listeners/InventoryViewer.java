package me.daniel.staff.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryViewer {
	
	public static void handleViewInventoryEvent(Player clicker, Player clicked) {
		String ownerShip = "'s ";
		if(clicked.getName().substring(clicked.getName().length()-1, clicked.getName().length()).equalsIgnoreCase("s")) ownerShip = "' ";
		Inventory playerInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + clicked.getName() + ownerShip + "Inventory");
		
		int i = 0; //Index in inventory
		for(ItemStack is : clicked.getInventory().getContents()) {
			playerInventory.setItem(i++, is);
		}
		i+=13; //Empty row, three empty before armor contents (centres it)
		for(ItemStack is : clicked.getInventory().getArmorContents()) {
			playerInventory.setItem(i++, is);
		}
		clicker.openInventory(playerInventory);
	}
	
}