package cat.gay.spherret.plugins.MobMassacre;

import com.sun.swing.internal.plaf.metal.resources.metal;
import org.bukkit.Bukkit;

import java.util.*;

public class Rewarder {

	Start start;

	public void setStart(Start start){
		this.start = start;
	}

	public void rewardAll(){
		Set players = GlobalVars.kills.keySet();
		Collection kills = GlobalVars.kills.values();
		Object[] names = players.toArray();
		Object[] kills1 = kills.toArray();

		List<String> rewardedextra = new ArrayList<String>();

		try{
			GlobalVars.rewards.put(names[0].toString(), GlobalVars.rewards.get(names[0]) + start.getConfig().getInt("placerewards.1"));
			GlobalVars.rewards.put(names[1].toString(), GlobalVars.rewards.get(names[1]) + start.getConfig().getInt("placerewards.2"));
			GlobalVars.rewards.put(names[2].toString(), GlobalVars.rewards.get(names[2]) + start.getConfig().getInt("placerewards.3"));
			rewardedextra.add(names[0].toString());
			rewardedextra.add(names[1].toString());
			rewardedextra.add(names[2].toString());
		}catch (Exception e){}

		for (String s : GlobalVars.rewards.keySet()){
			int reward = GlobalVars.rewards.get(s);
			String Reward = start.getReward();
			Reward = Reward.replaceAll("@r", (reward * start.getConfig().getDouble("rate") + ""));
			Reward = Reward.replaceAll("@p", s);
			String message;
			if (rewardedextra.contains(s)){
				message = start.getConfig().getString("chartmessage");
				message = message.replaceAll("@pl", rewardedextra.indexOf(s) + "");
				message = message.replaceAll("@rpl", "" + start.getConfig().getInt("placerewards." + rewardedextra.indexOf(s)));
			}
			else
				message = start.getConfig().getString("message");
			message = message.replaceAll("@mk", GlobalVars.kills.get(s) + "");
			message = message.replaceAll("@r", (reward * start.getConfig().getDouble("rate") + ""));
			message = message.replaceAll("@p", s);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Reward);
			Bukkit.getPlayer(s).sendMessage(message);
		}
		GlobalVars.rewards.clear();
		GlobalVars.statis.set("Kills", null);
	}
}
