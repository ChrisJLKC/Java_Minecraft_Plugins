package me.chris.bossbars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	// Initiating the bar class
	public Bar bar;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		bar = new Bar(this);
		bar.createBar();
		
		if (Bukkit.getOnlinePlayers().size() > 0)
			for (Player on : Bukkit.getOnlinePlayers())
				bar.addPlayer(on);
	}

	@Override
	public void onDisable() {
		// removing all bars when the server is being shutdown
		bar.getBar().removeAll();
	}
	
	// When a player joins the server
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// if player is not assigned to the boss bar and gets added to the boss bar
		if (!bar.getBar().getPlayers().contains(event.getPlayer()))
			bar.addPlayer(event.getPlayer());
	}
	
}
