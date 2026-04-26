package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;

public class CyborgPassiveBonusesAbility extends PerkAbility {
    private static final ResourceLocation ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor_bonus");
    private static final ResourceLocation ARMOR_TOUGHNESS_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor_toughness_bonus");

    public CyborgPassiveBonusesAbility() {
        super("Cyborg Passive Bonuses", "Provides extra armor, toughness and damage for Cyborgs");
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        if (entity.getAttribute(Attributes.ARMOR) != null && entity.getAttribute(Attributes.ARMOR).getModifier(ARMOR_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, 10.0, AttributeModifier.Operation.ADD_VALUE));
        }

        if (entity.getAttribute(Attributes.ARMOR_TOUGHNESS) != null && entity.getAttribute(Attributes.ARMOR_TOUGHNESS).getModifier(ARMOR_TOUGHNESS_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_ID, 4.0, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
