package cat.gay.spherret.plugins.MobMassacre;

public class Rewarder {

	Start start = new Start();
	GlobalVars gbvs = new GlobalVars();
	Stats stats = new Stats();

	public void rewardAll(){
		for (String s : gbvs.rewards.keySet()){
			int kills = stats.getKills(s);
			int reward = gbvs.rewards.get(s);
			String Reward = start.getConfig().getString("Reward");
			Reward = Reward.replaceAll("@r", ((Integer) (reward)).toString()).replaceAll("@p", s);
		}
	}
}
