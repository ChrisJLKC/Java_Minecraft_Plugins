package me.chris.trailsgui.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.chris.trailsgui.main;

public class Effects {
	
	private int taskID;
	private final Player player;
	
	// for the private variable Player
	public Effects(Player player) {
		this.player = player;
	}
	
	// for the totem trial effect
	public void startTotem() {
		// frame of running the totem effect
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(main.class), new Runnable() {
		
			double var = 0;
			Location loc, first, second;
			ParticleData particle = new ParticleData(player.getUniqueId());

			// where the particles run 
			@Override
			public void run() {
				// if player has no id link to them
				if (!particle.hasID()) {
					particle.setID(taskID);
				}
				
				// value changes as it runs ((+=) = var = var + 1;)
				var += Math.PI / 16;
				
				// getting location of player
				loc = player.getLocation();
				
				// What the particles do around the position of the person
				//                           X              Y                 Z
				first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
				second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));
				
				// Now spawn the particle into the world in mine craft
				player.getWorld().spawnParticle(Particle.TOTEM, first, 0);
				player.getWorld().spawnParticle(Particle.TOTEM, second, 0);
				
			}
		
		// How fast it runs	
		}, 0, 1);
	}
}
