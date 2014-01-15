package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.Bukkit;

import javax.xml.bind.annotation.XmlElementDecl;
import java.util.*;

public class Rewarder {

	Start start;

	public void setStart(Start start){
		this.start = start;
	}

	public void rewardAll(){

		HashMap<String, Integer> extrapts = new HashMap<String, Integer>();

		try{
			extrapts.put(GlobalVars.entryarray[0].getKey(), GlobalVars.entryarray[0].getValue());
			extrapts.put(GlobalVars.entryarray[1].getKey(), GlobalVars.entryarray[1].getValue());
			extrapts.put(GlobalVars.entryarray[2].getKey(), GlobalVars.entryarray[2].getValue());
		}catch (Exception e){}

		for (String s : GlobalVars.rewards.keySet()){
			int reward = GlobalVars.rewards.get(s);
			String Reward = start.getReward();
			if (extrapts.containsKey(s)){
				Reward = Reward.replaceAll("@r", ((reward * start.getConfig().getDouble("rate")) + extrapts.get(s) + ""));
			}else
				Reward = Reward.replaceAll("@r", (reward * start.getConfig().getDouble("rate") + ""));
			Reward = Reward.replaceAll("@p", s);
			String message;
			message = start.getConfig().getString("message");
			message = message.replaceAll("@mk", GlobalVars.rewards.get(s) + "");
			message = message.replaceAll("@r", (reward * start.getConfig().getDouble("rate") + ""));
			message = message.replaceAll("@p", s);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Reward);
			Bukkit.getPlayer(s).sendMessage(message);
		}
		GlobalVars.rewards.clear();
		GlobalVars.statis.set("Kills", null);
		GlobalVars.entryset.clear();
		GlobalVars.entryarray = null;
	}
}
