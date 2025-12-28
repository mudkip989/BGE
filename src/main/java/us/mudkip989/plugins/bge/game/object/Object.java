package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.util.*;
import org.joml.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.object.enums.*;

import java.util.*;

public abstract class Object {
    public Matrix4f transform;
    public World world;
    private Object parent;
    private List<Object> children;
    protected UUID uuid;
    private UUID gameId;
    public List<String> tagList;

    public Object(Matrix4f location, World w, UUID gid) {
        parent = null;
        transform = location;
        uuid = UUID.randomUUID();
        children = new ArrayList<>();
        world = w;
        gameId = gid;
        BGE.gameInstances.get(gid).objectTracker.put(uuid, this);
    }

    public Object(Matrix4f location, World w, UUID gid, Object obj) {
        if (obj != null){
            parent = obj;
            parent.addChild(this);
        }
        transform = location;
        uuid = UUID.randomUUID();
        children = new ArrayList<>();
        world = w;
        gameId = gid;
        BGE.gameInstances.get(gid).objectTracker.put(uuid, this);
    }



    //Grab Transform from parent and apply to current
    public Matrix4f getWorldSpaceTransform() {
        Matrix4f ptran;
        Matrix4f localtran = new Matrix4f();
        if(parent != null) {
            ptran = parent.getWorldSpaceTransform();
        } else {
            ptran = new Matrix4f().scale(1);
        }
        ptran.mul(transform, localtran);

        return localtran;
    }

    public Matrix4f getWorldSpaceTransform(Matrix4f ptran) {
        Matrix4f localtran = new Matrix4f();
        ptran.mul(transform, localtran);

        return localtran;
    }

    public abstract void teleport(Matrix4f trans, World w);

    public void setTransform(Matrix4f trans, World w){
        try {
            transform = (Matrix4f) trans.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mulTransform(Matrix4f trans){
        trans.mul(transform, transform);
    }

    public void delete() {
        parent = null;

        BGE.gameInstances.get(gameId).objectTracker.remove(uuid);
        if(children != null) {
            for (Object obj : children) {
                obj.delete();
            }
        }
        children = null;
    }

    public void update() {
        Matrix4f trans = getWorldSpaceTransform();
        teleport(trans, world);
        children.forEach(child -> child.update(trans, world));
        //Pull info apply to this
    }

    public void update(Matrix4f trans, World w) {
        Matrix4f trans2 = getWorldSpaceTransform(trans);
        teleport(trans2, w);
        children.forEach(child -> child.update(trans2, w));
        //parent force updating this
    }

    public Object setParent(Object obj) {
        if(parent != null){
            parent.removeChild(this);
        }

        parent = obj;
        if(parent != null){
            parent.addChild(this);
        }
        return this;
    }

    protected Object addChild(Object obj) {
        if(obj != null) {
            children.add(obj);
        }
        return this;
    }

    protected Object removeChild(Object obj) {
        if(obj != null) {
            children.remove(obj);
        }
        return this;
    }



}
