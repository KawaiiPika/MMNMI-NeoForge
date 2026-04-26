package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BrawlerPassiveBonusesAbility extends PerkAbility {

    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_attack_speed");
    // TODO Add Punch Damage modifier when ported

    public BrawlerPassiveBonusesAbility() {
        super("Brawler Passive Bonuses", "Provides extra punch damage and attack speed when fighting empty-handed");
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
            if (entity.getAttribute(Attributes.ATTACK_SPEED) != null && entity.getAttribute(Attributes.ATTACK_SPEED).getModifier(ATTACK_SPEED_MODIFIER_ID) == null) {
                // The old code added -0.5 attack speed... Brawler attack speed bonus/penalty
                entity.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, -0.5, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (entity.getAttribute(Attributes.ATTACK_SPEED) != null) {
                entity.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_MODIFIER_ID);
            }
        }
    }
}
