package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;

public class FishmanPassiveBonusesAbility extends PerkAbility {

    private static final ResourceLocation SWIM_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_swim_speed");

    public FishmanPassiveBonusesAbility() {
        super("Fishman Passive Bonuses", "Provides extra swim speed and underwater abilities for Fishmen");
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        if (entity.getAttribute(NeoForgeMod.SWIM_SPEED) != null && entity.getAttribute(NeoForgeMod.SWIM_SPEED).getModifier(SWIM_SPEED_MODIFIER_ID) == null) {
            entity.getAttribute(NeoForgeMod.SWIM_SPEED).addTransientModifier(new AttributeModifier(SWIM_SPEED_MODIFIER_ID, 1.5, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
