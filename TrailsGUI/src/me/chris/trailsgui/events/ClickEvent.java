package me.chris.trailsgui.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.chris.trailsgui.models.Effects;
import me.chris.trailsgui.models.GUI;
import me.chris.trailsgui.models.ParticleData;

public class ClickEvent implements Listener {
	
	// Importing the Inventory of GUI file
	private GUI menu;
	public ClickEvent() {
		menu = new GUI();
	}
	
	// When clicking on something in the GUI
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		// making sure that it is in the inventory of trails
		if (!event.getInventory().equals(menu.getInventory())) return;
		
		// Now we know that we have got a player in the right inventory make the Player cast
		Player player = (Player) event.getWhoClicked();
		// To make sure players can't take item out of the inventory, this is said
		event.setCancelled(true);
		
		// If the player is clicking in their own inventory, ignore it as shown below
		if (event.getView().getType() == InventoryType.PLAYER) return;
		
		// Now we know that they are clicking in something in our GUI, we bring in the particleData
		// First we make them a Id which is related to their UUID
		ParticleData particle = new ParticleData(player.getUniqueId());
		
		// If they have already got a trail going, then stop that trail
		if (particle.hasID()) {
			particle.endTask();
			particle.removeID();
		}
		
		// Using our effects class in the models package
		Effects trails = new Effects(player);
		
		// when a GUI item is clicked
		switch(event.getSlot()) {
		case 3:
			trails.startTotem();
			player.closeInventory();
			player.updateInventory();
			break;
		case 5:
			particle.setID(1);
			player.closeInventory();
			player.updateInventory();
			break;
		case 8:
			if (particle.hasID()) {
				particle.endTask();
				particle.removeID();
			}
			player.closeInventory();
			player.updateInventory();
			break;
		default:
			break;
		}
	}
}
