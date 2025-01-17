package me.saharnooby.plugins.randombox.nms;

import lombok.NonNull;
import org.bukkit.Bukkit;

public final class NMSUtil {

	private static final String VERSION;

	static {
		String name = Bukkit.getServer().getClass().getName();
		if (name.equals("org.bukkit.craftbukkit.CraftServer")) {
			name = Bukkit.getServer().getBukkitVersion();
			String[] parts = name.split("-")[0].split("\\.");
			if (parts.length == 3) {
				VERSION = "v" + parts[0] + "_" + parts[1] + "_R" + parts[2];
			} else if (parts.length == 2) {
				VERSION = "v" + parts[0] + "_" + parts[1] + "_R0";
			} else {
				throw new IllegalStateException("Invalid server version '" + name + "'");
			}
		} else {
			VERSION = name.substring(23, name.lastIndexOf('.'));
		}
	
		// Bukkit.getLogger().info("Server version: " + VERSION);
	
		if (!VERSION.matches("v1_\\d{1,2}_R\\d{1,2}")) {
			throw new IllegalStateException("Invalid server version '" + VERSION + "', server class is " + name);
		}
	}

	/**
	 * @return Server version, for example 'v1_14_R1'.
	 */
	public static String getVersion() {
		return VERSION;
	}

	/**
	 * @return Minor server version, for example '14' from 'v1_14_R1'.
	 */
	public static int getMinorVersion() {
		String version = getVersion();
		version = version.substring(version.indexOf('_') + 1);
		return Integer.parseInt(version.substring(0, version.indexOf('_')));
	}

	public static Class<?> getNMSClass(@NonNull String name) throws ClassNotFoundException {
		return Class.forName("net.minecraft." + (getMinorVersion() >= 17 ? "" : "server." + getVersion() + '.') + name);
	}

	public static Class<?> getCraftClass(@NonNull String name) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + getVersion() + '.' + name);
	}

}
