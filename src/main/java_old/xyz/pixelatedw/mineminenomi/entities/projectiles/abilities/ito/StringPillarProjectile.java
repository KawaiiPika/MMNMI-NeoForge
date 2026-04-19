package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class StringPillarProjectile extends NuProjectileEntity {
   public StringPillarProjectile(EntityType<? extends StringPillarProjectile> type, Level world) {
      super(type, world);
   }

   public StringPillarProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.STRING_PILLAR.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setPassThroughEntities();
      this.setArmorPiercing(0.25F);
      this.setEntityCollisionSize((double)0.75F, (double)5.0F, (double)0.75F);
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_146926_(90.0F);
   }

   public void onProjectileCollision(NuProjectileEntity owner, NuProjectileEntity target) {
      if (!(target instanceof StringPillarProjectile)) {
         super.onProjectileCollision(owner, target);
      }
   }
}
