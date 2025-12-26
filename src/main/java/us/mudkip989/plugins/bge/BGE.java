package us.mudkip989.plugins.bge;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.Listeners.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.builtin.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;
import java.util.concurrent.*;

public final class BGE extends JavaPlugin {

    public static BGE instance;
//    public static HashMap<Entity, Location> EntityTeleportQueue = new HashMap<>();
    private static HashMap<String, Class<? extends Game>> gameRegistry = new HashMap<>();
    public static HashMap<UUID, Game> gameInstances = new HashMap<>();
    public static Queue<Runnable> mainQueue = new ConcurrentLinkedQueue<>();



    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PluginManager PM = Bukkit.getPluginManager();
        this.getCommand("boardgameengine").setExecutor(new CommandListener());
        this.getCommand("boardgameengine").setTabCompleter(new CommandCompleter());
        PM.registerEvents(new PassableEventListener(), this);
        registerGame("bge:rottest", RotationTest.class);
        registerGame("bge:clicktest", ClickTest.class);
        registerGame("bge:hovertest", HoverTest.class);
        registerGame("bge:othello", Othello.class);
        new BukkitRunnable() {
            @Override
            public void run() {
                Runnable task;
                while ((task = mainQueue.poll()) != null) {
                    task.run();
                }


//                if(!EntityTeleportQueue.isEmpty()){
//                    EntityTeleportQueue.forEach(Entity::teleport);
//                    EntityTeleportQueue = new HashMap<>();
//                }
            }
        }.runTaskTimer(BGE.instance, 1, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(World world: Bukkit.getWorlds()){
                    world.getEntities().stream().filter(entity -> (entity.getScoreboardTags().contains("bge"))).filter(entity -> {
                        UUID gameId = null;
                        for(String tag: entity.getScoreboardTags()){
                            if(tag.startsWith("game:")){
                                gameId = UUID.fromString(tag.substring(5));
                                break;
                            }

                        }
                        return !gameInstances.containsKey(gameId);
                    }).forEach(Entity::remove);
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

    public boolean startGame(String id, Location loc, String options) {
        if(!getGameIds().contains(id)) return false;
        try {
            gameRegistry.get(id).getDeclaredConstructor(Matrix4f.class, World.class, String.class)
                    .newInstance(TransformUtils.getTransform(loc), loc.getWorld(), options);
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
