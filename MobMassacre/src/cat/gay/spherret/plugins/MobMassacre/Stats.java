package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Stats{

	Start s = new Start();

	int changes;

	public void changeKill(Player player, Entity killedEntity){
		if (GlobalVars.validMobs.contains(killedEntity.getType().getName())){
			try{
				GlobalVars.statis.set("Kills." + player.getName() + ".kills", 1 + GlobalVars.statis.getInt("Kills." + player.getName() + ".kills"));
				System.out.println(GlobalVars.statis.getInt("Kills." + player.getName() + ".kills") + " " + "Kills." + player.getName() + ".kills");
			}catch (Exception e){
				GlobalVars.statis.set("Kills." + player.getName() + ".kills", 1);
			}
			changes++;
			if (changes > 4)
				s.saveStats();
		}
	}

	public int getKills(Player p){
		try{
			return GlobalVars.statis.getInt("Kills." + p.getName() + ".kills");
		}catch (Exception e){
			return 0;
		}
	}
	public int getKills(String p){
		try{
			return GlobalVars.statis.getInt("Kills." + p + ".kills");
		}catch (Exception e){
			return 0;
		}
	}

}
