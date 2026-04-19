package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HydraProjectile extends NuHorizontalLightningEntity {
   private boolean isDemonMode = false;

   public HydraProjectile(EntityType<? extends HydraProjectile> type, Level world) {
      super(type, world);
   }

   public HydraProjectile(Level world, LivingEntity player, IAbility ability, boolean isDemonMode) {
      super((EntityType)ModProjectiles.HYDRA.get(), player, 64.0F, isDemonMode ? 10.0F : 8.0F, ability);
      this.setDamage(isDemonMode ? 30.0F : 20.0F);
      this.setEntityCollisionSize((double)1.0F);
      this.setMaxLife(isDemonMode ? 40 : 30);
      this.setSize(0.1F);
      this.setSegments(1);
      this.setPassThroughEntities();
      this.isDemonMode = isDemonMode;
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
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
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.isDemonMode = buffer.readBoolean();
   }

   public boolean isDemonMode() {
      return this.isDemonMode;
   }
}
