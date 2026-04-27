package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.UUID;
import java.util.function.Predicate;

public class FishmanPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier SWIM_SPEED_MODIFIER;
   private static final Predicate<LivingEntity> FISHMAN_CHECK;

   public FishmanPassiveBonusesAbility() {
      super();
      this.pushStaticAttribute(NeoForgeMod.SWIM_SPEED, SWIM_SPEED_MODIFIER);
   }

   // The active effect granting needs to be handled externally since addDuringPassiveEvent doesn't exist yet,
   // or implemented if we update the base class. We will add a tick() method to PassiveAbility.

   @Override
   public void tick(LivingEntity entity) {
      if (FISHMAN_CHECK.test(entity) && entity.isEyeInFluid(FluidTags.WATER)) {
         entity.setAirSupply(300);
         entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 250, 0, false, false));
         entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100, 0, false, false));
      }
   }

   public Predicate<LivingEntity> getCheck() {
      return FISHMAN_CHECK;
   }

   static {
      SWIM_SPEED_MODIFIER = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_swim_speed"), 3.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
      FISHMAN_CHECK = (entity) -> {
         boolean hasAnyFruit = entity.hasData(ModDataAttachments.PLAYER_STATS) && entity.getData(ModDataAttachments.PLAYER_STATS).hasDevilFruit();
         return !entity.isInWater() || !hasAnyFruit || entity.hasEffect(ModEffects.BUBBLY_CORAL);
      };
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      if (entity.hasData(ModDataAttachments.PLAYER_STATS)) {
         return entity.getData(ModDataAttachments.PLAYER_STATS).isFishman() ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
      }
      return xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Fishman Passive Bonuses");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_passive_bonuses");
   }
}
