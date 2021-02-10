package me.chris.hubscoreboards;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class ActiveBoard {
	
	// This document is mainly used to set up the runnable for the ID of the player
	
	// HashMap for holding values of tasks in the score board
	private static Map<UUID, Integer> TASKS = new HashMap<UUID, Integer>();
	// setting a UUID variable
	private final UUID uuid;
	
	// this.uuid is declaring our variable uuid
	// and the other uuid is equal to the library one
	public ActiveBoard(UUID uuid) {
		this.uuid = uuid;
	}
	
	// setting a item on the score board
	public void setID(int id) {
		TASKS.put(uuid, id);
	}
		
	// getting id, to check if it is on the score board
	public int getID() {
		return TASKS.get(uuid);
	}
		
	// making sure player has a stored variable where it has been assigned a thing on the score board
	public boolean hasID() {
		if (TASKS.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	
	// stopping refresh of the particular thing on the score board, mainly used when player leaves the server
	public void stop() {
		Bukkit.getScheduler().cancelTask(TASKS.get(uuid));
		TASKS.remove(uuid);
	}
}
