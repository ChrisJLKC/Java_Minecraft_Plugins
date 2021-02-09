package me.chris.trailsgui.events;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.chris.trailsgui.models.ParticleData;

public class Movement implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		// if they have a fake ID 
		if (!ParticleData.hasFakeID(event.getPlayer().getUniqueId())) return;
		
		// so when they move they generate particles randomly
		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			// detailing the place of each particle
			event.getPlayer().getWorld().spawnParticle(Particle.FLAME, event.getPlayer().getLocation()
					.add(r.nextDouble() * 0.5, r. nextDouble() * 0.5, r.nextDouble() * 0.5), 0);
		}
		
		for (int i = 0; i < 5; i++) {
			// detailing the place of each particle
			event.getPlayer().getWorld().spawnParticle(Particle.FLAME, event.getPlayer().getLocation()
					.add(-1 * (r.nextDouble() * 0.5), r. nextDouble() * 0.5, (r.nextDouble() * 0.5) * -1), 0);
		}
	}
}
