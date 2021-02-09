package me.chris.trailsgui.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {
	
	public static Inventory INV;
	
	// what the inventory will look like
	public void register() {
		//                          ALLOCATED PLAYER TO INVENTORY
		//                                      |   SIZE OF INVENTORY
		//                                      |    |            TITLE OF INVENTORY
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "" + ChatColor.BOLD + "Trails");
		
		// Putting items in the inventory
		ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
		// setting a unique meta code for that item
		ItemMeta meta = item.getItemMeta();
		// setting the name it displays when being hovered over
		meta.setDisplayName(ChatColor.YELLOW + "Totem Trail");
		item.setItemMeta(meta);
		// position in inventory for item
		inv.setItem(3, item);

		// Putting items in the inventory
		item = new ItemStack(Material.CAMPFIRE);
		// setting a unique meta code for that item
		meta = item.getItemMeta();
		// setting the name it displays when being hovered over
		meta.setDisplayName(ChatColor.RED + "Fire Trail");
		item.setItemMeta(meta);
		// position in inventory for item
		inv.setItem(5, item);
		
		// Putting items in the inventory
		item = new ItemStack(Material.BARRIER);
		// setting a unique meta code for that item
		meta = item.getItemMeta();
		// setting the name it displays when being hovered over
		meta.setDisplayName(ChatColor.RED + "Exit Trail");
		item.setItemMeta(meta);
		// position in inventory for item
		inv.setItem(8, item);
		
		// apply inventory to command
		setInventory(inv);
	}
	
	// get the name of an inventory
	public Inventory getInventory() {
		return INV;
	}
	
	private void setInventory(Inventory inv) {
		INV = inv;
	}
	
	// When player is opening inventory
	public void openInventory(Player player) {
		player.openInventory(INV);
	}
}
