package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoPistolProjectile extends NuHorizontalLightningEntity {
   public GomuGomuNoPistolProjectile(EntityType<? extends GomuGomuNoPistolProjectile> type, Level world) {
      super(type, world);
   }

   public GomuGomuNoPistolProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_PISTOL.get(), thrower, 80.0F, 8.0F, ability);
      this.setRetracting();
      this.setSize(0.025F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setPassThroughEntities(false);
      this.setDamage(6.0F);
      this.setFist();
   }
}
