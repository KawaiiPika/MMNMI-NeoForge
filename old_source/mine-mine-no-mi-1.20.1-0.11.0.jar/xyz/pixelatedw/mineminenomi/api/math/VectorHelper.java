package xyz.pixelatedw.mineminenomi.api.math;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VectorHelper {
   public static Vec3 rotateAroundX(Vec3 vector, double angle) {
      double y = Math.cos(angle) * vector.f_82480_ - Math.sin(angle) * vector.f_82481_;
      double z = Math.sin(angle) * vector.f_82480_ + Math.cos(angle) * vector.f_82481_;
      vector = new Vec3(vector.f_82479_, y, z);
      return vector;
   }

   public static Vec3 rotateAroundY(Vec3 vector, double angle) {
      double x = Math.cos(angle) * vector.f_82479_ + Math.sin(angle) * vector.f_82481_;
      double z = -Math.sin(angle) * vector.f_82479_ + Math.cos(angle) * vector.f_82481_;
      vector = new Vec3(x, vector.f_82480_, z);
      return vector;
   }

   public static Vec3 rotateAroundZ(Vec3 vector, double angle) {
      double x = Math.cos(angle) * vector.f_82479_ - Math.sin(angle) * vector.f_82480_;
      double y = Math.sin(angle) * vector.f_82479_ + Math.cos(angle) * vector.f_82480_;
      vector = new Vec3(x, y, vector.f_82481_);
      return vector;
   }

   public static Vec3 calculateViewVectorFromBodyRot(float xRot, float yRot) {
      float f = xRot * ((float)Math.PI / 180F);
      float f1 = -yRot * ((float)Math.PI / 180F);
      float f2 = Mth.m_14089_(f1);
      float f3 = Mth.m_14031_(f1);
      float f4 = Mth.m_14089_(f);
      float f5 = Mth.m_14031_(f);
      return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
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
