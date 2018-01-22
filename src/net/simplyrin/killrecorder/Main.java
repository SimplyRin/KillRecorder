package net.simplyrin.killrecorder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private static Main plugin;
	private static Boolean isEnabled = false;

	@Override
	public void onEnable() {
		plugin = this;

		isEnabled = Boolean.valueOf(plugin.rawWithAgent("https://api.simplyrin.net/Spigot-Plugins/KillRecorder/verification/"));
		if(!isEnabled) {
			return;
		}

		plugin.saveDefaultConfig();
		plugin.getServer().getPluginManager().registerEvents(this, this);
		plugin.getCommand("killrecorder").setExecutor(new KillRecorder());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(!isEnabled) {
			return;
		}

		Player player = event.getPlayer();

		plugin.getConfig().set("Players." + player.getUniqueId().toString() + ".Player", player.getName());
		plugin.saveConfig();
		plugin.reloadConfig();

		if(!plugin.getConfig().isSet("Players." + player.getUniqueId().toString() + ".Kills")) {
			plugin.getConfig().set("Players." + player.getUniqueId().toString() + ".Kills", 0);
			plugin.saveConfig();
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(!isEnabled) {
			return;
		}

		Player player = event.getEntity().getKiller();

		int kills = plugin.getConfig().getInt("Players." + player.getUniqueId().toString() + ".Kills");
		kills++;

		plugin.getConfig().set("Players." + player.getUniqueId().toString() + ".Kills", kills);
		plugin.saveConfig();
		plugin.reloadConfig();
	}

	private String rawWithAgent(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(true);
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setReadTimeout(15000);
			connection.setConnectTimeout(15000);
			connection.setDoOutput(true);
			InputStream is = connection.getInputStream();
			Charset encoding = Charset.defaultCharset();
			String s = IOUtils.toString(is, encoding);
			if (s != null) {
				return s;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Main getPlugin() {
		return plugin;
	}

	public static String getPrefix() {
		return "§7[§cKillRecorder§7] §r";
	}

}
