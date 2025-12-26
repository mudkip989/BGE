package us.mudkip989.plugins.bge.game.builtin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.joml.*;
import us.mudkip989.plugins.bge.dataTypes.*;
import us.mudkip989.plugins.bge.game.*;
import us.mudkip989.plugins.bge.game.object.*;
import us.mudkip989.plugins.bge.game.object.Object;
import us.mudkip989.plugins.bge.util.*;


import java.util.*;

import static us.mudkip989.plugins.bge.game.processors.CommandOptionParser.parseOptionFloat;
import static us.mudkip989.plugins.bge.game.processors.CommandOptionParser.parseOptionInt;

public class Othello extends Game {

    Player p1,p2;
    Object[][] pieces;
    List<Object> boardParts;
    InteractionEntity frame;

    public Othello(Matrix4f transform, World wrld, String options) {
        super(transform, wrld, options);
        //create frame
        frame = new InteractionEntity(TransformUtils.getTransform(new Location(world, 0, 0, 0)), world, uuid, 2, 1);


        //get tags
        float scale = parseOptionFloat(options,"scale");
        if(scale != 0) {
            transformation.scale(scale);
        }

        int boardSize = parseOptionInt(options,"size");
        if(boardSize == 0){
            boardSize = 8;
        }
        frame.setHeight(scale);
        frame.setWidth(2*scale);

        //init list objects
        boardParts = new ArrayList<>();
        pieces = new Object[boardSize][boardSize];


        //spawn parts
        //base
        ModelDisplay baseboard = new ModelDisplay(TransformUtils.blankTransform()
                .translate(0f, 0.05f, 0f)
                .scale(1, 0.1f, 1),
                world, uuid, new ItemStack(Material.SANDSTONE));
        baseboard.setParent(frame);
        boardParts.add(baseboard);

        //spawn lines
        for (int i = 0; i < boardSize; i++){
            ModelDisplay line = new ModelDisplay(TransformUtils.blankTransform()
                    .translate((1f / boardSize) * (i+0.5f) - 0.5f, 0.101f, 0)
                    .scale(0.1f/boardSize, 0.002f, 1),
                    world, uuid, new ItemStack(Material.BLACK_CONCRETE));
            line.setParent(frame);
        }

        for (int i = 0; i < boardSize; i++){
            ModelDisplay line = new ModelDisplay(TransformUtils.blankTransform()
                    .translate(0, 0.101f, (1f / boardSize) * (i+0.5f) - 0.5f)
                    .scale(1, 0.002f, 0.1f/boardSize),
                    world, uuid, new ItemStack(Material.BLACK_CONCRETE));
            line.setParent(frame);
        }


        //spawn pieces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++){
                GroupObject part = new GroupObject(TransformUtils.blankTransform()
                        .setTranslation((1f / boardSize) * (i+0.5f) - 0.5f, 0.1f + ((0.4f/boardSize)/2), (1f / boardSize) * (j+0.5f) - 0.5f)
                        .scale(0.6f/boardSize, 0.4f/boardSize, 0.6f/boardSize), world, uuid);
                part.setParent(frame);
                ModelDisplay piece = new ModelDisplay(new Matrix4f(), world, uuid, new ItemStack(Material.GLASS));
                piece.setParent(part);
                BasicButton collider = new BasicButton(new Matrix4f(), world, uuid, new hitbox(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));
                collider.setParent(part);
                pieces[i][j] = part;
            }

        }





        Vector3f rotation = transformation.getRotation(new AxisAngle4f()).get(new Quaternionf()).getEulerAnglesYXZ(new Vector3f());
        Vector3f scaling = transformation.getScale(new Vector3f());
        frame.setTransform(transformation.setRotationYXZ(rotation.y, 0f, 0f).scale(scaling), world);
        frame.update();
    }

    @Override
    public BukkitTask runGameTask() {
        return null;
    }
}
