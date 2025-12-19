package us.mudkip989.plugins.bge.game.builtin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.game.object.Object;

import java.util.*;

public class OthelloGo extends Game {

    Player p1,p2;
    Object[][] pieces;
    List<Object> boardParts;

    public OthelloGo(Matrix4f transform, World world, String options) {
        super(transform, world, options);



    }

    @Override
    public BukkitTask runGameTask() {
        return null;
    }
}
