package me.daniel.staff.listeners;

import java.util.Random;

import me.daniel.staff.Core;
import me.daniel.staff.objects.PlayerInformation;
import me.daniel.staff.objects.StaffPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class StaffListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("staff.on")) {
			StaffPlayer.staffPlayer(p);
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		//To prevent duplicate entries in the maps/lists
		Player p = e.getPlayer();
		if(StaffPlayer.isStaff(p)) {
			StaffPlayer.unstaffPlayer(p);
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		//Do not want players in staff mode picking up stuff
		if(StaffPlayer.isStaff(e.getPlayer())) {
			e.setCancelled(true);
		}
		
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		//Do not want players in staff mode dropping the staff items
		if(StaffPlayer.isStaff(e.getPlayer())) {
			e.setCancelled(true);
		}
		
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		//Do not want players in staff mode placing blocks
		if(StaffPlayer.isStaff(e.getPlayer())) {
			Core.sendMessage(e.getPlayer(), "You cannot place blocks in staff mode.");
			e.setCancelled(true);
		}
		
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockDestroy(BlockBreakEvent e) {
		//Do not want players in staff mode breaking blocks
		if(StaffPlayer.isStaff(e.getPlayer())) {
			Core.sendMessage(e.getPlayer(), "You cannot break blocks in staff mode.");
			e.setCancelled(true);
		}
		
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		//Prevent players in staff mode from using blocks to store staff items
		if(StaffPlayer.isStaff(e.getPlayer())) {
			e.setCancelled(true);
			if(e.getPlayer().getItemInHand().equals(Core.staffItems[0])) {
				randomTeleportToPlayer(e.getPlayer());
			} else if(e.getPlayer().getItemInHand().equals(Core.staffItems[3])) {
				Bukkit.getServer().dispatchCommand(e.getPlayer(), "corereport list");
				Core.sendMessage(e.getPlayer(), "To view a report, use: /corereport admin list <number>");
			}
		}
		
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	
	//Covers damage from another entity
	@EventHandler
	public void onPlayerHurt(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(StaffPlayer.isStaff(p)) {
			e.setCancelled(true);
			return;
		}
		if(FreezePlayer.isFrozen(p)) {
			e.setCancelled(true);
			return;
		}
	}
	
	//Covers natural damage (drowning, falling, lava, etc)
	@EventHandler
	public void onPlayerHurtNatural(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(StaffPlayer.isStaff(p)) {
			e.setCancelled(true);
			return;
		}
		if(FreezePlayer.isFrozen(p)) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractAtEntityEvent e) {
		//Prevent players in staff mode from using entities to store staff items (item frames, minecart chests/furnaces, etc)
		if(StaffPlayer.isStaff(e.getPlayer())) {
			e.setCancelled(true);
			if((e.getRightClicked() instanceof ItemFrame || e.getRightClicked() instanceof Minecart)) {
				Core.sendMessage(e.getPlayer(), "You cannot use this in staff mode.");
			} else if(e.getRightClicked() instanceof Player) {
				ItemStack inHand = e.getPlayer().getItemInHand();
				if(inHand == null || inHand.getType().equals(Material.AIR)) return;
				//The right click player items, staffItems[1] = book, staffItems[2] = blaze rod
				if(inHand.equals(Core.staffItems[1])) {
					InventoryViewer.handleViewInventoryEvent(e.getPlayer(), (Player) e.getRightClicked());
					Core.sendMessage(e.getPlayer(), "Now viewing the inventory of " + e.getRightClicked().getName() + ".");
				} else if(inHand.equals(Core.staffItems[2])) {
					boolean status = FreezePlayer.toggleFreezePlayer((Player) e.getRightClicked());
					if(status) {
						Core.sendMessage(e.getPlayer(), "You have frozen " + e.getRightClicked().getName());
					} else {
						Core.sendMessage(e.getPlayer(), "You have unfrozen " + e.getRightClicked().getName());
					}
				}
			
			}
		}
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void inventoryEvent(InventoryClickEvent e) {
		//Prevents duplication of items... can't do much with them since you can't transfer items in/out of staff mode, but still :)
		if(e.getInventory().getTitle().startsWith(ChatColor.GOLD + "") && e.getInventory().getTitle().endsWith("Inventory")) {
			e.setCancelled(true);
		}
		if(FreezePlayer.isFrozen((Player)e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(FreezePlayer.isFrozen(e.getPlayer())) {
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(!FreezePlayer.isFrozen(e.getPlayer())) return;
		if(!e.getPlayer().hasPermission("staff.freeze") || !e.getMessage().startsWith("/freeze")) {
			Core.sendError(e.getPlayer(), "You cannot use commands while frozen.");
			e.setCancelled(true);
		}
	}
	
	//Utility methods
	public static void setStaffInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setContents(Core.staffInventory[0]);
		p.getInventory().setArmorContents(Core.staffInventory[1]);
		p.updateInventory();
	}
	
	public static void resetPlayerInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setContents(PlayerInformation.getPreviousInventory(p));
		p.getInventory().setArmorContents(PlayerInformation.getArmorInventory(p));
		p.updateInventory();
	}
	
	public static void randomTeleportToPlayer(Player p) {
		//Generates a random number from 0 to the number of players online
		//Then iterates through all online players
		//When the current index = the generated number, it teleports the requested player to the current player in the loop.
		int randomIndex = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size());
		int currentpos = 0;
		for(Player a : Bukkit.getServer().getOnlinePlayers()) {
			if(currentpos++ == randomIndex) {
				//Prevents teleporting to yourself.
				if(a.equals(p)) {
					currentpos--;
					continue;
				}
				p.teleport(a);
				Core.sendMessage(p, "You have been teleported to " + a.getName());
			}
		}
	}
	
}