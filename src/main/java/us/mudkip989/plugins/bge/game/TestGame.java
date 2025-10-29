package us.mudkip989.plugins.bge.game;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class TestGame extends Game{

    public ArrayList<Player> players = new ArrayList<>();
    private ModelDisplay display1, display2;

    // Test Environment
    public TestGame(Matrix4f transform, World world) {
        super(transform, world);
        display1 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, new ItemStack(Material.COBBLESTONE));
        display2 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 1, 0, 0)), world, uuid, new ItemStack(Material.DIAMOND_BLOCK));

        display2.setParent(display1);
        System.out.println("1");
        System.out.println(transform);
        System.out.println(transform.getTranslation(new Vector3f()));
        display1.setTransform(transform, world);
        display1.update();
    }



    @Override
    public BukkitTask runGameTask() {

        return new BukkitRunnable() {
            @Override
            public void run() {
                //Rotation Test happens here.
            }
        }.runTaskTimerAsynchronously(BGE.instance, 1, 1);
    }


}
