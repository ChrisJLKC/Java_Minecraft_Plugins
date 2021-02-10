package me.chris.hubscoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class main extends JavaPlugin implements Listener {
	
	// used to hold the runnable
	private int taskID;
	
	@Override
	public void onEnable() {
		// Registering the events that occur inside the listeners
		this.getServer().getPluginManager().registerEvents(this, this);
		
		// When starting up server, adding players to the score board, when they join
		if (!Bukkit.getOnlinePlayers().isEmpty())
			for (Player online : Bukkit.getOnlinePlayers()) {
				createBoard(online);
				start(online);
			}
	}

	@Override
	public void onDisable() {
		
	}
	
	// When a player joins
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// creates score board for player
		createBoard(event.getPlayer());
		// starting the runnable
		start(event.getPlayer());
	}
	
	// When a player leaves
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		// using our runnable classes we made in the ActiveBoard class, we can see if the player has a runnable to their ID
		ActiveBoard board = new ActiveBoard(event.getPlayer().getUniqueId());
		// Then we stop the score board from regenerating when the player has left
		if (board.hasID()) board.stop();
	}
	
	// This is where the runnable executes
	public void start(Player player) {
		// Initiating the runnable
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			// how many times the runnable is executed
			int count = 0;
			// Initiating our runnable ID functions
			ActiveBoard board = new ActiveBoard(player.getUniqueId());
			
			@Override
			public void run() {
				// when player has no ID to the score board
				if (!board.hasID()) board.setID(taskID);
				// Resetting the counter
				if (count == 12) count = 0;
				
				// with this we are getting a scrolling effect with our title of our score board
				switch(count) {					
				case 0:
					// Getting the title, changing colour from green to pink (&d&l = pink &2&l = green)
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &d&lA&2&lWESOME &a&l>>"));
					break;
					
				case 1:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lA&d&lW&2&lESOME &a&l>>"));
					break;
				
				case 2:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAW&d&lE&2&lSOME &a&l>>"));
					break;	
					
				case 3:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWE&d&lS&2&lOME &a&l>>"));
					break;	
				
				case 4:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWES&d&lO&2&lME &a&l>>"));
					break;
					
				case 5:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESO&d&lM&2&lE &a&l>>"));
					break;	
					
				case 6:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOM&d&lE&2&l &a&l>>"));
					break;	
					
				case 7:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOME &a&l>>"));
					break;	
					
				case 8:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &d&lAWESOME &a&l>>"));
					break;	
					
				case 9:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOME &a&l>>"));
					break;	
					
				case 10:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &d&lAWESOME &a&l>>"));
					break;
					
				case 11:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOME &a&l>>"));
					// updating the board
					createBoard(player);
					break;
				
				default:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
							ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOME &a&l>>"));
					break;
					
				}
				
				// adding 1 to count
				count++;
			}
			// Every 10 ticks the runnable is going to run
		}, 0, 10);
	}
	
	// Creating the board
	public void createBoard(Player player) {
		// managing where things go on the score board
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		// making a new board on the score board
		Scoreboard board = manager.getNewScoreboard();
		// The object of the score board
		//                                         NAME OF SCOREBOARD
		//                                                 |          CRITERIA
		//                                                 |             |              DISPLAY NAME OF SCOREBOARD
		Objective obj = board.registerNewObjective("HubScoreboards-1", "dummy", ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAWESOME &a&l>>"));
		// Also if you're lazy you can state the display name like this:
		// obj.setDisplayName("");
		
		// Now we say where it should be positioned
		// (You can say also a custom score board for the tab list of players)
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		// Now we create a score to separate the title from the info below
		Score score = obj.getScore(ChatColor.BLUE + "=-=-=-=-=-=-=-=-=-=");
		// now we set the position on the score board which is shown on the score board (The numbers are decreasing down the list)
		score.setScore(3);
		
		// Now we make new items to put into the score board
		
		//                                                                                       GETTING AMOUNT OF PLAYERS ONLINE
		//                                        REMEMBER THE TEXT HAS TO BE BELOW 40 CHARS                     |
		Score score2 = obj.getScore(ChatColor.AQUA + "Online Players: " + ChatColor.DARK_AQUA + Bukkit.getOnlinePlayers().size());
		score2.setScore(2);
		//                                                                                             GETTING THE MOB KILLS IN GAME
		//                                                                                                          |
		Score score3 = obj.getScore(ChatColor.AQUA + "Total Mob Kills: " + ChatColor.DARK_AQUA + player.getStatistic(Statistic.MOB_KILLS));
		score3.setScore(1);
		
		//                                                              DISPLAYS THE RANK (NEEDS SORTING)
		Score score4 = obj.getScore(ChatColor.AQUA + "Rank: " + ChatColor.DARK_AQUA + "Owner");
		score4.setScore(0);
		
		// Now return the score board to the player 
		player.setScoreboard(board);
	}
	
}
