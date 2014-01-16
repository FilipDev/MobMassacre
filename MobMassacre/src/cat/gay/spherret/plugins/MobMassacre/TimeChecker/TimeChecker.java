package cat.gay.spherret.plugins.MobMassacre.TimeChecker;

import cat.gay.spherret.plugins.MobMassacre.GlobalVars;
import cat.gay.spherret.plugins.MobMassacre.Rewarder;
import cat.gay.spherret.plugins.MobMassacre.Start;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class TimeChecker implements Runnable {

	Start start;
	final Rewarder rewarder = new Rewarder();
	boolean isSynced = false;
	Long time;
	Long curTime;
	Long difference;

	public boolean restart = false;

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
			Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Syncing time. (" + time + ")");
			isSynced = true;
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public TimeChecker(Start start){
		this.start = start;
		rewarder.setStart(start);
	}

	@Override
	public void run() {

		YamlConfiguration statis = GlobalVars.statis;

		curTime = System.currentTimeMillis();

		if (restart)
			isSynced = false;

		if (isSynced){
			statis.set("TIME", curTime);
			if (curTime - GlobalVars.statis.getLong("24HRSTART") > time){
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MM] New 24 hour period started.");
				statis.set("24HRSTART", curTime);
				rewarder.rewardAll();
			}
		}
		else{
			statis.set("TIME", curTime);
			if (curTime - GlobalVars.statis.getLong("24HRSTART") > 86400000 || restart){
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MM] New 24 hour period started.");
				statis.set("24HRSTART", curTime);
				rewarder.rewardAll();
			}
		}
		if (Start.isBarEnabled){
			long minutes = TimeUnit.MINUTES.toMinutes(86400000 - (curTime - statis.getLong("24HRSTART")));
			for (String s : GlobalVars.validWorlds){
				for (Player p : Bukkit.getWorld(s).getPlayers()){
					me.confuser.barapi.BarAPI.setMessage(p, ChatColor.RED + "There are " + minutes + " left until all the scores are counted!", 15);
				}
			}
		}
		start.saveStats();
	}
	public void restartNow(){

		YamlConfiguration statis = GlobalVars.statis;
		curTime = System.currentTimeMillis();

		statis.set("TIME", curTime);
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MM] RESTARTED LOOP: New 24 hour period started.");
		statis.set("24HRSTART", curTime);
		rewarder.rewardAll();
	}
}
