package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.function.Predicate;

public class CyborgPassiveBonusesAbility extends PassiveStatBonusAbility {
   private static final AttributeModifier CYBORG_ARMOR;
   private static final AttributeModifier CYBORG_ARMOR_TOUGHNESS;
   private static final AttributeModifier CYBORG_DAMAGE;
   private static final Predicate<LivingEntity> CYBORG_CHECK;

   public CyborgPassiveBonusesAbility() {
      super();
      this.pushStaticAttribute(Attributes.ARMOR, CYBORG_ARMOR);
      this.pushStaticAttribute(Attributes.ARMOR_TOUGHNESS, CYBORG_ARMOR_TOUGHNESS);
      this.pushStaticAttribute(ModAttributes.PUNCH_DAMAGE, CYBORG_DAMAGE);
   }

   public Predicate<LivingEntity> getCheck() {
      return CYBORG_CHECK;
   }

   static {
      CYBORG_ARMOR = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor_bonus"), 10.0, AttributeModifier.Operation.ADD_VALUE);
      CYBORG_ARMOR_TOUGHNESS = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor_toughness_bonus"), 4.0, AttributeModifier.Operation.ADD_VALUE);
      CYBORG_DAMAGE = new AttributeModifier(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_damage_bonus"), 2.0, AttributeModifier.Operation.ADD_VALUE);
      CYBORG_CHECK = (entity) -> entity.hasData(ModDataAttachments.PLAYER_STATS) && entity.getData(ModDataAttachments.PLAYER_STATS).isCyborg();
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      return CYBORG_CHECK.test(entity) ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Cyborg Passive Bonuses");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_passive_bonuses");
   }
}
