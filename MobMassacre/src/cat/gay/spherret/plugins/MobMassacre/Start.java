package cat.gay.spherret.plugins.MobMassacre;

import cat.gay.spherret.plugins.MobMassacre.TimeChecker.TimeChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Start extends JavaPlugin {

	public GlobalVars gbvs = new GlobalVars();

	public Stats stats;

	public static NewYAML newYAML;


	public void onEnable(){
		newYAML = new NewYAML(new File(this.getDataFolder().getPath() + File.separator + "data.yml"));
		System.out.println(newYAML.getFile());
		GlobalVars.statis = newYAML.newYaml();
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		Set<String> rewards = GlobalVars.statis.createSection("Kills").getKeys(false);
		for (String player : rewards){
			gbvs.rewards.put(player, GlobalVars.statis.getInt("Kills." + player));
		}
		this.saveDefaultConfig();
		TimeChecker timeChecker = new TimeChecker(this);
		timeChecker.trySync();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timeChecker, 0, 3600);
		String[] validMobs = {"Chicken", "Zombie"};
		GlobalVars.validMobs = Arrays.asList(validMobs);
	}

	public void onDisable(){
		this.saveStats();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			Player p = ((Player) sender);
			if (command.getName().equals("score")){
				if (args.length < 1)
					p.sendMessage(stats.getKills(p) + "");
				if (args.length == 1 && p.hasPermission("mobmassacre.seeothers"))
					try{
						p.sendMessage(stats.getKills(Bukkit.getPlayer(args[0])) + "");
					}catch (Exception e){
						p.sendMessage(ChatColor.RED + "Second parameter must be a player.");
					}
			}
			if (command.getName().equals("top")){

			}
		}

		return true;
	}

	public void saveStats(){
		System.out.println(newYAML.getFile());
		try{
			gbvs.statis.save(newYAML.getFile());
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
