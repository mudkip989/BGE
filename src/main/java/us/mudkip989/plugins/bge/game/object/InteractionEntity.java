package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class InteractionEntity extends Object{
    private Interaction entity;

    public InteractionEntity(Matrix4f transformation, World w, UUID game, float width, float height) {
        super(transformation, w, game);
        entity = (Interaction) w.spawnEntity(TransformUtils.getLocation(transformation, w), EntityType.INTERACTION);
        entity.setInteractionHeight(height);
        entity.setInteractionWidth(width);
        uuid = UUID.randomUUID();
    }

    @Override
    public void delete() {
        super.delete();
        entity.remove();
    }

    @Override
    public void teleport(Matrix4f trans, World w) {
        Location loc = TransformUtils.getLocation(trans, w);
        BGE.EntityTeleportQueue.put(entity, loc);

    }
}
