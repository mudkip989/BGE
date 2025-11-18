package us.mudkip989.plugins.bge.game;

import io.papermc.paper.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.object.Object;

import java.util.*;

public abstract class Game {

    public UUID uuid;
    public BukkitTask task;
    public HashMap<UUID, Object> objectTracker = new HashMap<>();
    public Matrix4f transformation;
    public World world;



    public Game(Matrix4f transform, World world){
        uuid = UUID.randomUUID();
        task = runGameTask();
        BGE.gameInstances.put(uuid, this);
    }

    public Game(String oldUUID){
        uuid = UUID.fromString(oldUUID);
        task = runGameTask();
    }

    public abstract BukkitTask runGameTask();

    public void ClickEvent(PlayerInteractEvent e){

    }

}
