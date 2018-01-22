package net.simplyrin.killrecorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KillRecord {

	public static String getPlayer(int rank) {
		Main plugin = Main.getPlugin();
		plugin.reloadConfig();

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(String uuid : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
			String player = plugin.getConfig().getString("Players." + uuid + ".Player");
			int kills = plugin.getConfig().getInt("Players." + uuid + ".Kills");

			map.put(player, kills);
		}

		List<Map.Entry<String,Integer>> entries =  new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return ((Integer) o2.getValue()).compareTo((Integer) o1.getValue());
			}
		});

		rank--;

		try {
			return entries.get(rank).getKey() + ";" + entries.get(rank).getValue();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	public static String getPlayer(int rank) {
		Main plugin = Main.getPlugin();
		plugin.reloadConfig();

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<Integer> list = new ArrayList<Integer>();
		for(String uuid : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
			String player = plugin.getConfig().getString("Players." + uuid + ".Player");
			int kills = plugin.getConfig().getInt("Players." + uuid + ".Kills");

			map.put(kills, player);
			list.add(kills);
		}

		Collections.sort(list, Comparator.reverseOrder());

		for(Integer i : list) {
			Main.getPlugin().getServer().getConsoleSender().sendMessage(Main.getPrefix() + i);
		}

		return map.get(list.get(rank));
	}
	 */

}
