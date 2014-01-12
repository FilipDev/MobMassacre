package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Entity;

import java.io.File;

public class Events implements Listener{

	GlobalVars gbvs = new GlobalVars();
	Start start = new Start();
	Stats stats = new Stats();

	@EventHandler
	public void onPlayerKillMob(EntityDeathEvent e){
		Entity entity = e.getEntity();
		Player p;
		try{
			p = e.getEntity().getKiller();
		}catch (Exception er){
			return;
		}
		if (gbvs.validMobs.contains(entity))
			stats.changeKill(p, entity);
	}
}
