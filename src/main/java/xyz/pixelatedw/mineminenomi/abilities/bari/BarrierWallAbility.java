package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Barrier Wall — creates an invisible barrier wall in front of the user.
 * Enemies that walk into the barrier take damage and are knocked back.
 * Currently implemented as a burst knockback on activation.
 */
public class BarrierWallAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BarrierWallAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Wall deployed in front — push away all nearby frontal entities
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(2.0)))) {
            if (target instanceof LivingEntity living) {
                Vec3 push = look.scale(3.5).add(0, 0.5, 0);
                living.setDeltaMovement(push);
                living.hurt(entity.damageSources().mobAttack(entity), 4.0F);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barrier_wall"); }
}
