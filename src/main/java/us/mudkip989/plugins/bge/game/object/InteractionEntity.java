package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.joml.*;

import java.util.*;

public class InteractionEntity extends Object{
    private Interaction entity;

    public InteractionEntity(Location loc, float width, float height){
        entity = (Interaction) loc.getWorld().spawnEntity(loc, EntityType.INTERACTION);
        entity.setInteractionHeight(height);
        entity.setInteractionWidth(width);
        uuid = UUID.randomUUID();
    }

    @Override
    public void teleport(Matrix4f trans) {

    }
}
