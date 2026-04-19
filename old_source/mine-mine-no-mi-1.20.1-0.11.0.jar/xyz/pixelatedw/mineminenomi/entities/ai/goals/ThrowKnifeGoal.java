package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;

public class ThrowKnifeGoal extends TickedGoal<Mob> {
   private LivingEntity target;
   private final int cooldown;
   private int amount;
   private float speed;
   private double spread;

   public ThrowKnifeGoal(Mob entity) {
      this(entity, 200);
   }

   public ThrowKnifeGoal(Mob entity, int cooldown) {
      super(entity);
      this.amount = 1;
      this.speed = 1.0F;
      this.spread = (double)5.0F;
      this.cooldown = cooldown;
   }

   public ThrowKnifeGoal setSpread(double spread) {
      this.spread = spread;
      return this;
   }

   public ThrowKnifeGoal setSpeed(float speed) {
      this.speed = speed;
      return this;
   }

   public ThrowKnifeGoal setAmount(int amount) {
      this.amount = amount;
      return this;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd((float)this.cooldown)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (GoalHelper.isWithinDistance(this.entity, this.target, (double)6.0F)) {
            return false;
         } else {
            return this.entity.m_142582_(this.entity.m_5448_());
         }
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      LivingEntity target = this.entity.m_5448_();
      if (target != null) {
         float chanceForFull = 0.6F;
         if (this.entity.m_20270_(target) < 10.0F) {
            double distance = this.entity.m_9236_().m_46791_() == Difficulty.HARD ? (double)15.0F : (double)10.0F;
            Vec3 dir = this.entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_(distance, (double)0.0F, distance).m_82520_((double)0.0F, this.entity.m_20184_().f_82480_, (double)0.0F);
            this.entity.m_6478_(MoverType.SELF, dir);
         }

         if (this.entity.m_9236_().m_46791_() == Difficulty.HARD) {
            this.speed += 0.5F;
            chanceForFull = 0.2F;
         }

         int finalAmount = 1;
         if (this.entity.m_217043_().m_188501_() > chanceForFull) {
            finalAmount = this.amount;
         }

         int min = finalAmount / 2;
         int max = finalAmount / 2;
         float x = (float)(target.m_20185_() - this.entity.m_20185_());
         float z = (float)(target.m_20189_() - this.entity.m_20189_());
         float angle = (float)Math.toDegrees(Math.atan2((double)x, (double)z));

         for(int i = -min; i <= max; ++i) {
            NuProjectileEntity projectile = new ThrowingWeaponEntity(this.entity.m_9236_(), this.entity);
            projectile.m_6034_(projectile.m_20185_(), this.entity.m_20186_() + (double)(this.entity.m_20206_() / 2.0F) + (double)1.0F, projectile.m_20189_());
            projectile.m_37251_(this.entity, this.entity.m_146909_() - 7.0F, (float)((double)(-angle) + (double)i * this.spread), 0.0F, this.speed, 0.0F);
            projectile.m_5602_(this.entity);
            this.entity.m_9236_().m_7967_(projectile);
         }

      }
   }
}
