package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Stats{

	Start s = new Start();

	GlobalVars gbvs = new GlobalVars();

	public void changeKill(Player player, Entity killedEntity){
		if (gbvs.validMobs.contains(killedEntity.getType().getName())){
			s.statis.set("Kills." + player.getName() + ".kills", 1);
			try{
				s.statis.save(s.newYAML.getFile());
			}catch (IOException e){
				System.out.println("Failed to save score.");
			}
		}
	}

	public int getKills(Player p){
		try{
			return s.statis.getInt("Kills." + p.getName() + ".kills");
		}catch (Exception e){
			return 0;
		}
	}
	public int getKills(String p){
		try{
			return s.statis.getInt("Kills." + p + ".kills");
		}catch (Exception e){
			return 0;
		}
	}

}
