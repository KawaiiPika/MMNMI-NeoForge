package xyz.pixelatedw.mineminenomi.api.math;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VectorHelper {
    public static Vec3 rotateAroundX(Vec3 vector, double angle) {
        double y = Math.cos(angle) * vector.y - Math.sin(angle) * vector.z;
        double z = Math.sin(angle) * vector.y + Math.cos(angle) * vector.z;
        return new Vec3(vector.x, y, z);
    }

    public static Vec3 rotateAroundY(Vec3 vector, double angle) {
        double x = Math.cos(angle) * vector.x + Math.sin(angle) * vector.z;
        double z = -Math.sin(angle) * vector.x + Math.cos(angle) * vector.z;
        return new Vec3(x, vector.y, z);
    }

    public static Vec3 rotateAroundZ(Vec3 vector, double angle) {
        double x = Math.cos(angle) * vector.x - Math.sin(angle) * vector.y;
        double y = Math.sin(angle) * vector.x + Math.cos(angle) * vector.y;
        return new Vec3(x, y, vector.z);
    }

    public static Vec3 calculateViewVectorFromBodyRot(float xRot, float yRot) {
        float f = xRot * ((float)Math.PI / 180F);
        float f1 = -yRot * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }

    public static Vec3 getRelativeOffset(Vec3 origin, float yawDegrees, Vec3 localOffset) {
        return getRelativeOffset(origin, yawDegrees, 0.0F, localOffset);
    }

    public static Vec3 getRelativeOffset(Vec3 origin, float yawDegrees, float pitchDegrees, Vec3 localOffset) {
        double yawRad = Math.toRadians((double)yawDegrees);
        double pitchRad = Math.toRadians((double)pitchDegrees);
        double x1 = localOffset.x;
        double y1 = localOffset.y * Math.cos(pitchRad) - localOffset.z * Math.sin(pitchRad);
        double z1 = localOffset.y * Math.sin(pitchRad) + localOffset.z * Math.cos(pitchRad);
        double x2 = x1 * Math.cos(yawRad) - z1 * Math.sin(yawRad);
        double z2 = x1 * Math.sin(yawRad) + z1 * Math.cos(yawRad);
        return origin.add(x2, y1, z2);
    }
}
