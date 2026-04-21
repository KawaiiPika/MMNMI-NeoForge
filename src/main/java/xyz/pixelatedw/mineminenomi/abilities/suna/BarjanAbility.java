package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Barjan — Crocodile's hook-arm blade slam that creates a large sand shockwave. */
public class BarjanAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");
    public BarjanAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Forward slam + shockwave
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4.0).move(look.scale(4.0)))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 12.0F);
                living.setDeltaMovement(look.scale(2.5).add(0, 0.5, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barjan"); }
}
