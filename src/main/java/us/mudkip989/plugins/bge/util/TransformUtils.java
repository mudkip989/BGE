package us.mudkip989.plugins.bge.util;

import org.bukkit.*;
import org.bukkit.util.*;
import org.joml.*;
import org.joml.Math;

public class TransformUtils {
    public static Location getLocation(Matrix4f transform, World world) {
        Vector3f vec = new Vector3f();
        transform.getTranslation(vec);
        return new Location(world, vec.x, vec.y, vec.z);
    }
    public static EulerAngle getEulerRotation(Matrix4f transform) {
        AxisAngle4d rot = transform.getRotation(new AxisAngle4d());
        Matrix3d rotmat = rot.get(new Matrix3d());

        return new EulerAngle(-Math.asin(rotmat.m20), Math.atan2(rotmat.m21, rotmat.m22), Math.atan2(rotmat.m10, rotmat.m00));
    }
    public static Matrix4f getTransform(Location loc){
        return new Matrix4f()
                .setTranslation((float) loc.x(), (float) loc.y(), (float) loc.z())
                .setRotationYXZ(-Math.toRadians(loc.getYaw()), Math.toRadians(loc.getPitch()), 0);
    }

}
