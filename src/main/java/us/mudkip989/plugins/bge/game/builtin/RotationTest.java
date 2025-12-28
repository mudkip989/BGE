package us.mudkip989.plugins.bge.game.builtin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import org.joml.Math;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.util.*;


import java.util.*;

public class RotationTest extends Game {

    public ArrayList<Player> players = new ArrayList<>();
    private ModelDisplay display1, display2;
    private InteractionEntity frame;

    // Test Environment
    public RotationTest(Matrix4f transform, World wrld, String options) {
        super(transform, wrld, "");
        display1 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, new ItemStack(Material.COBBLESTONE));
        display2 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 1, 0, 0)), world, uuid, new ItemStack(Material.DIAMOND_BLOCK));
        frame = new InteractionEntity(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, 1, 1);
        display2.setParent(display1);
        display1.setParent(frame);
        frame.setTransform(transformation, world);
        frame.update();
    }



    @Override
    public BukkitTask runGameTask() {

        return new BukkitRunnable() {
            @Override
            public void run() {
                //Rotation Test happens here.
                display1.mulTransform(new Matrix4f().scale(1).setRotationYXZ(Math.toRadians(10), Math.toRadians(2), Math.toRadians(0)));
                display1.update();
            }
        }.runTaskTimerAsynchronously(BGE.instance, 20, 1);
    }


}
