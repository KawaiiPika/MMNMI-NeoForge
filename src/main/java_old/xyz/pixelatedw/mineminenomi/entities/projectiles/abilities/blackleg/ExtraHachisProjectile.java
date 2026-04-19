package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ExtraHachisProjectile extends NuProjectileEntity {
   public ExtraHachisProjectile(EntityType<? extends ExtraHachisProjectile> type, Level world) {
      super(type, world);
   }

   public ExtraHachisProjectile(Level world, LivingEntity player, IAbility abl) {
      super((EntityType)ModProjectiles.EXTRA_HACHIS.get(), world, player, (IAbility)null, SourceElement.NONE, SourceHakiNature.HARDENING, SourceType.FIST);
      this.setDamage(8.0F);
      this.setMaxLife(5);
      this.setEntityCollisionSize((double)1.0F);
   }
}
