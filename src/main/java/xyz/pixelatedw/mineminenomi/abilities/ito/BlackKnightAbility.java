package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Black Knight — user creates a string clone that slashes nearby enemies.
 * Implemented as a high-damage burst around the user.
 */
public class BlackKnightAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    public BlackKnightAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Burst slash around the user
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(4.0))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 12.0F);
                Vec3 push = living.position().subtract(entity.position()).normalize().scale(2.0);
                living.setDeltaMovement(push);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.black_knight"); }
}
