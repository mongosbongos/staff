package me.daniel.staff.listeners;

import java.util.ArrayList;
import java.util.List;

import me.daniel.staff.Core;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezePlayer {
	public static List<Player> frozenPlayers = new ArrayList<Player>();
	
	public static void freezePlayer(Player p) {
		Core.sendMessage(p, "You have been frozen.");
		frozenPlayers.add(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 4));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 4));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 4));
	}
	
	public static void unfreezePlayer(Player p) {
		Core.sendMessage(p, "You have been unfrozen.");
		frozenPlayers.remove(p);
		p.removePotionEffect(PotionEffectType.SLOW);
		p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
		p.removePotionEffect(PotionEffectType.BLINDNESS);
	}
	
	public static boolean toggleFreezePlayer(Player p) {
		if(isFrozen(p)) {
			unfreezePlayer(p);
			return false;
		}
		freezePlayer(p);
		return true;
	}
	
	public static boolean isFrozen(Player p) {
		return frozenPlayers.contains(p);
	}
	
}