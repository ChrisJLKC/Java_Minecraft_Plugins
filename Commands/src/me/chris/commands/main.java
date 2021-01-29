package me.chris.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("mystats").setExecutor(new statcommand());
		this.getCommand("mystats").setTabCompleter(new stattab());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("hello")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("hello.use")) {
					player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Hey Welcome to the server!");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1H&2a&3v&4e &5f&6u&7n&8!"));
					return true;
				}
				player.sendMessage(ChatColor.RED + "You do not have permission!");
				return true;
			}
			
			else {
				sender.sendMessage("Hey console!");
				return true;
			}
		}
		
		else if(label.equalsIgnoreCase("commandassist")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("commandassist.use")) {
					player.sendMessage(ChatColor.RED + "Remember the / at the beginning!");
					player.sendMessage(ChatColor.DARK_PURPLE + "hello" + ChatColor.WHITE + " - Welcomes you to the server");
					player.sendMessage(ChatColor.DARK_PURPLE + "launch" + ChatColor.WHITE + " - Launches you into the sky, below 100 blocks");
					player.sendMessage(ChatColor.DARK_PURPLE + "godboots" + ChatColor.WHITE + " - Gives you leaping / no fall damage boots");
					player.sendMessage(ChatColor.DARK_PURPLE + "doctor" + ChatColor.WHITE + " - Instant Health");
					player.sendMessage(ChatColor.DARK_PURPLE + "supertrident" + ChatColor.WHITE + " - Gives you a powerful trident");
					player.sendMessage(ChatColor.DARK_PURPLE + "changeteam" + ChatColor.WHITE + " - changes the color of your leather armor");
					player.sendMessage(ChatColor.DARK_PURPLE + "gamble" + ChatColor.WHITE + " - Gamble using 3 diamonds");
					player.sendMessage(ChatColor.DARK_PURPLE + "skull" + ChatColor.WHITE + " - Get anyones head in Mojang");
					player.sendMessage(ChatColor.DARK_PURPLE + "randomblock" + ChatColor.WHITE + " - resets random block collection");
					player.sendMessage(ChatColor.DARK_PURPLE + "mystats" + ChatColor.WHITE + " - shows stats of player");
					return true;
				}
				player.sendMessage(ChatColor.RED + "You do not have permission!");
				return true;
			}
			
			else {
				sender.sendMessage("Invalid Assist");
				return true;
			}
		}
		
		else if(label.equalsIgnoreCase("launch")) {
			if ((!(sender instanceof Player))) {
				sender.sendMessage("console goes flying");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Zooooom!");
				player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
				
				return true;
			}
			
			if (isNum(args[0])) {
				if (args.length <= 1) {
					player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Zooooom!");
					player.setVelocity(player.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(2));
					
					return true;
				}
				
				else {
					player.sendMessage(ChatColor.RED + "Too Many Arguments!");
					
					return true;
				}
			}
			
			else { 
				player.sendMessage(ChatColor.RED + "Usage: /launch <number-value>");
				
				return true;
			}
			
		}
		
		
		else if(label.equalsIgnoreCase("skull")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You cannot do this!");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (args.length == 0) {
				// Give player head
				player.sendMessage(ChatColor.GOLD + "You have been given the skull of " + ChatColor.RED + "" + player.getName());
				
				if (player.getInventory().firstEmpty() == -1) { // When Inventory is full
					Location loc = player.getLocation();
					World world = player.getWorld();
					
					world.dropItemNaturally(loc, getPlayerHead(player.getName()));
					return true;
				}
				
				player.getInventory().addItem(getPlayerHead(player.getName()));
				return true;
			}
			
			player.sendMessage(ChatColor.GOLD + "You have been given the skull of " + ChatColor.RED + "" + args[0]);
			
			if (player.getInventory().firstEmpty() == -1) { // When Inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getPlayerHead(args[0]));
				return true;
			}
			
			player.getInventory().addItem(getPlayerHead(args[0]));
			return true;
		}
		
		else {
			sender.sendMessage("Invaid Command");
			return true;
		}
		
	}
	
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getPlayerHead(String player) {
		
		// checking if item names are compatible with the version of server
		boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
		
		Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
		
		ItemStack item = new ItemStack(type, 1);
		
		if(!isNewVersion) {
			item.setDurability((short) 3);
		}
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(player);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
