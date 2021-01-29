package me.chris.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin implements Listener {
	
	public Inventory inv;
	
	List<Inventory> invs = new ArrayList<Inventory>();
	public static ItemStack[] contents;
	private int itemIndex = 0;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		createTeamInv();
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("changeteam")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You cannot do this!");
				return true;
			}
			
			Player player = (Player) sender;
			//Opens the GUI
			
			player.openInventory(inv);
			return true;
		}
		
		else if (label.equalsIgnoreCase("gamble")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("No Gambling Here!");
				return true;
			}
			
			Player player = (Player) sender;
			ItemStack fee = new ItemStack(Material.DIAMOND);
			
			fee.setAmount(3);
			
			if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND) && player.getInventory().getItemInMainHand().getAmount() >= 3) {
				player.getInventory().removeItem(fee);
				// Spin the GUI !!!
				spin(player);
				return true;
			}
			
			player.sendMessage(ChatColor.DARK_RED + "You need 3 diamonds to gamble in main hand!");
			return true;
		}

		return false;
	}
	
	@EventHandler
	public void ClickonInv(InventoryClickEvent event) {
		if (!event.getInventory().equals(inv)) return;
		
		if(event.getCurrentItem() == null) return;
		
		if(event.getCurrentItem().getItemMeta() == null) return;
		
		if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		
		if (event.getSlot() == 0 && event.getCurrentItem().getType() == Material.BLUE_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.BLUE);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 1 && event.getCurrentItem().getType() == Material.RED_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.RED);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 2 && event.getCurrentItem().getType() == Material.GREEN_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.GREEN);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 3 && event.getCurrentItem().getType() == Material.ORANGE_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.ORANGE);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 4 && event.getCurrentItem().getType() == Material.PURPLE_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.PURPLE);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 5 && event.getCurrentItem().getType() == Material.CYAN_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.AQUA);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 6 && event.getCurrentItem().getType() == Material.BLACK_CONCRETE) {
			ItemStack[] armor = player.getEquipment().getArmorContents();
			
			armor = changeColorofArmor(armor, Color.BLACK);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "You changed your team!");
		}
		
		if (event.getSlot() == 8 && event.getCurrentItem().getType() == Material.BARRIER) {
			player.closeInventory();
		}
		
		return;
		
	}
	
	public ItemStack[] changeColorofArmor(ItemStack[] a, Color color) {
		for (ItemStack item : a) {
			try {
				if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
						item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS) {
					LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
					meta.setColor(color);
					item.setItemMeta(meta);
				}
			} catch (Exception e) {
				// do Nothing
			}
					
		}
		
		return a;
	}
	
	public void createTeamInv() {
		
		inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Select Team"); // only be line long so *9
		
		ItemStack item = new ItemStack(Material.BLUE_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		
		//Blue Team
		meta.setDisplayName(ChatColor.DARK_BLUE + "BLUE TEAM");
		List<String> lore = new ArrayList <String>();
		lore.add(ChatColor.GRAY + "Click to join team!");
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(0, item); // first number is the place in the line on the inventory. so 0 to 8 and the item is placed in that place
		
		//Red Team
		item.setType(Material.RED_CONCRETE);
		meta.setDisplayName(ChatColor.DARK_RED + "RED TEAM");
		item.setItemMeta(meta);
		inv.setItem(1, item);
		
		//Green Team
		item.setType(Material.GREEN_CONCRETE);
		meta.setDisplayName(ChatColor.DARK_GREEN + "GREEN TEAM");
		item.setItemMeta(meta);
		inv.setItem(2, item);
		
		//Orange Team
		item.setType(Material.ORANGE_CONCRETE);
		meta.setDisplayName(ChatColor.GOLD + "ORANGE TEAM");
		item.setItemMeta(meta);
		inv.setItem(3, item);
		
		//Purple Team
		item.setType(Material.PURPLE_CONCRETE);
		meta.setDisplayName(ChatColor.DARK_PURPLE + "PURPLE TEAM");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
		// Light Blue Team
		item.setType(Material.CYAN_CONCRETE);
		meta.setDisplayName(ChatColor.BLUE + "CYAN TEAM");
		item.setItemMeta(meta);
		inv.setItem(5, item);
		
		//Black Team
		item.setType(Material.BLACK_CONCRETE);
		meta.setDisplayName(ChatColor.GRAY + "BLACK TEAM");
		item.setItemMeta(meta);
		inv.setItem(6, item);

		// close button
		item.setType(Material.BARRIER);
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Menu");
		lore.clear();
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(8, item);
 	}
	
	public void shuffle(Inventory inv) {
		if (contents == null) {
			ItemStack[] items = new ItemStack[10];
			
			items[0] = new ItemStack(Material.COARSE_DIRT, 32);
			items[1] = new ItemStack(Material.DIAMOND, 3);
			items[2] = new ItemStack(Material.SLIME_SPAWN_EGG, 3);
			items[3] = new ItemStack(Material.EMERALD, 4);
			items[4] = new ItemStack(Material.COOKED_MUTTON, 32);
			items[5] = new ItemStack(Material.OAK_LOG, 32);
			items[6] = new ItemStack(Material.SALMON_SPAWN_EGG, 1);
			items[7] = new ItemStack(Material.NETHERITE_INGOT, 1);
			items[8] = new ItemStack(Material.NAME_TAG, 1);
			items[9] = new ItemStack(Material.IRON_PICKAXE, 2);
			
			contents = items;
		}
		
		int startingIndex = ThreadLocalRandom.current().nextInt(contents.length);
		
		for (int index = 0; index < startingIndex; index++) {
			for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
				inv.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
			}
			itemIndex++;
		}
	
		ItemStack item = new ItemStack(Material.HOPPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GRAY + "!");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
	}
	
	public void spin(final Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "" + ChatColor.BOLD +  "Good Luck!");
		shuffle(inv);
		invs.add(inv);
		
		player.openInventory(inv);
		
		Random r = new Random();
		double seconds = 7.0 + (12.0 -7.0) * r.nextDouble();
		
		new BukkitRunnable() {
			double delay = 0;
			int ticks = 0;
			boolean done = false;
			
			public void run() {
				if (done)
					return;
				ticks++;
				delay += 1 / (20 *seconds);
				if (ticks > delay * 10) {
					ticks = 0;
					
					for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
						inv.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
					}
					
					itemIndex++;
					
					if (delay >= 2.5) {
						done =  true;
						new BukkitRunnable() {
							public void run() {
								ItemStack item = inv.getItem(13);
								player.getInventory().addItem(item);
								player.updateInventory();
								player.closeInventory();
								cancel();
							}
						}.runTaskLater(main.getPlugin(main.class), 50);
						cancel();
					}
				}
			}
		}.runTaskTimer(this, 0, 1);
	}
	
	@EventHandler
	public void Clickoninvs(InventoryClickEvent event) {
		if (!invs.contains(event.getInventory())) 
			return;
		
		event.setCancelled(true);
		return;
	}
}
