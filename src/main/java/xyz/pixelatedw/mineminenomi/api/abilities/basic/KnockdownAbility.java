package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Knockdown — basic brawler passive, knocks down enemy on hit.
 * Category: Passives (Cat 1)
 */
public class KnockdownAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             this.startCooldown(entity, 100);
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount + 6.0F;
    }

    @Override
    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source) {
        if (!entity.level().isClientSide) {
            target.setDeltaMovement(entity.getLookAngle().x * 1.5, 0.4, entity.getLookAngle().z * 1.5);
            target.hurtMarked = true;
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.knockdown");
    }
}
