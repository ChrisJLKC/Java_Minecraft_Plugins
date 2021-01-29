package me.chris.customrecipe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.addRecipe(getRecipe());
		Bukkit.addRecipe(getPicaxeRecipe());
	}

	@Override
	public void onDisable() {

	}
	
	public ShapedRecipe getRecipe() {
		
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		
		NamespacedKey key = new NamespacedKey(this, "nether_star");
		
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		// each quote is a line values resemble some item in the 3x3 grid of crafting table.
		
		recipe.shape(" T ", "TET", " T ");
		
		recipe.setIngredient('T', Material.GHAST_TEAR);
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		
		
		return recipe;
	}
	
	public ShapedRecipe getPicaxeRecipe() {
		
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + "Emerald Pickaxe");
		meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
		
		// If enchantment is above 10... 
		// meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "emerald_pickaxe");
		
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		// each quote is a line values resemble some item in the 3x3 grid of crafting table.
		
		recipe.shape("EEE", " S ", " S ");
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		
		
		return recipe;
	}

}
