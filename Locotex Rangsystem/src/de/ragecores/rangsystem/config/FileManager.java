package de.ragecores.rangsystem.config;

public class FileManager {
	private ConfigFile cfgFile;

	public FileManager() {
		this.cfgFile = new ConfigFile();
	}

	public ConfigFile getConfigFile() {
		return this.cfgFile;
	}

}
