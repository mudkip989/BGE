package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class ModelDisplay extends Object{

    private ItemDisplay entity;

    public ModelDisplay(Matrix4f transformation, World w, UUID game, ItemStack item) {
        super(transformation, w, game);
        entity = (ItemDisplay) w.spawnEntity(TransformUtils.getLocation(transformation, w), EntityType.ITEM_DISPLAY);
        entity.setItemStack(item);

        entity.setTeleportDuration(0);
        entity.setInterpolationDuration(0);
        entity.setInterpolationDelay(0);
    }

    @Override
    public void delete() {
        super.delete();
        entity.remove();
    }

    @Override
    public void teleport(Matrix4f trans, World w) {
        Location loc = TransformUtils.getLocation(trans, w);
        BGE.EntityTeleportQueue.put(entity, loc.setRotation(0, 0));
        new Matrix4f();
        Matrix4f temp;
        try {
            temp = (Matrix4f) trans.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        entity.setTransformationMatrix(temp.setTranslation(0, 0, 0));

    }
}
