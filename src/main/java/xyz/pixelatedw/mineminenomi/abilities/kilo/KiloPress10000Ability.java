package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import java.util.List;

public class KiloPress10000Ability extends Ability {

    private double initialPosY = 0.0;

    public KiloPress10000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.10000_kilo_press");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.onGround()) {
            this.initialPosY = entity.getY();
        } else {
            this.initialPosY = 0.0;
        }
        entity.setDeltaMovement(entity.getDeltaMovement().x, -5.0D, entity.getDeltaMovement().z);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.onGround()) {
            if (this.initialPosY > 0.0 && entity.getY() < this.initialPosY) {
                float damage = (float) Math.min(80.0F, Math.max(1.0F, this.initialPosY - entity.getY()));
                if (damage > 0.0F && !entity.level().isClientSide()) {
                    // Deal damage in area
                    AABB aabb = entity.getBoundingBox().inflate(5.0D);
                    List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());
                    DamageSource source = entity.damageSources().mobAttack(entity);
                    for (LivingEntity target : targets) {
                        double dist = target.distanceTo(entity);
                        if (dist <= 1.0) {
                            target.hurt(source, damage);
                        } else {
                            target.hurt(source, damage);
                        }
                    }
                }
                this.initialPosY = 0.0;

                // Generate crater effect
                if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    serverLevel.getServer().execute(() -> {
                        int r = 3;
                        net.minecraft.core.BlockPos center = entity.blockPosition().below();
                        for (int bx = -r; bx <= r; bx++) {
                            for (int by = -r; by <= r; by++) {
                                for (int bz = -r; bz <= r; bz++) {
                                    if (bx*bx + by*by + bz*bz <= r*r) {
                                        net.minecraft.core.BlockPos targetPos = center.offset(bx, by, bz);
                                        if (!serverLevel.getBlockState(targetPos).isAir() && serverLevel.getBlockState(targetPos).getDestroySpeed(serverLevel, targetPos) >= 0) {
                                            serverLevel.setBlock(targetPos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION, entity.getX(), entity.getY(), entity.getZ(), 5, 1.0D, 1.0D, 1.0D, 0.1D);
                    });
                }
            }
            this.stop(entity);
        } else if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        startCooldown(entity, 20 + getDuration(entity));
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        return isUsing(entity) && source.is(net.minecraft.tags.DamageTypeTags.IS_FALL);
    }
}
