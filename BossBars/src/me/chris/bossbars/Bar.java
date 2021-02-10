package me.chris.bossbars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;


public class Bar {

	// assigning our runnable
	private int taskID = -1;
	// implementing the main class
	private final main plugin;
	// initiating the boss bar library
	private BossBar bar;
	
	// Applying the main is equal to the main plug in
	public Bar(main plugin) {
		this.plugin = plugin;
	}
	
	// Adding a player to the boss bar
	public void addPlayer(Player player) {
		bar.addPlayer(player);
	}
	
	// Applying the boss bar again when the server is reloaded 
	public BossBar getBar() {
		return bar;
	}
	
	// creating the boss bar for the player to see
	public void createBar() {
		//                                  Text on BossBar
		//                                         |               Colour of Boss Bar
		//                                         |                       |        Boss Bar style (SOLID) bit there is fragmented if needed
		//                                         |                       |                |      (Also a flag can be created if it is a mob bar)
		bar = Bukkit.createBossBar(format("&c&lYeeting on the Daily"), BarColor.RED, BarStyle.SOLID);
		// Setting Bar to visible on the player
		bar.setVisible(true);
		// allowing the cast function to be used with the bar
		cast();
	}
	
	// Now we set the runnable
	public void cast() {
		// Now we set the runnable to the variable
		setTaskID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			// the count of each message is here
			int count = -1;
			// how full the boss bar is at start
			double progress = 1.0;
			// how fast it goes down by 20 seconds
			double time = 1.0/(20 * 20);
			
			// Where the movement happens in mine craft
			@Override
			public void run() {
				// setting the progress to the progress variable
				bar.setProgress(progress);
				
				// changing the message in the boss bar
				switch(count) {
				// showing the first message
				case -1:
					break;
				
				// showing the transitioned messages
				case 0:
					bar.setColor(BarColor.PINK);
					bar.setTitle(format("&d&lYeeting without consent"));
					break;
				
				case 1:
					bar.setColor(BarColor.PURPLE);
					bar.setTitle(format("&5&lYEEEET!!!!!!!"));
					break;
					
				default:
					// going back to original message
					bar.setColor(BarColor.RED);
					bar.setTitle(format("&c&lYeeting on the Daily"));
					count = -1;
					break;
				}
				
				// Now we state how long the progress bar is going to decrease by
				progress = progress - time;
				if (progress <= 0) {
					count++;
					progress = 1.0;
				}
				
			}
			
		// How many ticks its going run, this is 1 second
		}, 0, 0));
	}
	
	// When creating multiple translate colours
	private String format(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
}
