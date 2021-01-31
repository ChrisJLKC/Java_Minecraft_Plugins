package me.chris.privatevault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	
	public static Map<String, ItemStack[]> menus = new HashMap<String, ItemStack[]>();
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		
		if (this.getConfig().contains("data")) {
			this.restoreInvs();
			this.getConfig().set("data", null);
			this.saveConfig();
		}
	}

	@Override
	public void onDisable() {
		if (!menus.isEmpty()) {
			this.saveInvs();
		}
	}
	
	public void saveInvs() {
		// save items in configure file
		for (Map.Entry<String, ItemStack[]> entry : menus.entrySet()) {
			this.getConfig().set("data." + entry.getKey(), entry.getValue());
		}
		this.saveConfig();
	}
	
	public void restoreInvs() {
		// collects all ID's of items
		this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
			
			// gets item in the data from the specific player and places it into the item array which is the content of the GUI
			@SuppressWarnings("unchecked")
			ItemStack[] content = ((List<ItemStack>) this.getConfig().get("data." + key)).toArray(new ItemStack[0]);
			// places the content into the GUI
			menus.put(key, content);
		});
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("pv")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You don't have a private vault!");
				return true;
			}
			
			Player player = (Player) sender;
			
			// checking and creates inventory 
			
			Inventory inv = Bukkit.createInventory(player, 54, player.getName() + "'s Private Vault");
			
			if (menus.containsKey(player.getUniqueId().toString())) {
				inv.setContents(menus.get(player.getUniqueId().toString()));
			}
			
			player.openInventory(inv);
			return true;
		}

		return false;
	}
	
	// saves private vault with specific player name
	
	@EventHandler
	public void onGUIClose(InventoryCloseEvent event) {
		if (event.getView().getTitle().contains(event.getPlayer().getName() + "'s Private Vault")) {
			menus.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
		}
	}
	
}
