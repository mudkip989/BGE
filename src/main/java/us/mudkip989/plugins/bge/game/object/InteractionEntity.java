package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class InteractionEntity extends Object{
    private Interaction entity;
    private UUID uuid;

    public InteractionEntity(Location loc, float width, float height){
        entity = (Interaction) loc.getWorld().spawnEntity(loc, EntityType.INTERACTION);
        entity.setInteractionHeight(height);
        entity.setInteractionWidth(width);
        uuid = UUID.randomUUID();
    }
}
