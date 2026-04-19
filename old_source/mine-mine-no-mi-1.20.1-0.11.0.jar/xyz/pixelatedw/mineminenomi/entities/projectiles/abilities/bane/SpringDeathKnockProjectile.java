package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class SpringDeathKnockProjectile extends NuHorizontalLightningEntity {
   public SpringDeathKnockProjectile(EntityType<? extends SpringDeathKnockProjectile> type, Level world) {
      super(type, world);
   }

   public SpringDeathKnockProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.SPRING_DEATH_KNOCK.get(), entity, 15.0F, 2.0F, ability);
      this.setRetracting();
      this.setSize(0.05F);
      this.setColor(168, 168, 168, 255);
      this.setOneTimeHit();
      this.setDamage(20.0F);
      this.setFist();
   }
}
