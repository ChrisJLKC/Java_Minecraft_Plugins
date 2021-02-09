package me.chris.trailsgui.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chris.trailsgui.models.GUI;

public class Trail implements CommandExecutor {
	
	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
		// if something writes in our command
		if (label.equalsIgnoreCase("trails")) {
			// Making sure that it is a player in the game
			if (!(sender instanceof Player)) {
				sender.sendMessage("Sorry we can't allow this!");
				return true;
			}
			
			// creating the player relative to the sender
			Player player = (Player) sender;
			
			// finding our GUI in our GUI.java
			GUI menu = new GUI();
			// open inventory for the player
			menu.openInventory(player);
			return true;
		}
		return false;
	}
}
