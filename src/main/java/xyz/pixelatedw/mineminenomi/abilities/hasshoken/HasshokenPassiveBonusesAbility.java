package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class HasshokenPassiveBonusesAbility extends PerkAbility {

    private static final ResourceLocation ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hasshoken_armor");
    private static final ResourceLocation ARMOR_TOUGHNESS_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hasshoken_armor_toughness");

    public HasshokenPassiveBonusesAbility() {
        super("Hasshoken Passive Bonuses", "Provides extra armor, toughness, and unarmed damage");
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        ItemStack heldItem = entity.getMainHandItem();
        boolean isEmptyHanded = heldItem.isEmpty();

        if (isEmptyHanded) {
            if (entity.getAttribute(Attributes.ARMOR) != null && entity.getAttribute(Attributes.ARMOR).getModifier(ARMOR_MODIFIER_ID) == null) {
                entity.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, 2.0, AttributeModifier.Operation.ADD_VALUE));
            }
            if (entity.getAttribute(Attributes.ARMOR_TOUGHNESS) != null && entity.getAttribute(Attributes.ARMOR_TOUGHNESS).getModifier(ARMOR_TOUGHNESS_MODIFIER_ID) == null) {
                entity.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (entity.getAttribute(Attributes.ARMOR) != null) {
                entity.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_ID);
            }
            if (entity.getAttribute(Attributes.ARMOR_TOUGHNESS) != null) {
                entity.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS_MODIFIER_ID);
            }
        }
    }
}
