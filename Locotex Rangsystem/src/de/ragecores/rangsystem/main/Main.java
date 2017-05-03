package de.ragecores.rangsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.ragecores.rangsystem.config.FileManager;
import de.ragecores.rangsystem.database.SQLManager;
import de.ragecores.rangsystem.utils.LogHandler;


public class Main extends JavaPlugin {

    public static String prefix = "§8[§cRageCores§8] §e";
    public static Main instance;
    private static SQLManager sqlManager;
    private static FileManager fileManager;
    
    public void onEnable(){
	instance = this;
	fileManager = new FileManager();

	if (!loadSQL()) {
	    LogHandler.err("Es konnte keine Verbindung zur MySQL Datenbank hergestellt werden");
	    Bukkit.getPluginManager().disablePlugin(instance);

	    return;
	} else {
	    LogHandler.err("MySQL Verbindung hergestellt!");

	}


	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    public static Main getInstance() {
	return instance;
    }



    public static SQLManager getSQLManager() {
	return sqlManager;
    }

    public static String getPrefix() {
	return prefix;
    }

    private boolean loadSQL() {
	FileConfiguration cfg = fileManager.getConfigFile().getConfig();
	String host = cfg.getString("MySQL.Host");
	String port = cfg.getString("MySQL.Port");
	String user = cfg.getString("MySQL.User");
	String pass = cfg.getString("MySQL.Pass");
	String database = cfg.getString("MySQL.DB");
	sqlManager = new SQLManager(host, port, user, pass, database);
	return sqlManager.openConnection();
    }
}
