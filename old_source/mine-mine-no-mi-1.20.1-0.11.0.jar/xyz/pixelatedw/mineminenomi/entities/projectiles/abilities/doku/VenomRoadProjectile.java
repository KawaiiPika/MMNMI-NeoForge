package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class VenomRoadProjectile extends NuHorizontalLightningEntity {
   private boolean isDemonMode = false;
   private Vec3 startPos;

   public VenomRoadProjectile(EntityType<? extends VenomRoadProjectile> type, Level world) {
      super(type, world);
   }

   public VenomRoadProjectile(Level world, LivingEntity player, IAbility ability, boolean isDemonMode) {
      super((EntityType)ModProjectiles.VENOM_ROAD.get(), player, 64.0F, isDemonMode ? 10.0F : 8.0F, ability);
      this.setDamage(isDemonMode ? 15.0F : 8.0F);
      this.f_19811_ = true;
      this.setMaxLife(isDemonMode ? 40 : 30);
      this.setSize(0.1F);
      this.setSegments(1);
      this.setFadeTime(-1);
      this.setColor(255, 255, 255, 100);
      this.setPassThroughEntities();
      this.isDemonMode = isDemonMode;
      this.startPos = player.m_20182_();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      LivingEntity thrower = this.getOwner();
      if (thrower != null && !AbilityUseConditions.canUseMomentumAbilities(thrower).isFail()) {
         BlockPos pos = result.m_82425_();
         BlockState state = thrower.m_9236_().m_8055_(pos);
         if (!state.m_60795_()) {
            if (thrower.m_20202_() != null) {
               thrower.m_8127_();
            }

            thrower.m_8127_();
            thrower.m_20324_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
            thrower.f_19789_ = 0.0F;
            this.getParent().ifPresent((abl) -> abl.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(thrower, 80.0F)));
         }
      }
   }

   public void m_8119_() {
      if (this.getLife() <= 0 && this.getLife() > -1200) {
         this.setLife(this.getLife() - 1);
         AbilityHelper.setDeltaMovement(this, (double)0.0F, (double)0.0F, (double)0.0F);
      } else {
         super.m_8119_();
      }
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), 400, this.isDemonMode ? 3 : 1));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 400, this.isDemonMode ? 2 : 1));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 400, this.isDemonMode ? 2 : 1));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 400, this.isDemonMode ? 2 : 1));
      }

   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeBoolean(this.isDemonMode);
      buffer.writeDouble(this.startPos != null ? this.startPos.f_82479_ : (double)0.0F);
      buffer.writeDouble(this.startPos != null ? this.startPos.f_82480_ : (double)0.0F);
      buffer.writeDouble(this.startPos != null ? this.startPos.f_82481_ : (double)0.0F);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.isDemonMode = buffer.readBoolean();
      this.startPos = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
   }

   public Vec3 getStartPos() {
      return this.startPos;
   }

   public boolean isDemonMode() {
      return this.isDemonMode;
   }
}
