package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class Start extends JavaPlugin {

	GlobalVars gbvs = new GlobalVars();

	Stats stats;

	NewYAML newYAML;
	YamlConfiguration statis;

	String dir;

	public void onEnable(){
		dir = this.getDataFolder().getPath();
		newYAML = new NewYAML(new File(dir + "data.yml"));
		statis = newYAML.newYaml();
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		List<String> rewards = statis.getStringList("Rewards");
		for (String mob : rewards){
			gbvs.reward.put(mob, statis.getInt("Rewards" + mob));
		}
		this.saveDefaultConfig();
		TimeChecker timeChecker = new TimeChecker();
		timeChecker.trySync();
		try{
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TimeChecker(), 0, 100);
		}catch (NullPointerException e){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TimeChecker(), 0, 100);
		}
	}
	public void onDisable(){
		this.saveStats();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			Player p = ((Player) sender);
			if (command.getName().equals("score")){
				p.sendMessage("");
			}
		}

		return true;
	}

	public void saveStats(){
		try{
			statis.save(newYAML.getFile());
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
