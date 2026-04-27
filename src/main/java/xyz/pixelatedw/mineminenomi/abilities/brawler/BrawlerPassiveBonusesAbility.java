package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.UUID;
import java.util.function.Predicate;

public class BrawlerPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier BRAWLER_ATTACK_SPEED_MODIFIER;
   private static final Predicate<LivingEntity> BRAWLER_CHECK;

   public BrawlerPassiveBonusesAbility() {
      super();
      this.pushDynamicAttribute(ModAttributes.PUNCH_DAMAGE, this::getModifier);
      this.pushStaticAttribute(Attributes.ATTACK_SPEED, BRAWLER_ATTACK_SPEED_MODIFIER);
   }

   private AttributeModifier getModifier(LivingEntity entity) {
      double bonus = this.getBonus(entity);
      return new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_punch_bonus"), bonus, AttributeModifier.Operation.ADD_VALUE);
   }

   private double getBonus(LivingEntity entity) {
      double doriki = 0.0;
      if (entity.hasData(ModDataAttachments.PLAYER_STATS)) {
         doriki = entity.getData(ModDataAttachments.PLAYER_STATS).getDoriki();
      }
      return 2.0 + doriki * 5.0E-4;
   }

   public Predicate<LivingEntity> getCheck() {
      return BRAWLER_CHECK;
   }

   static {
      BRAWLER_ATTACK_SPEED_MODIFIER = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_speed_multiplier"), -0.5, AttributeModifier.Operation.ADD_VALUE);
      BRAWLER_CHECK = (entity) -> {
         ItemStack heldItem = entity.getMainHandItem();
         return heldItem.isEmpty();
      };
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      if (entity.hasData(ModDataAttachments.PLAYER_STATS)) {
         return entity.getData(ModDataAttachments.PLAYER_STATS).isBrawler() ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
      }
      return xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Brawler Passive Bonuses");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_passive_bonuses");
   }
}
