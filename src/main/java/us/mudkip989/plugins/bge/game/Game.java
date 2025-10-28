package us.mudkip989.plugins.bge.game;

import org.bukkit.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.game.object.Object;

import java.util.*;

public abstract class Game {

    public UUID uuid;
    public BukkitTask task;
    public ArrayList<Object> objectTracker = new ArrayList<>();
    public Matrix4f transformation;
    public World world;

    public Game(Matrix4f transform, World world){
        uuid = UUID.randomUUID();
        task = runGameTask();
    }

    public Game(String oldUUID){
        uuid = UUID.fromString(oldUUID);
        task = runGameTask();
    }

    public abstract BukkitTask runGameTask();

}
