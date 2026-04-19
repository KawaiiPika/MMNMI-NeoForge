package xyz.pixelatedw.mineminenomi.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FishmanStrengthAbility extends Ability {

    private static final ResourceLocation STRENGTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_strength");

    @Override
    protected void startUsing(LivingEntity entity) {
        // Passives are "always on" if they are in the active abilities list
    }

    @Override
    public void tick(LivingEntity entity) {
        // Ensure the passive is always active for the race
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        if (entity.isInWater()) {
            if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null && entity.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(STRENGTH_MODIFIER_ID) == null) {
                entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(STRENGTH_MODIFIER_ID, 0.05, AttributeModifier.Operation.ADD_VALUE));
            }
            
            entity.setAirSupply(300);
            if (!entity.level().isClientSide) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.NIGHT_VISION, 250, 0, false, false));
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DIG_SPEED, 100, 0, false, false));
            }
        } else {
            if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(STRENGTH_MODIFIER_ID);
            }
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        return amount + 2.0F; // Flat damage boost for Fishmen
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fishman_strength");
    }
}
