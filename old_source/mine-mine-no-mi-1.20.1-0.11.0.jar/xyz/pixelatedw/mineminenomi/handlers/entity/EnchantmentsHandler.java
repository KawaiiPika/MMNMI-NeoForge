package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EnchantmentsHandler {
   public static void tryFlashDialEffect(LivingEntity entity, LivingEntity target) {
      ItemStack heldItem = entity.m_21205_();
      int flashDialLevel = heldItem.getEnchantmentLevel((Enchantment)ModEnchantments.DIAL_FLASH.get());
      if (flashDialLevel > 0) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 200 * flashDialLevel, flashDialLevel));
      }

   }

   public static void tryImpactDialEffect(LivingEntity entity, LivingEntity target) {
      ItemStack heldItem = entity.m_21205_();
      int impactDialLevel = heldItem.getEnchantmentLevel((Enchantment)ModEnchantments.DIAL_IMPACT.get());
      if (impactDialLevel > 0) {
         heldItem.m_41622_((int)(WyHelper.randomWithRange(1, 3) * (double)impactDialLevel), target, (itemUser) -> itemUser.m_21166_(EquipmentSlot.MAINHAND));
         CommonExplosionParticleEffect.Details details = new CommonExplosionParticleEffect.Details((double)impactDialLevel);
         AbilityExplosion explosion = new AbilityExplosion(entity, (IAbility)null, target.m_20185_(), target.m_20186_() + (double)1.0F, target.m_20189_(), (float)impactDialLevel);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), details);
         explosion.m_46061_();
      }

   }
}
