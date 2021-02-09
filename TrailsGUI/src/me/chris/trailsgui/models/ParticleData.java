package me.chris.trailsgui.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class ParticleData {
	
	// creates an array where data can be stored whilst the server is up
	private static Map<UUID, Integer> TRAILS = new HashMap<UUID, Integer>();
	// getting the uuid variable
	private final UUID uuid;
	
	// sets uuid as a plublic variable
	public ParticleData(UUID uuid) {
		this.uuid = uuid;
	}
	
	// setting the player a trail and setting a global variable to assign it to.
	public void setID(int id) {
		TRAILS.put(uuid, id);
	}
	
	// getting id of the player, to check if they have a trail
	public int getID() {
		return TRAILS.get(uuid);
	}
	
	// making sure player has a stored variable where it has been assigned a trail
	public boolean hasID() {
		if (TRAILS.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	
	// When player wants to remove their trail
	public void removeID() {
		TRAILS.remove(uuid);
	}
	
	// When the player is switching to different trails and needs to cancel the trail before
	public void endTask() {
		if (getID() == 1) {
			return;
		}
		
		
		Bukkit.getScheduler().cancelTask(getID());
	}
	
	// The only reason we say getID() == 1 in endTask is because if the player is moving or the particle effect is a moving one.
	public static boolean hasFakeID(UUID uuid) {
		if (TRAILS.containsKey(uuid)) {
			if (TRAILS.get(uuid) == 1) return true;
		}
		return false;
	}
	
}
