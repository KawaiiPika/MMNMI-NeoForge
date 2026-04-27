package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class JigokuTabiAbility extends Ability {
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
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityState("jigoku_force", 4);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 120) {
            this.stop(entity);
            return;
        }

        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;
        int force = stats.getAbilityState("jigoku_force");
        if (force == 0) force = 4;

        if (duration % 60 == 0 && force < 6) {
            force++;
            stats.setAbilityState("jigoku_force", force);
        }

        if (!entity.level().isClientSide) {
            var aabb = entity.getBoundingBox().inflate(24.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                target.setDeltaMovement(target.getDeltaMovement().x, target.getDeltaMovement().y - 4.0, target.getDeltaMovement().z);

                if (duration % 20 == 0) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 5, false, false));
                    target.hurt(entity.damageSources().mobAttack(entity), force * 2.0F);

                    // Simple crater logic
                    BlockPos center = target.blockPosition();
                    int radius = force;
                    for (int dx = -radius; dx <= radius; dx++) {
                        for (int dy = -2; dy <= 1; dy++) {
                            for (int dz = -radius; dz <= radius; dz++) {
                                if (dx*dx + dy*dy + dz*dz <= radius*radius) {
                                    BlockPos p = center.offset(dx, dy, dz);
                                    if (entity.level().getBlockState(p).isCollisionShapeFullBlock(entity.level(), p) && entity.level().getBlockState(p).getDestroySpeed(entity.level(), p) >= 0) {
                                        entity.level().setBlock(p, Blocks.AIR.defaultBlockState(), 3);
                                    }
                                }
                            }
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
