package us.mudkip989.plugins.bge.game;

import io.papermc.paper.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.dataTypes.*;
import us.mudkip989.plugins.bge.game.object.Object;

import java.util.*;

import static us.mudkip989.plugins.bge.game.processors.CommandOptionParser.parseOptionFloat;

public abstract class Game {

    public UUID uuid;
    public BukkitTask task;
    public HashMap<UUID, Object> objectTracker = new HashMap<>();
    public Matrix4f transformation;
    public World world;



    public Game(Matrix4f transform, World wrld, String options){
        uuid = UUID.randomUUID();
        task = runGameTask();

        Vector3f rotation = transform.getRotation(new AxisAngle4f()).get(new Quaternionf()).getEulerAnglesYXZ(new Vector3f());

        FlagData rotX = parseOptionFloat(options, "rotationX");
        FlagData rotY = parseOptionFloat(options, "rotationY");
        FlagData rotZ = parseOptionFloat(options, "rotationZ");

        if(rotX != null){
            rotation.x = (float) rotX.val();
        }
        if(rotY != null){
            rotation.y = (float) rotY.val();
        }
        if(rotZ != null){
            rotation.z = (float) rotZ.val();
        }

        Vector3f translation = transform.getTranslation(new Vector3f());

        FlagData tranX = parseOptionFloat(options, "positionX");
        FlagData tranY = parseOptionFloat(options, "positionY");
        FlagData tranZ = parseOptionFloat(options, "positionZ");

        if(tranX != null){
            translation.x = (float) tranX.val();
        }
        if(tranY != null){
            translation.y = (float) tranY.val();
        }
        if(tranZ != null){
            translation.z = (float) tranZ.val();
        }


        transformation = transform.setTranslation(translation).setRotationYXZ(rotation.y, rotation.x, rotation.z);
        world = wrld;
        BGE.gameInstances.put(uuid, this);
    }

    public Game(String oldUUID){
        uuid = UUID.fromString(oldUUID);
        task = runGameTask();
    }

    public abstract BukkitTask runGameTask();

    public void ClickEvent(PlayerInteractEntityEvent e){

    }

    public void delete(){
        if(task != null) {
            task.cancel();
        }
        List<Object> objList = objectTracker.values().stream().toList();
        for(Object obj: objList){
            obj.delete();
        }
    }

}
