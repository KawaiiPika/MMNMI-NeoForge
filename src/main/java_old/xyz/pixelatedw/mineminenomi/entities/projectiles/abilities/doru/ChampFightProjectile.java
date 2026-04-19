package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ChampFightProjectile extends NuProjectileEntity {
   public ChampFightProjectile(EntityType<? extends ChampFightProjectile> type, Level world) {
      super(type, world);
   }

   public ChampFightProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.CHAMP_FIGHT.get(), world, entity, ability);
      this.setDamage(10.0F);
      this.setMaxLife(20);
      this.setFist();
   }
}
