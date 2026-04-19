package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalLunaProjectile extends NuProjectileEntity {
   private ElectricalLunaAbility ability;

   public ElectricalLunaProjectile(EntityType<? extends ElectricalLunaProjectile> type, Level world) {
      super(type, world);
   }

   public ElectricalLunaProjectile(Level world, LivingEntity player, ElectricalLunaAbility ability) {
      super((EntityType)ModProjectiles.ELECTRICAL_LUNA.get(), world, player, ability);
      this.ability = ability;
      this.setMaxLife(20);
      this.setPassThroughEntities();
      this.addTickEvent(100, this::onTickEvent);
      this.addBlockHitEvent(100, this::onBlockHitEvent);
   }

   private void onBlockHitEvent(BlockHitResult result) {
      if (this.getOwner() != null && !this.getParent().isEmpty()) {
         this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.RANGE.get()).ifPresent((comp) -> {
            int range = 6;

            for(LivingEntity target : comp.getTargetsInArea(this.getOwner(), (float)range)) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 30, 0));
               int amount = 16;

               for(int j = 0; j < amount; ++j) {
                  float boltSize = (float)WyHelper.randomWithRange(3, range);
                  ElectroVisualProjectile bolt = new ElectroVisualProjectile(this.m_9236_(), this.getOwner(), (IAbility)this.getParent().get(), boltSize);
                  this.m_9236_().m_7967_(bolt);
               }
            }

         });
      }
   }

   private void onTickEvent() {
      if (this.f_19797_ >= 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRICAL_LUNA.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }
   }
}
