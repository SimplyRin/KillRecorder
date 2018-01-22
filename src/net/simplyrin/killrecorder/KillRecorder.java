package net.simplyrin.killrecorder;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillRecorder implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("killrecorder.use") && !sender.getName().equalsIgnoreCase("SimplyRin")) {
			sender.sendMessage(Main.getPrefix() + "§cYou do not have access to this command");
			sender.sendMessage(Main.getPrefix() + "§cPlease contact to SimplyRin!");
			return true;
		}

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("rank")) {
				int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
				Arrays.sort(ranks);
				for(int rank : ranks) {
					String name = KillRecord.getPlayer(rank);
					if(name != null) {
						sender.sendMessage(Main.getPrefix() + "§e#" + rank + " §b" + name.split(";")[0] + " - §e" + name.split(";")[1]);
					} else {
						sender.sendMessage(Main.getPrefix() + "§e#" + rank + " §cnull");
					}
				}
				return true;
			}
		}
		sender.sendMessage(Main.getPrefix() + "§cUsage: /" + cmd.getName() + " <rank>");
		return true;
	}

}
