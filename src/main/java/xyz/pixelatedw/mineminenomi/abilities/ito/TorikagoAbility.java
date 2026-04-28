package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Torikago — Birdcage; creates a massive inescapable string cage.
 * Modified for 1.21.1 thread safety: Uses enqueueWork to handle bulk block updates over time if necessary,
 * or simply triggers block replacement directly on the main thread safely.
 */
public class TorikagoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    private static final int MAX_RADIUS = 30;

    // Store active cages for later removal (simplified tracker)
    private java.util.UUID ownerUUID = null;
    private BlockPos centerPos = null;

    public TorikagoAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            this.ownerUUID = entity.getUUID();
            this.centerPos = entity.blockPosition();

            // Thread-safe large block placement over time could go here via a scheduled task.
            // For now we will apply a massive AoE slow and damage to represent the cage in this modernized port.
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(MAX_RADIUS)).forEach(target -> {
                if (target != entity) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 600, 3));
                    target.hurt(entity.damageSources().mobAttack(entity), 5.0F);
                }
            });

            // Build the string wall block structure around the player.
            // Because we are already on the Main Thread (abilities execute synchronously), we can modify blocks.
            // We use a hollow sphere algorithm.
            int radiusSq = MAX_RADIUS * MAX_RADIUS;
            int innerRadiusSq = (MAX_RADIUS - 1) * (MAX_RADIUS - 1);
            BlockPos.betweenClosedStream(this.centerPos.offset(-MAX_RADIUS, -MAX_RADIUS, -MAX_RADIUS),
                                         this.centerPos.offset(MAX_RADIUS, MAX_RADIUS, MAX_RADIUS))
                .forEach(pos -> {
                    double distSq = pos.distSqr(this.centerPos);
                    if (distSq <= radiusSq && distSq > innerRadiusSq) {
                        if (entity.level().getBlockState(pos).isAir() || entity.level().getBlockState(pos).canBeReplaced()) {
                            // Using standard cobweb as placeholder until StringWall block is ported
                            entity.level().setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.COBWEB.defaultBlockState());
                        }
                    }
                });

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.WOOL_PLACE,
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);

            this.startCooldown(entity, 1200);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.torikago"); }
}
