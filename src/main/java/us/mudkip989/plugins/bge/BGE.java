package us.mudkip989.plugins.bge;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import us.mudkip989.plugins.bge.Listeners.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.builtin.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

public final class BGE extends JavaPlugin {

    public Logger logger;
    //public final PaperPluginLogger logger = (PaperPluginLogger) PaperPluginLogger.getLogger(String.valueOf(this));
    public static Integer BGEAPIVersion = 1;
    public static BGE instance;
    public AddonLoader addonLoader;

    private static HashMap<String, HashMap<String, Class<? extends Game>>> gameRegistry = new HashMap<>();



    //    private static HashMap<String, Class<? extends Game>> gameRegistry = new HashMap<>();
    public static HashMap<UUID, Game> gameInstances = new HashMap<>();
    public static Queue<Runnable> mainQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onLoad() {
        // Plugin startup logic
        saveDefaultConfig();

        instance = this;

        logger = instance.getLogger();

        //variable initializers
        //Needs to happen before the enabling process so that other plugins dont fail to get added.
        addonLoader = new AddonLoader();
    }

    @Override
    public void onEnable() {             // Non-functional, testing in external project



        // ^ Starter for configs ^

        PluginManager PM = Bukkit.getPluginManager();
        logger.fine("Registering Listeners and Events");
        this.getCommand("boardgameengine").setExecutor(new CommandListener());
        this.getCommand("boardgameengine").setTabCompleter(new CommandCompleter());
        PM.registerEvents(new PassableEventListener(), this);




        logger.fine("Starting Background Tasks");
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
        }.runTaskTimer(BGE.instance, 1, 5);

//        reload(false);

        //Trigger auto-mount after 10 seconds
        new BukkitRunnable(){
            @Override
            public void run() {



            }
        }.runTaskLater(BGE.instance, 200);


    }

    private void loadGames(){

        logger.fine("Registering Built-In Game Tests. (please move this to a separate method)");
        registerGame("bge", "rottest", RotationTest.class);
        registerGame("bge", "clicktest", ClickTest.class);
        registerGame("bge", "hovertest", HoverTest.class);

    }

    public void reload(Boolean Panic){
        Set<UUID> uuids = gameInstances.keySet();

        for(UUID uuid: uuids){
            gameInstances.get(uuid).delete();
        }

        gameRegistry = new HashMap<>();
        gameInstances = new HashMap<>();

        addonLoader.oldunloadAddons();
        loadGames();
        if(!Panic) {
            addonLoader.oldloadAddons();
        }
    }



    public static void registerGame(String namespace, String id, Class<? extends Game> game){
        if(!gameRegistry.containsKey(namespace)) {
            HashMap<String, Class<? extends Game>> mappy = new HashMap<>();
            mappy.put(id, game);
            gameRegistry.put(namespace, mappy);
        }else{
            gameRegistry.get(namespace).put(id, game);
        }
        BGE.instance.logger.fine("Registered game ID: " + id);
    }

    public List<String> getGameIds(){
        List<String> responses = new ArrayList<>();

        gameRegistry.forEach((namespace, secondmap) -> {
            secondmap.keySet().forEach(id -> {
                responses.add(namespace + ":" + id);
            });

        });
        return responses;
    }

    public boolean startGame(String id, Location loc, String options) {
        String[] idSplit = id.split(":");
        String idnamespace = idSplit[0];
        String idgame = idSplit[1];
        if(!getGameIds().contains(id)) return false;
        try {
            gameRegistry.get(idnamespace).get(idgame).getDeclaredConstructor(Matrix4f.class, World.class, String.class)
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
