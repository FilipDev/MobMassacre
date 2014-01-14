package cat.gay.spherret.plugins.MobMassacre;

import cat.gay.spherret.plugins.MobMassacre.TimeChecker.TimeChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Start extends JavaPlugin {

	public static NewYAML newYAML;

	TimeChecker timeChecker;

	public void onEnable(){
		this.saveDefaultConfig();
		newYAML = new NewYAML(new File(this.getDataFolder().getPath() + File.separator + "data.yml"));
		GlobalVars.statis = newYAML.newYaml();
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		try{
			Set<String> rewards = GlobalVars.statis.getConfigurationSection("Kills.").getKeys(false);

			for (String player : rewards){
				GlobalVars.rewards.put(player, GlobalVars.statis.getInt("Kills." + player + ".kills"));
			}
		}catch (Exception e){}
		timeChecker = new TimeChecker(this);
		timeChecker.trySync();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timeChecker, 0, 3600);
		List<String> validMobs = getConfig().getStringList("mobs");
		GlobalVars.validMobs = validMobs;
		arrangeArray();
	}

	public void onDisable(){
		this.saveStats();
	}

	public String getReward(){
		return getConfig().getString("reward");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((sender instanceof Player)){
			Player p = ((Player) sender);
			if (command.getName().equalsIgnoreCase("score")){
				if (args.length < 1){
					p.sendMessage(ChatColor.GOLD + "You have killed " + ChatColor.RED + Stats.getKills(p.getName()) + " valid mobs.");
				}
				if (args.length == 1 && p.hasPermission("mobmassacre.seeothers"))
					try{
						p.sendMessage(Stats.getKills(Bukkit.getPlayer(args[0])) + "");
					}catch (Exception e){
						p.sendMessage(ChatColor.RED + "Second parameter must be a player.");
					}
			}
			if (command.getName().equalsIgnoreCase("top")){
				p.sendMessage(ChatColor.GREEN + "Arranging List...");
				arrangeArray(p);
			}
			if (command.getName().equalsIgnoreCase("restartonnext") && p.hasPermission("mobmassacre.restart")){
				timeChecker.restart = true;
				p.sendMessage(ChatColor.GREEN + "Will restart on next loop.");
				return true;
			}
			if (command.getName().equalsIgnoreCase("restartnow") && p.hasPermission("mobmassacre.restart")){
				p.sendMessage(ChatColor.GRAY + "Restarting...");
				timeChecker.restartNow();
				p.sendMessage(ChatColor.GREEN + "Restarted.");
				return true;
			}
		}

		return true;
	}

	public static void saveStats(){
		try{
			GlobalVars.statis.save(newYAML.getFile());
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	public void arrangeArray(final Player p){
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				ValueComparator comp = new ValueComparator(GlobalVars.rewards);
				TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(comp);
				sorted_map.putAll(GlobalVars.rewards);
				GlobalVars.kills = sorted_map;
				Set players = sorted_map.keySet();
				Collection kills = sorted_map.values();
				tellTop(p, players, kills);
			}
		});
	}

	public void arrangeArray(){
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				ValueComparator comp = new ValueComparator(GlobalVars.rewards);
				TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(comp);
				sorted_map.putAll(GlobalVars.rewards);
				GlobalVars.kills = sorted_map;
			}
		});
	}

	public void tellTop(Player p, Set set, Collection kills){
		Object[] names = set.toArray();
		Object[] kills1 = kills.toArray();
		p.sendMessage(ChatColor.DARK_GRAY + "---TOP 3 SCORES---");
		try{
			p.sendMessage(ChatColor.RED + "1. " + ChatColor.GOLD + names[0] + " - " + ChatColor.RED + kills1[0]);
			p.sendMessage(ChatColor.RED + "2. " + ChatColor.GOLD + names[1] + " - " + ChatColor.RED + kills1[1]);
			p.sendMessage(ChatColor.RED + "3. " + ChatColor.GOLD + names[2] + " - " + ChatColor.RED + kills1[2]);
		}catch (ArrayIndexOutOfBoundsException e){}
	}
	class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;
		public ValueComparator(Map<String, Integer> base){
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with equals.
		public int compare(String a, String b){
			if(base.get(a) >= base.get(b)){
				return -1;
			}else{
				return 1;
			}// returning 0 would merge keys
		}
	}
	public Start(){}
}
