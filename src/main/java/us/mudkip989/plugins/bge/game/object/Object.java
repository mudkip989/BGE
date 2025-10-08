package us.mudkip989.plugins.bge.game.object;

import org.bukkit.*;
import org.bukkit.util.*;
import org.joml.*;
import us.mudkip989.plugins.bge.game.object.enums.*;

import java.util.*;

public abstract class Object {
    private Matrix4f transform;
    private Object parent;
    private List<Object> children;
    protected UUID uuid;

    public Object(Matrix4f location){
        parent = null;
        transform = location;
        uuid = UUID.randomUUID();
        children = new ArrayList<>();
    }
    public Object(Matrix4f location, Object obj){
        if (obj != null){
            parent = obj;
            parent.addChild(this);
        }
        transform = location;
        uuid = UUID.randomUUID();
        children = new ArrayList<>();
    }

    //Grab Transform from parent and apply to current
    public Matrix4f getWorldSpaceTransform(){
        Matrix4f ptran;
        Matrix4f localtran = new Matrix4f();
        if(parent != null) {
            ptran = parent.getWorldSpaceTransform();
        } else {
            ptran = new Matrix4f().scale(1);
        }

        localtran.translate(transform.getTranslation(new Vector3f()));
        localtran.rotation(transform.getRotation(new AxisAngle4f()));
        localtran.mul(ptran);

        return localtran;
    }

    public Matrix4f getWorldSpaceTransform(Matrix4f ptran) {
        Matrix4f localtran = new Matrix4f();
        localtran.translate(transform.getTranslation(new Vector3f()));
        localtran.rotation(transform.getRotation(new AxisAngle4f()));
        localtran.mul(ptran);

        return localtran;
    }

    public abstract void teleport(Matrix4f trans);

    public void update() {
        Matrix4f trans = getWorldSpaceTransform();
        teleport(trans);
        children.forEach(child -> child.update(trans));
        //Pull info apply to this
    }

    public void update(Matrix4f trans){

        teleport(trans);
        children.forEach(child -> child.update(trans));
        //parent force updating this
    }

    public void setParent(Object obj){
        if(parent != null){
            parent.removeChild(this);
        }

        parent = obj;
        if(parent != null){
            parent.addChild(this);
        }
    }

    protected void addChild(Object obj){
        if(obj != null) {
            children.add(obj);
        }
    }

    protected void removeChild(Object obj){
        if(obj != null) {
            children.remove(obj);
        }
    }

}
