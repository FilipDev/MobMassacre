package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalVars {

	public static YamlConfiguration statis;

	public HashMap<String, Integer> rewards = new HashMap<String, Integer>();
	public boolean isActive = false;
	public static List<String> validMobs = new ArrayList<String>();
	public List<String> topPlayers = new ArrayList<String>();

}