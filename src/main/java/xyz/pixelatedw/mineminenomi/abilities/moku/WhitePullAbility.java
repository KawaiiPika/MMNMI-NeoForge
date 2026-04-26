package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WhitePullAbility extends Ability {

    private static final int MAX_DURATION = 100;
    private static final double RANGE = 20.0; // Optimized range from 100 to 20 for standard queries

    public WhitePullAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Starts the continuity
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE)).forEach(target -> {
                if (target != entity && target.hasEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN)) {
                    Vec3 pull = entity.position().subtract(target.position()).normalize().scale(0.5);
                    target.setDeltaMovement(pull.x, pull.y, pull.z);
                    target.hurtMarked = true;
                }
            });

            if (duration >= MAX_DURATION) {
                this.stop(entity);
                this.startCooldown(entity, 200);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_pull");
    }
}
