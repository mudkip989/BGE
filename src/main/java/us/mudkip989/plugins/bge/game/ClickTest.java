package us.mudkip989.plugins.bge.game;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import org.joml.Math;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.game.object.tags.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class ClickTest extends Game{

    public ArrayList<Player> players = new ArrayList<>();
    private ModelDisplay display1, display2;
    private InteractionEntity frame;
    private boolean clickToggle;

    // Test Environment
    public ClickTest(Matrix4f transform, World world) {
        super(transform, world);
        display1 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, new ItemStack(Material.COBBLESTONE));
        display2 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 1, 0, 0)), world, uuid, new ItemStack(Material.DIAMOND_BLOCK));
        frame = new InteractionEntity(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, 1, 1);
        display2.setParent(display1);
        display1.setParent(frame);
        frame.setTransform(new Matrix4f().scale(1).setTranslation(transform.getTranslation(new Vector3f())), world);
        frame.update();
    }



    @Override
    public BukkitTask runGameTask() {

        return new BukkitRunnable() {
            @Override
            public void run() {
                //Click Test happens here.
            }
        }.runTaskTimerAsynchronously(BGE.instance, 1, 1);
    }

    @Override
    public void ClickEvent(PlayerInteractEvent e) {

    }
}
