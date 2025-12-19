package us.mudkip989.plugins.bge.game.builtin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.dataTypes.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class HoverTest extends Game{

        public ArrayList<Player> players = new ArrayList<>();
        private ModelDisplay display1, display2;
        private BasicButton button;
        private InteractionEntity frame;
        private boolean clickToggle;

        // Test Environment
        public HoverTest(Matrix4f transform, World world, String options) {
            super(transform, world, "");
            //Object init
            display1 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, new ItemStack(Material.COBBLESTONE));
            display2 = new ModelDisplay(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, new ItemStack(Material.DIAMOND_BLOCK));
            button = new BasicButton(TransformUtils.getTransform(new Location(world, 1, 0, 0)).scale(new Vector3f(1, 2, 1)), world, uuid, new hitbox(new Vector3f(1,1,1), new Vector3f(0,0,0)));
            frame = new InteractionEntity(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, 1, 1);
            //Parenting
            display2.setParent(button);
            display1.setParent(frame);
            button.setParent(display1);
            frame.setTransform(transform, world);
            frame.update();

            clickToggle = false;
        }



        @Override
        public BukkitTask runGameTask() {

            return new BukkitRunnable() {
                @Override
                public void run() {

                    Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();

                    long count = players.stream()
                            .filter(player -> button.raycastToObject(player.getEyeLocation().toVector().toVector3f(), player.getEyeLocation().getDirection().toVector3f(), player.getWorld()).hit())
                            .count();


                    if(count > 0) {
                        display2.setItemMaterial(new ItemStack(Material.GOLD_BLOCK));
//                        players.stream()
//                                .filter(player -> button.raycastToObject(player.getEyeLocation().toVector().toVector3f(), player.getEyeLocation().getDirection().toVector3f(), player.getWorld()).hit()).forEach(player -> {
//                                    Vector3f vec = button.raycastToObject(player.getEyeLocation().toVector().toVector3f(), player.getEyeLocation().getDirection().toVector3f(), player.getWorld()).hitLoc();
//                                    Location loc = new Location(player.getWorld(), vec.x, vec.y, vec.z);
//                                    player.spawnParticle(Particle.END_ROD, loc, 1, 0, 0, 0);
//                                });
                    }else {
                        display2.setItemMaterial(new ItemStack(Material.DIAMOND_BLOCK));
                    }
                    frame.update();
                }
            }.runTaskTimerAsynchronously(BGE.instance, 1, 1);
        }




}
