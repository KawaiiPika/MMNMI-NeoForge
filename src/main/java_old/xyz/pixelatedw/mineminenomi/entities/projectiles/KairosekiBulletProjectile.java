package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KairosekiBulletProjectile extends NuProjectileEntity {
   public KairosekiBulletProjectile(EntityType<? extends KairosekiBulletProjectile> type, Level world) {
      super(type, world);
   }

   public KairosekiBulletProjectile(Level world, LivingEntity player) {
      super((EntityType)ModProjectiles.KAIROSEKI_BULLET.get(), world, player, (IAbility)null, SourceElement.NONE, SourceHakiNature.IMBUING, SourceType.PROJECTILE, SourceType.PHYSICAL, SourceType.BLUNT);
      this.setDamage(8.0F);
      this.addEntityHitEvent(100, this::entityHitEvent);
   }

   private void entityHitEvent(EntityHitResult target) {
      Entity var3 = target.m_82443_();
      if (var3 instanceof LivingEntity livingTarget) {
         AbilityHelper.disableAbilities(livingTarget, 20, AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
      }

   }
}
