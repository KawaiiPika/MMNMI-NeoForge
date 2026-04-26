package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;

public class MinkPassiveBonusesAbility extends PerkAbility {

    private static final ResourceLocation JUMP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_jump_bonus");
    private static final ResourceLocation FALL_RESISTANCE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_fall_resistance_bonus");

    public MinkPassiveBonusesAbility() {
        super("Mink Passive Bonuses", "Provides extra jump height and fall resistance for Minks");
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        if (entity.getAttribute(Attributes.JUMP_STRENGTH) != null && entity.getAttribute(Attributes.JUMP_STRENGTH).getModifier(JUMP_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.JUMP_STRENGTH).addTransientModifier(new AttributeModifier(JUMP_MODIFIER_ID, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }

        if (entity.getAttribute(Attributes.SAFE_FALL_DISTANCE) != null && entity.getAttribute(Attributes.SAFE_FALL_DISTANCE).getModifier(FALL_RESISTANCE_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.SAFE_FALL_DISTANCE).addTransientModifier(new AttributeModifier(FALL_RESISTANCE_MODIFIER_ID, 5.0, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
