package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.Bukkit;

import java.text.DecimalFormat;
import java.util.*;

public class Rewarder {

	Start start;

	public void setStart(Start start){
		this.start = start;
	}

	public void rewardAll(){

		HashMap<String, Integer> extrapts = new HashMap<String, Integer>();

		start.arrangeArray();

		try{
			for (String perm : start.getConfig().getConfigurationSection("placerewards").getKeys(false)){
				try{
					if (Vault.permission.playerHas((String) null, GlobalVars.entryarray[0].getKey(), perm) || Bukkit.getOfflinePlayer(GlobalVars.entryarray[0].getKey()).isOp())
						extrapts.put(GlobalVars.entryarray[0].getKey(), start.getConfig().getInt("placerewards." + perm + ".1"));
					if (Vault.permission.playerHas((String) null, GlobalVars.entryarray[1].getKey(), perm) || Bukkit.getOfflinePlayer(GlobalVars.entryarray[1].getKey()).isOp())
						extrapts.put(GlobalVars.entryarray[1].getKey(), start.getConfig().getInt("placerewards." + perm + ".2"));
					if (Vault.permission.playerHas((String) null, GlobalVars.entryarray[2].getKey(), perm) || Bukkit.getOfflinePlayer(GlobalVars.entryarray[2].getKey()).isOp())
						extrapts.put(GlobalVars.entryarray[2].getKey(), start.getConfig().getInt("placerewards." + perm + ".3"));
				}catch (NullPointerException e){}
			}
			try{
				extrapts.put(GlobalVars.entryarray[0].getKey(), extrapts.get(GlobalVars.entryarray[0].getKey()) + start.getConfig().getInt("placerewards.default.1"));
			}catch (NullPointerException e){
				extrapts.put(GlobalVars.entryarray[0].getKey(), start.getConfig().getInt("placerewards.default.1"));
				extrapts.put(GlobalVars.entryarray[1].getKey(), start.getConfig().getInt("placerewards.default.2"));
				extrapts.put(GlobalVars.entryarray[2].getKey(), start.getConfig().getInt("placerewards.default.3"));
			}
			try{
				extrapts.put(GlobalVars.entryarray[1].getKey(), extrapts.get(GlobalVars.entryarray[1].getKey()) + start.getConfig().getInt("placerewards.default.2"));
			}catch (NullPointerException e){
				extrapts.put(GlobalVars.entryarray[1].getKey(), start.getConfig().getInt("placerewards.default.2"));
				extrapts.put(GlobalVars.entryarray[2].getKey(), start.getConfig().getInt("placerewards.default.3"));
			}
			try{
				extrapts.put(GlobalVars.entryarray[2].getKey(), extrapts.get(GlobalVars.entryarray[2].getKey()) + start.getConfig().getInt("placerewards.default.3"));
			}catch (NullPointerException e){
				extrapts.put(GlobalVars.entryarray[2].getKey(), start.getConfig().getInt("placerewards.default.3"));
			}
		}catch (Exception e){}

		DecimalFormat df = new DecimalFormat("0.00");

		for (String s : GlobalVars.rewards.keySet()){

			int reward = GlobalVars.rewards.get(s);

			if (start.getConfig().getBoolean("usevault") && GlobalVars.vaultEcon){
				try{
					Vault.economy.depositPlayer(s, Double.parseDouble(df.format((reward * start.getConfig().getDouble("rate")) + extrapts.get(s).doubleValue())));
				}catch (NullPointerException e) {
					Vault.economy.createPlayerAccount(s);
					Vault.economy.depositPlayer(s, Double.parseDouble(df.format((reward * start.getConfig().getDouble("rate")) + extrapts.get(s).doubleValue())));
				}
			}else{
				String Reward = start.getReward();
				if (extrapts.containsKey(s)){
					Reward = Reward.replaceAll("@r", df.format((reward * start.getConfig().getDouble("rate")) + extrapts.get(s).doubleValue()));
				}else
					Reward = Reward.replaceAll("@r", df.format(reward * start.getConfig().getDouble("rate")));
				Reward = Reward.replaceAll("@p", s);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Reward);
			}

			String message;
			message = start.getConfig().getString("message");
			message = message.replaceAll("@mk", GlobalVars.rewards.get(s) + "");
			df = new DecimalFormat("0.0");
			if (extrapts.containsKey(s)){
				message = message.replaceAll("@r", df.format((reward * start.getConfig().getDouble("rate")) + extrapts.get(s).doubleValue()));
			}else
				message = message.replaceAll("@r", df.format((reward * start.getConfig().getDouble("rate"))));
			message = message.replaceAll("@p", s);
			try{
				Bukkit.getPlayer(s).sendMessage(message);
			}catch (NullPointerException e){}
		}
		GlobalVars.rewards.clear();
		GlobalVars.statis.set("Kills", null);
		GlobalVars.entryset = null;
		GlobalVars.entryarray = null;
	}

}
