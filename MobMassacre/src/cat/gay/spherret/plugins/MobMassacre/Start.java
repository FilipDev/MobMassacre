package cat.gay.spherret.plugins.MobMassacre;

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

	public static Object BarAPI;

	public static boolean isBarEnabled;

	TimeChecker timeChecker;

	public void onEnable(){
		GlobalVars.validWorlds = getConfig().getStringList("activeworlds");
		new cat.gay.spherret.plugins.MobMassacre.Vault().setupPermissions();
		isBarEnabled = setUpBar();
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
				if (GlobalVars.alreadyArranged){
					tellPlayer(p, GlobalVars.entryset);
					return;
				}
				GlobalVars.entryset = entriesSortedByValues(GlobalVars.rewards);
				for (Map.Entry en : GlobalVars.entryset)
					GlobalVars.rewards.put(en.getKey().toString(), (Integer) en.getValue());
				tellPlayer(p, GlobalVars.entryset);
				GlobalVars.alreadyArranged = true;
			}
		});
	}

	public boolean setUpBar(){
		boolean isEnabled = Bukkit.getPluginManager().isPluginEnabled("BarAPI");
		if (!isEnabled)
			return false;
		BarAPI = new me.confuser.barapi.BarAPI();
		return true;
	}

	public void tellPlayer(Player p, SortedSet<Map.Entry<String, Integer>> entries){
		Map.Entry<String, Integer>[] entryarray = entries.toArray(new Map.Entry[3]);
		GlobalVars.entryarray = entryarray;
		p.sendMessage(ChatColor.DARK_GRAY + "---TOP 3 SCORES---");
		try{
			p.sendMessage(ChatColor.RED + "1. " + ChatColor.GOLD + entryarray[0].getKey() + " - " + ChatColor.RED + entryarray[0].getValue());
			p.sendMessage(ChatColor.RED + "2. " + ChatColor.GOLD + entryarray[1].getKey() + " - " + ChatColor.RED + entryarray[1].getValue());
			p.sendMessage(ChatColor.RED + "3. " + ChatColor.GOLD + entryarray[2].getKey() + " - " + ChatColor.RED + entryarray[2].getValue());
		}catch (NullPointerException e){}
	}

	public void tellTop(Player p, Set set, Collection kills){
		Object[] names = set.toArray();
		Object[] kills1 = kills.toArray();
		p.sendMessage(ChatColor.DARK_GRAY + "---TOP 3 SCORES---");
		try{
			p.sendMessage(ChatColor.RED + "1. " + ChatColor.GOLD + names[0] + " - " + ChatColor.RED + ((kills1[0])));
			p.sendMessage(ChatColor.RED + "2. " + ChatColor.GOLD + names[1] + " - " + ChatColor.RED + ((kills1[1])));
			p.sendMessage(ChatColor.RED + "3. " + ChatColor.GOLD + names[2] + " - " + ChatColor.RED + ((kills1[2])));
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
	public Map changeMapValues(Map map, List<Object> lo){
		Map new_map = new HashMap();
		Integer x = 0;
		for (Object o : map.keySet()){
			new_map.put(o, lo.get(x));
			x++;
		}
		map.clear();
		return new_map;
	}
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
				new Comparator<Map.Entry<K,V>>() {
					@Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
						int res = e2.getValue().compareTo(e1.getValue());
						return res != 0 ? res : 1; // Special fix to preserve items with equal values
					}
				}
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
	public Start(){}
}
