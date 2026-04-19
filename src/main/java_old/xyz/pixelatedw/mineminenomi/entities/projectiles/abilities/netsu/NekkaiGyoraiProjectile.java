package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.netsu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.netsu.NetsuEnhancementAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class NekkaiGyoraiProjectile extends NuProjectileEntity {
   private int damage = 10;

   public NekkaiGyoraiProjectile(EntityType<? extends NekkaiGyoraiProjectile> type, Level world) {
      super(type, world);
   }

   public NekkaiGyoraiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.NEKKAI_GYORAI.get(), world, player, ability);
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      NetsuEnhancementAbility netsuAbility = (NetsuEnhancementAbility)abilityProps.getEquippedAbility((AbilityCore)NetsuEnhancementAbility.INSTANCE.get());
      if (netsuAbility != null && netsuAbility.isContinuous()) {
         this.damage += 5;
      }

      this.setDamage((float)this.damage);
      this.setMaxLife(30);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult hit) {
      hit.m_82443_().m_20254_(4);
   }

   private void blockHitEvent(BlockHitResult result) {
      BlockPos pos = result.m_82425_();
      NuWorld.setBlockState((Entity)this.getOwner(), pos.m_7918_(0, 1, 0), Blocks.f_50083_.m_49966_(), 2, DefaultProtectionRules.AIR);
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            ParticleType<SimpleParticleData> particle = (ParticleType)ModParticleTypes.NETSU.get();
            if (i % 3 == 0) {
               particle = (ParticleType)ModParticleTypes.NETSU2.get();
            }

            if (i % 7 == 0) {
               particle = (ParticleType)ModParticleTypes.MERA.get();
            }

            SimpleParticleData data = new SimpleParticleData(particle);
            data.setLife(10);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
