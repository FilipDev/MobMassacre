package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class GlobalVars {

	public static YamlConfiguration statis;
	public static HashMap<String, Integer> rewards = new HashMap<String, Integer>();
	public static List validMobs = new ArrayList<String>();
	public static TreeMap<String, Integer> kills = new TreeMap<String, Integer>();
	public static HashMap<String, Integer> extrapts = new HashMap<String, Integer>();

}