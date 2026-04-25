package xyz.pixelatedw.mineminenomi.abilities.kobu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ShoureiAbility extends Ability {

    public ShoureiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kobu_kobu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(20.0))) {
                if (target instanceof LivingEntity livingTarget) {
                    // Ideally we should check if they are in a friendly faction
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1, true, false, true));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1, true, false, true));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1, true, false, true));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 0, true, false, true));
                }
            }
            this.startCooldown(entity, 1200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.shourei");
    }
}
