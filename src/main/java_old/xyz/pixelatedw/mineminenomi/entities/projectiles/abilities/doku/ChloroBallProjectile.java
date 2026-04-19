package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.doku.DokuHelper;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.clouds.ChloroBallCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChloroBallProjectile extends NuProjectileEntity {
   private boolean isDemonMode = false;
   private int spread = 4;

   public ChloroBallProjectile(EntityType<? extends ChloroBallProjectile> type, Level world) {
      super(type, world);
   }

   public ChloroBallProjectile(Level world, LivingEntity player, IAbility ability, boolean isDemonForm) {
      super((EntityType)ModProjectiles.CHLORO_BALL.get(), world, player, ability);
      this.setDamage(isDemonForm ? 12.0F : 7.0F);
      this.isDemonMode = isDemonForm;
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), 300, this.getPoisonAmplifier()));
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      int fails = 0;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      int i = 0;

      while(i < this.getPoisonBlockAmount() && fails <= 100) {
         double offsetX = WyHelper.randomWithRange(-this.spread, this.spread);
         double offsetY = WyHelper.randomWithRange(-2, 2);
         double offsetZ = WyHelper.randomWithRange(-this.spread, this.spread);
         mutpos.m_122169_(this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         if (!this.m_9236_().m_8055_(mutpos.m_7495_()).m_60795_() && NuWorld.setBlockState((Entity)this.getOwner(), mutpos, this.getPoisonBlock().m_49966_(), 3, DefaultProtectionRules.AIR_FOLIAGE)) {
            ++i;
         } else {
            ++fails;
         }
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHLORO_BALL.get(), this, this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_());
      ChloroBallCloudEntity cloud = new ChloroBallCloudEntity(this.m_9236_(), this);
      cloud.setLife(30);
      cloud.m_7678_(this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_(), 0.0F, 0.0F);
      AbilityHelper.setDeltaMovement(cloud, (double)0.0F, (double)0.0F, (double)0.0F);
      this.m_9236_().m_7967_(cloud);
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
            if (this.isDemonMode) {
               data.setColor(1.0F, 0.0F, 0.0F);
            }

            data.setLife(5);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }

   public ParticleEffect.Details getPoisonParticles() {
      return (ParticleEffect.Details)(this.isDemonMode ? DokuHelper.VENOM_DETAILS : ParticleEffect.EMPTY_DETAILS);
   }

   public int getPoisonAmplifier() {
      return this.isDemonMode ? 4 : 0;
   }

   public int getPoisonBlockAmount() {
      return this.isDemonMode ? 40 : 20;
   }

   public Block getPoisonBlock() {
      return this.isDemonMode ? (Block)ModBlocks.DEMON_POISON.get() : (Block)ModBlocks.POISON.get();
   }

   public int getPoisonRange() {
      return this.isDemonMode ? 5 : 8;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeBoolean(this.isDemonMode);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.isDemonMode = buffer.readBoolean();
   }

   public boolean isDemonMode() {
      return this.isDemonMode;
   }
}
