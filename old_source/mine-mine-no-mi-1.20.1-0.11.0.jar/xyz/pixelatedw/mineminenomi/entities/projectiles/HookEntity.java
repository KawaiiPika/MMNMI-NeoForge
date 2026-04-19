package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class HookEntity extends NuProjectileEntity {
   private IDevilFruit props;

   public HookEntity(EntityType<? extends HookEntity> type, Level world) {
      super(type, world);
   }

   public HookEntity(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.HOOK.get(), world, thrower, ability);
      this.setMaxLife(50);
      this.setEntityCollisionSize((double)1.0F);
      this.props = (IDevilFruit)DevilFruitCapability.get(thrower).orElse((Object)null);
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData part = null;
            if (this.props.hasDevilFruit(ModFruits.SUNA_SUNA_NO_MI)) {
               part = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
               part.setSize(1.5F);
            } else if (this.props.hasDevilFruit(ModFruits.MOKU_MOKU_NO_MI)) {
               part = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
               part.setSize(1.3F);
            }

            if (part != null) {
               part.setLife(20);
               part.setRotation(0.0F, 0.0F, 1.0F);
               WyHelper.spawnParticles(part, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
            }
         }
      }

   }
}
