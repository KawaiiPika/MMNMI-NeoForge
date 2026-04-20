package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Nightmare Soldiers — user releases a swarm of shadow soldiers.
 * Deals damage and knocks back all nearby enemies in multiple directions.
 */
public class NightmareSoldiersAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    public NightmareSoldiersAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(6.0))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                Vec3 push = living.position().subtract(entity.position()).normalize().scale(3.0).add(0, 0.5, 0);
                living.setDeltaMovement(push);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.nightmare_soldiers"); }
}
