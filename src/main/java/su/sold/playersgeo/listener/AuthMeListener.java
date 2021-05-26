package su.sold.playersgeo.listener;


import fr.xephi.authme.events.LoginEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;
import su.sold.playersgeo.Database;
import su.sold.playersgeo.Plugin;
import su.sold.playersgeo.util.Geo;
import java.io.IOException;
import java.util.Objects;

public class AuthMeListener implements Listener {
    private Database db;
    public AuthMeListener(JavaPlugin plugin){
        db = new Database(plugin);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(LoginEvent event) throws IOException {
        final Player ply = event.getPlayer();
        JSONObject data = Geo.getGeoIPData(Objects.requireNonNull(ply.getAddress()).getHostName());
        if(data!=null) {
            Plugin.log.info("[PlayersGeo] "+ply.getName()+ " from "+data.getString("geoplugin_city")+", "+data.getString("geoplugin_countryCode"));
            db.add(ply.getName(), data.getString("geoplugin_city"), data.getString("geoplugin_countryName"), data.getString("geoplugin_countryCode"), data.getString("geoplugin_latitude"), data.getString("geoplugin_longitude"));
        }else{
            Plugin.log.info("[PlayersGeo] Geo data not found for " +ply.getName());
        }
    }

}
