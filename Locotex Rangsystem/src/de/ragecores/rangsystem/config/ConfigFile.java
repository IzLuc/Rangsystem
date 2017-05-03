package de.ragecores.rangsystem.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFile extends FileBase {

	public ConfigFile() {
		super("", "mysql");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();

		cfg.addDefault("MySQL.Host", "host");
		cfg.addDefault("MySQL.Port", "3306");
		cfg.addDefault("MySQL.User", "user");
		cfg.addDefault("MySQL.Pass", "pass");
		cfg.addDefault("MySQL.DB", "db");

		cfg.options().copyDefaults(true);
		saveConfig();
	}
}
