package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.Bukkit;

public class Rewarder {

	Start start = new Start();
	GlobalVars gbvs = new GlobalVars();
	Stats stats = start.stats;

	public void rewardAll(){
		for (String s : gbvs.rewards.keySet()){
			int reward = gbvs.rewards.get(s);
			String Reward = start.getConfig().getString("Reward");
			Reward = Reward.replaceAll("@r", ((Integer) (reward)).toString()).replaceAll("@p", s);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Reward);

		}
		gbvs.rewards.clear();
		GlobalVars.statis.set("Kills", "");
	}
}
