package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IWithHome;

public class RandomWalkAroundHomeGoal<E extends PathfinderMob & IWithHome> extends RandomStrollGoal {
   protected E mob;
   protected final float probability;
   private int distance;
   private int offset;

   public RandomWalkAroundHomeGoal(E mob, double speed) {
      this(mob, speed, 0.001F);
      this.mob = mob;
   }

   public RandomWalkAroundHomeGoal(E mob, double speed, float chance) {
      super(mob, speed);
      this.distance = 10;
      this.offset = 7;
      this.mob = mob;
      this.probability = chance;
   }

   public RandomWalkAroundHomeGoal<E> setWalkDistance(int distance) {
      this.distance = distance;
      return this;
   }

   public RandomWalkAroundHomeGoal<E> setWalkOffset(int offset) {
      this.offset = offset;
      return this;
   }

   @Nullable
   protected Vec3 m_7037_() {
      if (!((IWithHome)this.mob).getHomePosition().isPresent()) {
         return super.m_7037_();
      } else {
         Vec3 homePosition = (Vec3)((IWithHome)this.mob).getHomePosition().get();
         if (this.mob.m_20072_()) {
            Vec3 vector3d = WyHelper.findValidGroundLocation(this.mob.m_9236_(), homePosition, this.distance * 2, this.offset);
            return vector3d == null ? super.m_7037_() : vector3d;
         } else {
            return this.mob.m_217043_().m_188501_() >= this.probability ? WyHelper.findValidGroundLocation(this.mob.m_9236_(), homePosition, this.distance, this.offset) : super.m_7037_();
         }
      }
   }
}
