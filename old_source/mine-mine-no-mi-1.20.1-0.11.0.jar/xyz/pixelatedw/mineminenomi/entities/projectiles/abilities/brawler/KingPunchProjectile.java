package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.brawler;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KingPunchProjectile extends NuProjectileEntity {
   public KingPunchProjectile(EntityType<? extends KingPunchProjectile> type, Level world) {
      super(type, world);
   }

   public KingPunchProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.KING_PUNCH.get(), world, player, ability);
      super.setEntityCollisionSize((double)4.0F);
      super.setMaxLife(30);
      super.setPassThroughEntities();
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)1.25F;
            double offsetY = WyHelper.randomDouble() / (double)1.25F;
            double offsetZ = WyHelper.randomDouble() / (double)1.25F;
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123759_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, -0.1);
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123812_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.1);
         }
      }

   }
}
