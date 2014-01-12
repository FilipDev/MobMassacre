package cat.gay.spherret.plugins.MobMassacre;

public class TimeChecker implements Runnable {

	final Start start = new Start();
	final Rewarder rewarder = new Rewarder();

	@Override
	public void run() {
		start.statis.set("TIME", System.currentTimeMillis());
		start.saveStats();
		if (start.statis.getLong("TIME") > start.statis.getLong("24HRSTART")){
			rewarder.rewardAll();
		}
	}
}
