package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.phys.Vec3;

public class MathHelper {
   public static long clamp(long val, long min, long max) {
      return Math.min(Math.max(val, min), max);
   }

   public static Vec3 getRelativeOffset(Vec3 origin, float yawDegrees, Vec3 localOffset) {
      return getRelativeOffset(origin, yawDegrees, 0.0F, localOffset);
   }

   public static Vec3 getRelativeOffset(Vec3 origin, float yawDegrees, float pitchDegrees, Vec3 localOffset) {
      double yawRad = Math.toRadians((double)yawDegrees);
      double pitchRad = Math.toRadians((double)pitchDegrees);
      double x1 = localOffset.f_82479_;
      double y1 = localOffset.f_82480_ * Math.cos(pitchRad) - localOffset.f_82481_ * Math.sin(pitchRad);
      double z1 = localOffset.f_82480_ * Math.sin(pitchRad) + localOffset.f_82481_ * Math.cos(pitchRad);
      double x2 = x1 * Math.cos(yawRad) - z1 * Math.sin(yawRad);
      double z2 = x1 * Math.sin(yawRad) + z1 * Math.cos(yawRad);
      return origin.m_82520_(x2, y1, z2);
   }
}
