package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DesertGrandeEspadaProjectile extends NuProjectileEntity {
   public DesertGrandeEspadaProjectile(EntityType<? extends DesertGrandeEspadaProjectile> type, Level world) {
      super(type, world);
   }

   public DesertGrandeEspadaProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.DESERT_GRANDE_ESPADA.get(), world, entity, ability);
      this.setDamage(60.0F);
      this.setMaxLife(8);
      this.setPassThroughBlocks();
      this.setPassThroughEntities();
      this.setEntityCollisionSize((double)1.75F, (double)5.0F, (double)1.75F);
   }
}
