package xyz.pixelatedw.mineminenomi.abilities.wara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Life Minus — Absalom's Wara Wara no Mi drains life force from an enemy.
 * Deals damage based on target's max health; user heals for half.
 */
public class LifeMinusAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "wara_wara_no_mi");
    public LifeMinusAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        var look = entity.getLookAngle();
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0)))) {
            if (target instanceof LivingEntity living) {
                float drain = living.getMaxHealth() * 0.3f;
                living.hurt(entity.damageSources().mobAttack(entity), drain);
                entity.heal(drain * 0.5f);
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.life_minus"); }
}
