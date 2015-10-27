package me.daniel.staff;

import java.util.Arrays;
import java.util.List;

import me.daniel.staff.commands.FreezeCommand;
import me.daniel.staff.commands.InvSeeCommand;
import me.daniel.staff.commands.RandomTeleportCommand;
import me.daniel.staff.commands.StaffChatCommand;
import me.daniel.staff.commands.StaffCommand;
import me.daniel.staff.listeners.StaffListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	
	public static Core instance;
	public static ItemStack[][] staffInventory;
	public static ItemStack[] staffItems;
	
	@Override
	public void onEnable() {
		instance = this;
		populateInventory();
		Bukkit.getPluginManager().registerEvents(new StaffListener(), this);
		getCommand("staff").setExecutor(new StaffCommand());
		getCommand("freeze").setExecutor(new FreezeCommand());
		getCommand("sc").setExecutor(new StaffChatCommand());
		getCommand("sinvsee").setExecutor(new InvSeeCommand());
		getCommand("srtp").setExecutor(new RandomTeleportCommand());
	}
	
	public static Core getInstance() {
		return instance;
	}
	
	public ItemStack[][] getStaffInventory() {
		if(staffInventory == null) {
			populateInventory();
		}
		return staffInventory;
	}
	
	public void populateInventory() {
		staffInventory = new ItemStack[2][]; //ez ragged array
		staffInventory[0] = new ItemStack[5];
		staffInventory[1] = new ItemStack[4];
		
		//Create staff items:
		staffItems = new ItemStack[5];
		staffItems[0] = newStaffItem(Material.STICK,ChatColor.WHITE + "" + ChatColor.BOLD + "Random Teleport",Arrays.asList("Right click me to teleport", "to a random player."));
		staffItems[1] = newStaffItem(Material.BOOK, ChatColor.GRAY + "" + ChatColor.BOLD + "View Inventory", Arrays.asList("Right click a player with me", "to view their inventory."));
		staffItems[2] = newStaffItem(Material.BLAZE_ROD, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Freeze Player", Arrays.asList("Right click a player with me", "to freeze that player"));
		staffItems[3] = newStaffItem(Material.FIREBALL, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "View Reports", Arrays.asList("Right click me to view reports"));
		staffItems[4] = newStaffItem(Material.COOKED_BEEF, ChatColor.GOLD + "" + ChatColor.BOLD + "Feed", Arrays.asList("Right click me to feed yourself."));
		
		//Inv contents
		staffInventory[0][0] = staffItems[0];
		staffInventory[0][1] = staffItems[1];
		staffInventory[0][2] = staffItems[2];
		staffInventory[0][3] = staffItems[3];
		staffInventory[0][4] = staffItems[4];
		
		//Armor contents
		staffInventory[1][0] = new ItemStack(Material.AIR, 1);
		staffInventory[1][1] = new ItemStack(Material.AIR, 1);
		staffInventory[1][2] = new ItemStack(Material.AIR, 1);
		staffInventory[1][3] = new ItemStack(Material.AIR, 1);
		
	}
	
	public ItemStack newStaffItem(Material item, String name, List<String> lore) {
		ItemStack kek = new ItemStack(item,1);
		ItemMeta im = kek.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		kek.setItemMeta(im);
		return kek;
	}
	
	public static void sendMessage(Player p, String message) {
		//Wrapper for plugin messages
		if(p==null) return; //This will be called when a player leaves, possible NPE sending a message to a player that doesn't exist
		p.sendMessage(ChatColor.AQUA + "Staff" + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + message);
	}
	
	public static void sendMessage(CommandSender s, String message) {
		//Wrapper for plugin messages
		s.sendMessage(ChatColor.AQUA + "Staff" + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + message);
	}
	
	public static void sendError(CommandSender s, String message) {
		s.sendMessage(ChatColor.RED + "Staff" + ChatColor.DARK_GRAY + ">> " + ChatColor.DARK_RED + message);
	}
	
}