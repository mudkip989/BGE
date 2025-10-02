package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.util.*;
import us.mudkip989.plugins.bge.game.object.enums.*;

import java.util.*;

public abstract class Object {
    private Location location;
    private Transformation transform;
    private Object parent;
    private List<Object> children;

    /*
    ----------
    Possible Transform/Teleport Combos

     - Parent Only
     - Parent and Children(Recursive)

     */

//    public void teleport(Location loc){
//        switch(teleportMode){
//            case WORLD -> {
//                location = loc;
//            }
//            case PARENT -> {
//                if (parent == null) {
//                    location = loc;
//                } else {
//                    location = parent.getLocation().add(loc.toVector());
//                }
//
//            }
//            case LOCAL -> {
//
//            }
//        }
//
//        if(children != null){
//            children.forEach((ob) -> {if (ob.teleportMode == TeleportMode.PARENT) ob.update();});
//        }
//    }


    public Location getLocation() {return location.clone();

    }

    public Transformation getWorldSpaceTransform(){
        return null;
    }

    public Location getWorldSpaceLocation() {
        return null;
    }

    public void update() {

    }
    public void update(Location loc, Transformation trans){

    }
}
