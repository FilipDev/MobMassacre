package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Stats{

	int changes;

	public void changeKill(Player player, Entity killedEntity){
		if (player instanceof Player){
			if (GlobalVars.validMobs.contains(killedEntity.getType().getName())){
				if (GlobalVars.validWorlds.contains(player.getWorld().getName())){
					try{
						GlobalVars.statis.set("Kills." + player.getName() + ".kills", 1 + GlobalVars.statis.getInt("Kills." + player.getName() + ".kills"));
						GlobalVars.rewards.put(player.getName(), GlobalVars.statis.getInt("Kills." + player.getName() + ".kills"));
					}catch (Exception e){
						GlobalVars.statis.set("Kills." + player.getName() + ".kills", 1);
						GlobalVars.rewards.put(player.getName(), 1);
					}
					changes++;
					if (changes > 3){
						Start.saveStats();
						changes = 0;
					}
				}
			}
		}
	}

	public static int getKills(Player p){
		try{
			return GlobalVars.statis.getInt("Kills." + p.getName() + ".kills");
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	public static int getKills(String p){
		try{
			return GlobalVars.statis.getInt("Kills." + p + ".kills");
		}catch (Exception e){
			return 0;
		}
	}
	public void setKills(Player p, Integer i){
		GlobalVars.statis.set("Kills." + p.getName() + ".kills", i);
	}
	public void setKills(String s, Integer i){
		GlobalVars.statis.set("Kills." + s + ".kills", i);

	}
	public void addKills(Player p, Integer i){
		GlobalVars.statis.set("Kills." + p.getName() + ".kills", GlobalVars.statis.getInt("Kills." + p.getName() + ".kills") + i);
	}
	public void addKills(String s, Integer i){
		GlobalVars.statis.set("Kills." + s + ".kills", GlobalVars.statis.getInt("Kills." + s + ".kills") + i);
	}
	public void subtractKills(Player p, Integer i){
		GlobalVars.statis.set("Kills." + p.getName() + ".kills", GlobalVars.statis.getInt("Kills." + p.getName() + ".kills") - i);
	}
	public void subtractKills(String s, Integer i){
		GlobalVars.statis.set("Kills." + s + ".kills", GlobalVars.statis.getInt("Kills." + s + ".kills") - i);
	}

}
