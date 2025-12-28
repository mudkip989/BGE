package us.mudkip989.plugins.bge.game.object;

import net.kyori.adventure.text.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.util.*;

import java.util.*;

public class TextObject extends Object{

    private TextDisplay entity;

    public TextObject(Matrix4f transformation, World w, UUID game, Component text) {
        super(transformation, w, game);
        entity = (TextDisplay) w.spawnEntity(TransformUtils.getLocation(transformation, w), EntityType.TEXT_DISPLAY);
        entity.text(text);

        entity.setTeleportDuration(0);
        entity.setInterpolationDuration(0);
        entity.setInterpolationDelay(0);
        entity.setBillboard(Display.Billboard.FIXED);
        entity.addScoreboardTag("bge");
        entity.addScoreboardTag("game:"+game.toString());
    }

    @Override
    public void delete() {
        super.delete();
        entity.remove();
    }

    @Override
    public void teleport(Matrix4f trans, World w) {
        Location loc = TransformUtils.getLocation(trans, w);
//        BGE.EntityTeleportQueue.put(entity, loc.setRotation(0, 0));

        new Matrix4f();
        Matrix4f temp;
        try {
            temp = (Matrix4f) trans.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        BGE.mainQueue.add(() -> {
            entity.teleport(loc.setRotation(0, 0));
            entity.setTransformationMatrix(temp.setTranslation(0, 0, 0));
        });
    }

}
