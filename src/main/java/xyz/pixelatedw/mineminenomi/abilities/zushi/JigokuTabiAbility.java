package xyz.pixelatedw.mineminenomi.abilities.zushi;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class JigokuTabiAbility extends Ability {
    private static final int HOLD_TIME = 120;
    private static final int MIN_COOLDOWN = 100;
    private static final int RANGE = 24;
    private int force = 4;

    public JigokuTabiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.force = 4;
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration >= HOLD_TIME) {
                this.stop(entity);
                return;
            }

            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE), target -> target != entity && target.isAlive());

            boolean damageTick = duration % 20 == 0;
            boolean forceTick = duration > 0 && duration % 60 == 0;

            if (forceTick) {
                this.force++;
            }

            for (LivingEntity target : targets) {
                if (target.distanceToSqr(entity) <= RANGE * RANGE) {
                    AbilityHelper.setDeltaMovement(target, 0.0D, target.getDeltaMovement().y - 4.0D, 0.0D);

                    if (damageTick) {
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 5, false, false));
                        target.hurt(entity.damageSources().mobAttack(entity), (float) (this.force * 2));

                        int radius = this.force;
                        BlockPos center = target.blockPosition();

                        // Thread-safe enqueue
                        if (entity.level() instanceof ServerLevel serverLevel) {
                            serverLevel.getServer().tell(new net.minecraft.server.TickTask(0, () -> {
                                for (int dx = -radius; dx <= radius; dx++) {
                                    for (int dy = -2; dy <= 2; dy++) {
                                        for (int dz = -radius; dz <= radius; dz++) {
                                            if (dx * dx + dy * dy + dz * dz <= radius * radius) {
                                                BlockPos p = center.offset(dx, dy, dz);
                                                if (serverLevel.getBlockState(p).isSolid()) {
                                                    serverLevel.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                                                }
                                            }
                                        }
                                    }
                                }
                            }));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        float cooldown = MIN_COOLDOWN + getDuration(entity) * 2.0F;
        this.startCooldown(entity, (long) cooldown);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.jigoku_tabi");
    }
}
