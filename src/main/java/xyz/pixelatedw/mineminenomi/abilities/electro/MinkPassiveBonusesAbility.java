package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;
import xyz.pixelatedw.mineminenomi.init.ModTags;

import java.util.function.Predicate;

public class MinkPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier SPEED_MODIFIER;
   private static final AttributeModifier JUMP_MODIFIER;
   private static final AttributeModifier JUMP_RESISTANCE_MODIFIER;
   private static final Predicate<LivingEntity> TEMPERATURE_CHECK;

   public MinkPassiveBonusesAbility() {
      super();
      this.pushStaticAttribute(Attributes.MOVEMENT_SPEED, SPEED_MODIFIER);
      this.pushStaticAttribute(ModAttributes.JUMP_HEIGHT, JUMP_MODIFIER);
      this.pushStaticAttribute(ModAttributes.FALL_RESISTANCE, JUMP_RESISTANCE_MODIFIER);
   }

   public Predicate<LivingEntity> getCheck() {
      return TEMPERATURE_CHECK;
   }

   static {
      SPEED_MODIFIER = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_speed_multiplier"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
      JUMP_MODIFIER = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_jump_boost_addition"), 1.6, AttributeModifier.Operation.ADD_VALUE);
      JUMP_RESISTANCE_MODIFIER = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_jump_resistance"), 2.25, AttributeModifier.Operation.ADD_VALUE);
      TEMPERATURE_CHECK = (entity) -> {
         BlockPos position = entity.blockPosition();
         return entity.level().getBiome(position).is(ModTags.Biomes.MINK_DEBUFF_BIOMES);
      };
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      if (entity.hasData(ModDataAttachments.PLAYER_STATS)) {
         return entity.getData(ModDataAttachments.PLAYER_STATS).isMink() ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
      }
      return xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Mink Passive Bonuses");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_passive_bonuses");
   }
}
