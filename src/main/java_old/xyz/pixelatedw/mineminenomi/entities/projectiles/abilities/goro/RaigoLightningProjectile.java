package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class RaigoLightningProjectile extends NuLightningEntity {
   public RaigoLightningProjectile(EntityType<? extends RaigoLightningProjectile> type, Level world) {
      super(type, world);
   }

   public RaigoLightningProjectile(Level world, LivingEntity thrower, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float maxTravelDistance, IAbility ability) {
      super((EntityType)ModProjectiles.RAIGO_LIGHTNING.get(), thrower, posX, posY, posZ, rotationYaw, rotationPitch, maxTravelDistance, maxTravelDistance, ability);
      this.setAngle(20);
      this.setMaxLife(20);
      this.setDamage(0.0F);
      this.setSize(maxTravelDistance / 800.0F);
      this.setColor(ClientConfig.isGoroBlue() ? ElThorAbility.BLUE_THUNDER : ElThorAbility.YELLOW_THUNDER);
      this.setBranches(3);
      this.setSegments(10);
   }
}
