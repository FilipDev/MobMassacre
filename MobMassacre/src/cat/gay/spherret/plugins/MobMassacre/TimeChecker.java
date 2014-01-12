package cat.gay.spherret.plugins.MobMassacre;

public class TimeChecker implements Runnable {

	final Start start = new Start();
	final Rewarder rewarder = new Rewarder();
	boolean isSynced = false;
	Long time;

	public boolean trySync(){
		try{
			time = start.statis.getLong("TIME") - start.statis.getLong("24HRSTART");
			isSynced = true;
			return true;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	public void run() {

		if (isSynced){
			start.statis.set("TIME", System.currentTimeMillis());
			start.saveStats();
			if (start.statis.getLong("TIME") - time >= start.statis.getLong("24HRSTART")){
				start.statis.set("24HRSTART", System.currentTimeMillis());
				rewarder.rewardAll();
			}
		}
		else{
			start.statis.set("TIME", System.currentTimeMillis());
			start.saveStats();
			if (start.statis.getLong("TIME") - 86400000 >= start.statis.getLong("24HRSTART")){
				start.statis.set("24HRSTART", System.currentTimeMillis());
				rewarder.rewardAll();
			}
		}


	}
}
