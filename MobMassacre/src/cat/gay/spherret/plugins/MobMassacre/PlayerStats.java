package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerStats extends NewYAML{

	YamlConfiguration playerstats;

	GlobalVars gbvs = new GlobalVars();

	public PlayerStats(File f) {
		super(f);
		playerstats = newYaml();
	}

	public void changeKill(Player player, Entity killedEntity){
		try{
			playerstats.set("Kills." + player.getName() + ".kills", playerstats.getInt("Kills." + player.getName() + ".kills") + gbvs.rewards.get(killedEntity.getType().name()));
		}catch (Exception e){
			playerstats.set("Kills." + player.getName() + ".kills", 1);
			System.out.println("An error occured getting the reward value for mob, defaulting to 1");
		}
		try{
			playerstats.save(getFile());
		}catch (IOException e){
			System.out.println("Failed to save score.");
		}
	}

	public int getKills(Player p){
		try{
			return playerstats.getInt("Kills." + p.getName() + ".kills");
		}catch (Exception e){
			return 0;
		}
	}

}
