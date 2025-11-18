package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.joml.*;
import us.mudkip989.plugins.bge.dataTypes.*;
import us.mudkip989.plugins.bge.game.object.tags.*;

import java.util.*;

public class BasicButton extends ModelDisplay {

    public hitbox bounding;

    public BasicButton(Matrix4f transformation, World w, UUID game, ItemStack item) {
        super(transformation, w, game, item);
        bounding = new hitbox(1,1);
    }

    public Matrix4f raycastToObject(Vector3f pos, Vector3f dir){

        return null;
    }

}
