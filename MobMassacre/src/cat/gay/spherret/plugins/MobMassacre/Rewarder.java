package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Set;

public class Rewarder {

	Start start;

	public void setStart(Start start){
		this.start = start;
	}

	public void rewardAll(){
		for (String s : GlobalVars.rewards.keySet()){
			int reward = GlobalVars.rewards.get(s);
			String Reward = start.getReward();
			Reward = Reward.replaceAll("@r", ((Integer) (reward)).toString()).replaceAll("@p", s);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Reward);
			Set players = GlobalVars.kills.keySet();
			Collection kills = GlobalVars.kills.values();
			Object[] names = players.toArray();
			Object[] kills1 = kills.toArray();
		}
		GlobalVars.rewards.clear();
		GlobalVars.statis.set("Kills", null);
	}
}
