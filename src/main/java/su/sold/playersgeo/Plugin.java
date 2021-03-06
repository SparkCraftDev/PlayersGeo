package su.sold.playersgeo;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import su.sold.playersgeo.listener.AuthMeListener;
import su.sold.playersgeo.listener.PlayerJoinListener;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {

    public static final Logger log  = Logger.getLogger("Minecraft");
    private Listener authMeListener;
    private Listener playerJoinListener;
    public FileConfiguration cfg = getConfig();
    public Database db = new Database(this);
    @Override
    public void onEnable() {
        saveDefaultConfig();
        final PluginManager pluginManager = getServer().getPluginManager();
        if(cfg.getBoolean("authme")){
            log.info("§c[§ePlayers§6Geo§c] §fStarting in AuthMe mode...");
            if (pluginManager.isPluginEnabled("AuthMe")) {
                if (authMeListener == null) {
                    authMeListener = new AuthMeListener(this);
                    getServer().getPluginManager().registerEvents(authMeListener, this);
                }
            }else{
                log.severe("AuthMe mode is enabled in the config, but AuthMe plugin is disabled or not installed.");
                this.setEnabled(false);
            }
        }else{
            log.info("§c[§ePlayers§6Geo§c] §fStarting in normal mode...");
            if(playerJoinListener == null){
                playerJoinListener = new PlayerJoinListener(this);
                getServer().getPluginManager().registerEvents(playerJoinListener, this);
            }
        }
        PluginCommand geoCommand = getCommand("geo");
        assert geoCommand != null;
        geoCommand.setExecutor(new GeoCommand(this));
    }

    @Override
    public void onDisable() {
        log.info("Disabling PlayersGeo...");
    }


}
