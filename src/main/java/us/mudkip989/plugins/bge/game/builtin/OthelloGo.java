package us.mudkip989.plugins.bge.game.builtin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.object.*;

import java.util.*;

public class OthelloGo extends Game {

    Player p1,p2;
    ModelDisplay[][] pieces = new ModelDisplay[8][8];

    public OthelloGo(Matrix4f transform, World world) {
        super(transform, world);
    }

    @Override
    public BukkitTask runGameTask() {
        return null;
    }
}
