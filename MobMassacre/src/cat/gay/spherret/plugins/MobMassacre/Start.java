package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Start extends JavaPlugin {

	GlobalVars gbvs = new GlobalVars();

	PlayerStats playerStats;

	String dir;

	public void onEnable(){
		dir = this.getDataFolder().getPath();
		playerStats = new PlayerStats(new File(dir + "players.yml"));
		this.getServer().getPluginManager().registerEvents(new Events(), this);
	}
	public void onDisable(){

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
}
