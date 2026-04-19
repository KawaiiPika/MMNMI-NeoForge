package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BlackBoxProjectile extends NuProjectileEntity {
   public BlackBoxProjectile(EntityType<BlackBoxProjectile> type, Level world) {
      super(type, world);
   }

   public BlackBoxProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.BLACK_BOX.get(), world, player, ability);
      this.setDamage(5.0F);
      this.setMaxLife(15);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         MobEffectInstance instance = new MobEffectInstance((MobEffect)ModEffects.BLACK_BOX.get(), 80, 0, false, false);
         target.m_7292_(instance);
         LivingEntity var5 = this.getOwner();
         if (var5 instanceof ServerPlayer player) {
            player.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(target.m_19879_(), instance));
         }
      }

   }
}
