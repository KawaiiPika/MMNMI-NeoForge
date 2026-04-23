package xyz.pixelatedw.mineminenomi.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ElectroAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.electro.start"));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.electro.stop"));
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        return amount;
    }

    @Override
    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, DamageSource source) {
        if (this.isUsing(entity)) {
            target.hurt(entity.damageSources().lightningBolt(), 4.0F);
            if (!entity.level().isClientSide) {
                // Future: add lightning particles or sound
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.electro");
    }
}
