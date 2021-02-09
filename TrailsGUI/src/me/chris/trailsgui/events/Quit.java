package me.chris.trailsgui.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.chris.trailsgui.models.ParticleData;

public class Quit implements Listener {
	
	public void onQuit(PlayerQuitEvent event) {
		ParticleData p = new ParticleData(event.getPlayer().getUniqueId());
		// When player leaves the server
		if (p.hasID()) p.endTask();
	}
}
