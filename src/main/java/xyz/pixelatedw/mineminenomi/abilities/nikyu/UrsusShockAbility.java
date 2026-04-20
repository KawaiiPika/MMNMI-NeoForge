package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Ursus Shock — Kuma's signature attack; extracts all fatigue/pain from entity into
 * a compressed sphere and detonates it. Deals massive damage to all nearby.
 */
public class UrsusShockAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    public UrsusShockAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Giant compressed-pain detonation — massive AoE
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(10.0))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 20.0F);
                var dir = living.position().subtract(entity.position()).normalize();
                living.setDeltaMovement(dir.scale(5.0).add(0, 1.0, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ursus_shock"); }
}
