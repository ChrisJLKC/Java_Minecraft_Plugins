package me.chris.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin implements Listener {
	
	public static Inventory kits;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		createInventory();
	}

	@Override
	public void onDisable() {

	}
	
	
	// kit GUI
	private void createInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Kits");
		
		ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GRAY + "Noob Kit");
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.RED + "Click here to get the kit!");
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		inv.setItem(3, item);
		
		item.setType(Material.IRON_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Iron Kit");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
		item.setType(Material.DIAMOND_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Diamond Kit");
		item.setItemMeta(meta);
		inv.setItem(5, item);
		
		item.setType(Material.NETHERITE_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Netherite Kit");
		item.setItemMeta(meta);
		inv.setItem(6, item);
		
		kits = inv;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("kits")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("No kits for you!");
				return true;
			}
			
			Player player = (Player) sender;
			
			player.openInventory(kits);
			
			return true;
 		}
		
		
		return false;
	}
	
	@EventHandler
	public void onClickOnKit(InventoryClickEvent event) {
		// if not in the kits inventory, kick them out of the listener
		if (!event.getView().getTitle().contains("Kits")) return;
		// if item is nothing
		if (event.getCurrentItem() == null) return;
		// if item is not specified in our meta's in the private void createInventory()
		if (event.getCurrentItem().getItemMeta() == null) return;
		// once we know that we are in the right inventory, then we can process who clicked the item and which one
		Player player = (Player) event.getWhoClicked();
		// When a player clicks on a block we don't want them taking the block out of the inventory
		event.setCancelled(true);
		// if the item in the player's inventory is clicked, we don't want anything to happen or run anything which is in the slot in the players
		// GUI which is the same as the kit GUI, so we don't get false triggers of the kit spawning.
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;
		// This is the definition of the basic kit
		if (event.getSlot() == 3) {
			// making sure that player can use this kit
			if (!player.hasPermission("kits.noob")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use these kit!");
				return;
			}
			// dropping the chest in front of them, with basic kit inside
			this.dropChest(player, this.getNoobKit());
			
			// making sure that the user doesn't have a block in kit GUI in their inventory
			player.closeInventory();
			player.updateInventory();
			return;
		}
		
		// This is the definition of the iron kit
		if (event.getSlot() == 4) {
			// making sure that player can use this kit
			if (!player.hasPermission("kits.iron")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use these kit!");
				return;
			}
			// dropping the chest in front of them, with iron kit inside
			this.dropChest(player, this.getIronKit());
			
			// making sure that the user doesn't have a block in kit GUI in their inventory
			player.closeInventory();
			player.updateInventory();
			return;
		}
		
		// This is the definition of the diamond kit
		if (event.getSlot() == 5) {
			// making sure that player can use this kit
			if (!player.hasPermission("kits.diamond")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use these kit!");
				return;
			}
			// dropping the chest in front of them, with diamond kit inside
			this.dropChest(player, this.getDiamondKit());

			// making sure that the user doesn't have a block in kit GUI in their inventory
			player.closeInventory();
			player.updateInventory();
			return;
		}
		
		// This is the definition of the nether kit
		if (event.getSlot() == 6) {
			// making sure that player can use this kit
			if (!player.hasPermission("kits.netherite")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use these kit!");
				return;
			}
			// dropping the chest in front of them, with nether kit inside
			this.dropChest(player, this.getNetheriteKit());

			// making sure that the user doesn't have a block in kit GUI in their inventory
			player.closeInventory();
			player.updateInventory();
			return;
		}
 	}
	
	
	// Applying drop chest function
	private void dropChest(Player player, ItemStack[] items) {
		// As we don't know where the player is or what way they are facing, we leave it as null.
		Location chest = null;
		// specifying direction of the player in certain directions
		// As the direction depends on the X and Z axis in mine craft, we can specify where we should get the location of the block to spawn the kit.
		if (player.getFacing() == BlockFace.NORTH) {
			//                               X Y Z
			chest = player.getLocation().add(0,0,-1);
		}
		
		if (player.getFacing() == BlockFace.SOUTH) {
            //                               X Y Z
			chest = player.getLocation().add(0,0,1);
		}
		
		if (player.getFacing() == BlockFace.EAST) {
            //                               X Y Z
			chest = player.getLocation().add(1,0,0);
		}
		
		if (player.getFacing() == BlockFace.WEST) {
            //                               X Y Z
			chest = player.getLocation().add(-1,0,0);
		}
		
		// Making sure the block the player is facing is air not like a block of some kind
		if (chest.getBlock().getType() != Material.AIR) {
			// then say that you can't spawn the chest here
			player.sendMessage(ChatColor.RED + "Cannot open kit here!");
			return;
		}
		
		// getting the block in front of the player, dependent on location
		Block block = chest.getBlock();
		// now set the material above it to a chest
		block.setType(Material.CHEST);
		// making a variable to see the state of the chest and to put items in it
		Chest c = (Chest) block.getState();
		// adding items to chest
		c.getInventory().addItem(items);
	}
	
	// getting the items for the basic kit
	private ItemStack[] getNoobKit() {
		//                                    ITEM       NO OF ITEMS
		ItemStack[] items = {new ItemStack(Material.COAL, 16),
							 new ItemStack(Material.LEATHER_HELMET),
							 new ItemStack(Material.LEATHER_CHESTPLATE),
							 new ItemStack(Material.LEATHER_LEGGINGS),
							 new ItemStack(Material.LEATHER_BOOTS),
							 new ItemStack(Material.WOODEN_SWORD),
							 new ItemStack(Material.WOODEN_AXE),
							 new ItemStack(Material.WOODEN_PICKAXE),
							 new ItemStack(Material.WOODEN_SHOVEL),
							 new ItemStack(Material.WOODEN_HOE),
							 new ItemStack(Material.COOKED_BEEF, 32)
							};
		return items;
	}
	
	// getting the items for the iron kit
	private ItemStack[] getIronKit() {
		//                                    ITEM       NO OF ITEMS
		ItemStack[] items = {new ItemStack(Material.COAL, 32),
							 new ItemStack(Material.IRON_HELMET),
							 new ItemStack(Material.IRON_CHESTPLATE),
							 new ItemStack(Material.IRON_LEGGINGS),
							 new ItemStack(Material.IRON_BOOTS),
							 new ItemStack(Material.IRON_SWORD),
							 new ItemStack(Material.IRON_AXE),
							 new ItemStack(Material.IRON_PICKAXE),
							 new ItemStack(Material.IRON_SHOVEL),
							 new ItemStack(Material.IRON_HOE),
							 new ItemStack(Material.COOKED_BEEF, 64)
							};
		return items;
	}
	
	// getting the items for the diamond kit
	private ItemStack[] getDiamondKit() {
		//                                    ITEM       NO OF ITEMS
		ItemStack[] items = {new ItemStack(Material.COAL, 64),
							 new ItemStack(Material.DIAMOND_HELMET),
							 new ItemStack(Material.DIAMOND_CHESTPLATE),
							 new ItemStack(Material.DIAMOND_LEGGINGS),
							 new ItemStack(Material.DIAMOND_BOOTS),
							 new ItemStack(Material.DIAMOND_SWORD),
							 new ItemStack(Material.DIAMOND_AXE),
							 new ItemStack(Material.DIAMOND_PICKAXE),
							 new ItemStack(Material.DIAMOND_SHOVEL),
							 new ItemStack(Material.DIAMOND_HOE),
							 new ItemStack(Material.COOKED_BEEF, 64),
							 new ItemStack(Material.COOKED_BEEF, 32)
							};
		return items;
	}
	
	// getting the items for the nether kit
	private ItemStack[] getNetheriteKit() {
		
		// Setting enchantment to different tools
		ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
		item.setItemMeta(meta);
		
		ItemStack item2 = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		item2.setItemMeta(meta2);
		
		ItemStack item3 = new ItemStack(Material.NETHERITE_SHOVEL);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.addEnchant(Enchantment.DIG_SPEED, 2, true);
		item3.setItemMeta(meta3);
		
		ItemStack item4 = new ItemStack(Material.NETHERITE_AXE);
		ItemMeta meta4 = item4.getItemMeta();
		meta4.addEnchant(Enchantment.MENDING, 1, true);
		item4.setItemMeta(meta4);
		
		ItemStack item5 = new ItemStack(Material.NETHERITE_HOE);
		ItemMeta meta5 = item5.getItemMeta();
		meta5.addEnchant(Enchantment.DIG_SPEED, 1, true);
		item5.setItemMeta(meta5);
		
		ItemStack item6 = new ItemStack(Material.NETHERITE_HELMET);
		ItemMeta meta6 = item6.getItemMeta();
		meta6.addEnchant(Enchantment.DURABILITY, 3, true);
		item6.setItemMeta(meta6);
		
		ItemStack item7 = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta meta7 = item7.getItemMeta();
		meta7.addEnchant(Enchantment.DURABILITY, 3, true);
		item7.setItemMeta(meta7);
		
		ItemStack item8 = new ItemStack(Material.NETHERITE_LEGGINGS);
		ItemMeta meta8 = item8.getItemMeta();
		meta8.addEnchant(Enchantment.DURABILITY, 3, true);
		item8.setItemMeta(meta8);
		
		ItemStack item9 = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta meta9 = item9.getItemMeta();
		meta9.addEnchant(Enchantment.FROST_WALKER, 2, true);
		item9.setItemMeta(meta9);
		
		ItemStack item1 = new ItemStack(Material.COAL, 64);
	    ItemStack item0 = new ItemStack(Material.COOKED_BEEF, 64);
	    
	    ItemStack[] items = {item, item0, item1, item2, item3, item4, item5, item6, item7, item8, item9};
		
		return items;
	}

}
