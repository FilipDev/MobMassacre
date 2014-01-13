package cat.gay.spherret.plugins.MobMassacre.TimeChecker;

import cat.gay.spherret.plugins.MobMassacre.GlobalVars;
import cat.gay.spherret.plugins.MobMassacre.Rewarder;
import cat.gay.spherret.plugins.MobMassacre.Start;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class TimeChecker implements Runnable {

	Start start;
	final Rewarder rewarder = new Rewarder();
	boolean isSynced = false;
	Long time;
	Long curTime;
	Long difference;

	public boolean trySync(){
		try{
			curTime = System.currentTimeMillis();
			difference = curTime - GlobalVars.statis.getLong("TIME");
			GlobalVars.statis.set("24HRSTART", GlobalVars.statis.getLong("24HRSTART") + difference);
			GlobalVars.statis.set("TIME", GlobalVars.statis.getLong("TIME") + difference);
			time = 86400000 - (GlobalVars.statis.getLong("TIME") - GlobalVars.statis.getLong("24HRSTART"));
			if (time == 0){
				isSynced = false;
				return false;
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Syncing time. " + time);
			isSynced = true;
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public TimeChecker(Start start){
		this.start = start;
	}

	@Override
	public void run() {

		YamlConfiguration statis = GlobalVars.statis;

		curTime = System.currentTimeMillis();

		if (isSynced){
			statis.set("TIME", curTime);
			System.out.println(curTime - GlobalVars.statis.getLong("24HRSTART") - time);
			if (curTime - GlobalVars.statis.getLong("24HRSTART") > time){
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "New 24 hour period started, setting time to now. SYNCED");
				statis.set("24HRSTART", curTime);
				rewarder.rewardAll();
			}
		}
		else{
			statis.set("TIME", curTime);
			if (statis.getLong("TIME") - 86400000 >= GlobalVars.statis.getLong("24HRSTART")){
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "New 24 hour period started, setting time to now. NOT SYNCED");
				statis.set("24HRSTART", curTime);
				rewarder.rewardAll();
			}
		}

		start.saveStats();
	}
}
