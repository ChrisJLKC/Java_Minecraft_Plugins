package me.chris.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin implements Listener {
	
	Map<String, Long> cooldowns = new HashMap<String, Long>();
	
	public List<String> list = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this); // Listen to events that have EventHandler above them
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("godboots")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Sorry console, no items for you!");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (player.getInventory().firstEmpty() == -1) { // When Inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getBoots());
				player.sendMessage(ChatColor.GOLD + "The Mincraft Gods dropped a gift near you");
				return true;
			}
			player.getInventory().addItem(getBoots());
			player.sendMessage(ChatColor.GOLD + "The Minecraft Gods gave you a gift");
			return true;
			
		}
		
		else if(label.equalsIgnoreCase("supertrident")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Sorry console, no items for you!");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (cooldowns.containsKey(player.getName())) {
				// player inside HashMap string array
				if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
					// if they have time left on cool down
					long timeleft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
					player.sendMessage(ChatColor.RED + "Trident will be ready in " + timeleft + " second(s)");
					
					return true;
				}
			}
			
			cooldowns.put(player.getName(), System.currentTimeMillis() + (300 * 1000));
			
			
			if (player.getInventory().firstEmpty() == -1) { // When Inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getTrident());
				player.sendMessage(ChatColor.GOLD + "The Mincraft Gods dropped a gift near you");
				return true;
			}
			player.getInventory().addItem(getTrident());
			player.sendMessage(ChatColor.GOLD + "The Minecraft Gods gave you a gift");
			return true;
			
		}
		return false;
	}
	
	public ItemStack getBoots() {
		
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta meta = boots.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Boots of Leaping");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Boots made for the Minecraft Gods");
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		boots.setItemMeta(meta);
		
		return boots;
	}
	
	public ItemStack getTrident() {
		
		ItemStack boots = new ItemStack(Material.TRIDENT);
		ItemMeta meta = boots.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Ancient Trident");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Right Click) &a&oSpawn minions"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Left Click) &a&oShoot explosives"));
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.LOYALTY, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		boots.setItemMeta(meta);
		
		return boots;
	}
	
	
	@EventHandler
	public void onJumpwithBoots(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		
		// these if statements are to check if the player has boots, and if there are our type of boots, and also allow them to not enchant them even more with another block for example, Anvil
		// the last if statement is checking if the player is jumping from a block
		
		if (player.getInventory().getBoots() != null) {
			
			if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping")) {
				
				if (player.getInventory().getBoots().getItemMeta().hasLore()) {
					
					if (event.getFrom().getY() < event.getTo().getY() && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
						
						player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onFallwithBoots(EntityDamageEvent event) {
		
		// negates any type of fall damage when wearing boots
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (event.getCause() == DamageCause.FALL) {
				if (player.getInventory().getBoots() != null) {
					
					if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping")) {
						
						if (player.getInventory().getBoots().getItemMeta().hasLore()) {
							
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onTridentClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT)) {
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();
				
				// Right Click operation
				
				if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!list.contains(player.getName()))
						list.add(player.getName());
					return;
				}
				
				// Left Click operation
				
				if (event.getAction() == Action.LEFT_CLICK_AIR) {
					player.launchProjectile(Fireball.class);
				}
			}
		}
		
		else if (list.contains(event.getPlayer().getName())) {
			list.remove(event.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onGroundTridentZombieSpawn(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.TRIDENT) {
			if (event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();
				if (list.contains(player.getName())) {
					Location loc = event.getEntity().getLocation();
					loc.setY(loc.getY() + 1);
					
					for (int i = 1; i < 4; i++) {
						loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
						loc.setX(loc.getX() + i);
					}
				}
			}
			
		}
	}
}
