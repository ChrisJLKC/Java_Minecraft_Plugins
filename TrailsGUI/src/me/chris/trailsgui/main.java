package me.chris.trailsgui;

import org.bukkit.plugin.PluginManager;
// bukkit implement for Java 
import org.bukkit.plugin.java.JavaPlugin;

import me.chris.trailsgui.commands.Trail;
import me.chris.trailsgui.events.ClickEvent;
import me.chris.trailsgui.events.Movement;
import me.chris.trailsgui.events.Quit;
import me.chris.trailsgui.models.GUI;

public class main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// setting up all necessary packets and classes 
		
		GUI menu = new GUI();
		menu.register();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new ClickEvent(), this);
		pm.registerEvents(new Quit(), this);
		pm.registerEvents(new Movement(), this);
		
		this.getCommand("trails").setExecutor(new Trail());
	}

	@Override
	public void onDisable() {

	}
	
	
}
