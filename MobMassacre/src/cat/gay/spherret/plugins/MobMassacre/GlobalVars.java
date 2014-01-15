package cat.gay.spherret.plugins.MobMassacre;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class GlobalVars {

	public static YamlConfiguration statis;
	public static TreeMap<String, Integer> rewards = new TreeMap<String, Integer>();
	public static List validMobs = new ArrayList<String>();
	public static SortedSet<Map.Entry<String, Integer>> entryset;
	public static Map.Entry<String, Integer>[] entryarray;
	public static boolean alreadyArranged;
}