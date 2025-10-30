package us.mudkip989.plugins.bge;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.Listeners.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public final class BGE extends JavaPlugin {

    public static BGE instance;
    public static HashMap<Entity, Location> EntityTeleportQueue = new HashMap<>();
    private static HashMap<String, Class<? extends Game>> gameRegistry = new HashMap<>();
    public static HashMap<UUID, ? extends Game> gameInstances = new HashMap<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PluginManager PM = Bukkit.getPluginManager();
        this.getCommand("boardgameengine").setExecutor(new CommandListener());
        registerGame("bge:rottest", RotationTest.class);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!EntityTeleportQueue.isEmpty()){
                    EntityTeleportQueue.forEach(Entity::teleport);
                    EntityTeleportQueue = new HashMap<>();
                }
            }
        }.runTaskTimer(BGE.instance, 1, 1);

    }

    public void registerGame(String id, Class<? extends Game> game){
        gameRegistry.put(id, game);
    }

    public List<String> getGameIds(){
        return gameRegistry.keySet().stream().toList();
    }

    public boolean startGame(String id, Location loc) {
        if(!getGameIds().contains(id)) return false;
        try {
            gameRegistry.get(id).getDeclaredConstructor(Matrix4f.class, World.class).newInstance(TransformUtils.getTransform(loc), loc.getWorld());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
