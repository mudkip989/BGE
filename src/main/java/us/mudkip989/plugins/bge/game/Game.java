package us.mudkip989.plugins.bge.game;

import org.bukkit.scheduler.*;

import java.util.*;

public abstract class Game {

    public UUID uuid;

    public Game(){
        uuid = UUID.randomUUID();
    }

    public Game(String oldUUID){
        uuid = UUID.fromString(oldUUID);
    }


    public abstract BukkitTask runGameTask();

}
