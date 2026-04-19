package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Barrier Crash — user slams a large barrier forward.
 * Deals massive knockback and damage to anything in front.
 */
public class BarrierCrashAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BarrierCrashAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Frontal slam in a larger area than Pistol
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(4.0).move(look.scale(3.0)))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 15.0F);
                Vec3 knockback = look.scale(4.0).add(0, 0.5, 0);
                living.setDeltaMovement(knockback);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barrier_crash"); }
}
