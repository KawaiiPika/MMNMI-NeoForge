package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Empty Hands — baseline brawler passive. 
 * Always available when no weapon is equipped. 
 * Category: Passives (Cat 1)
 */
public class EmptyHandsAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             this.startCooldown(entity, 20);
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount + 4.0F;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.empty_hands");
    }
}
