package xyz.pixelatedw.mineminenomi.abilities.ryusoken;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RyuNoIbukiAbility extends Ability {

    private static final long COOLDOWN = 500L;
    private static final long CHARGE_TIME = 30L;
    private static final float EXPLOSION_RADIUS = 15.0F;

    private long chargeStartTime = 0;
    private boolean isCharging = false;
    private final List<BlockPos> affectedBlocks = new ArrayList<>();

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity) && entity.onGround()) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();

            if (entity.level() instanceof ServerLevel serverLevel) {
                int radiusXZ = 7;
                BlockPos center = entity.blockPosition();
                List<BlockPos> allPositions = new ArrayList<>();

                for (int x = -radiusXZ; x <= radiusXZ; x++) {
                    for (int y = -3; y <= 0; y++) {
                        for (int z = -radiusXZ; z <= radiusXZ; z++) {
                            BlockPos pos = center.offset(x, y, z);
                            if (pos.distSqr(center) <= radiusXZ * radiusXZ) {
                                if (serverLevel.getBlockState(pos).isSolidRender(serverLevel, pos) && serverLevel.getBlockState(pos).getBlock() != Blocks.AIR) {
                                    allPositions.add(pos);
                                }
                            }
                        }
                    }
                }

                Collections.shuffle(allPositions);
                this.affectedBlocks.clear();
                for (int i = 0; i < allPositions.size(); i++) {
                    if (i % 2 == 1) { // Take half
                        this.affectedBlocks.add(allPositions.get(i));
                    }
                }
            }
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;

            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));

            if (entity.level() instanceof ServerLevel serverLevel) {
                AABB area = entity.getBoundingBox().inflate(EXPLOSION_RADIUS);
                List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
                targets.remove(entity);

                for (LivingEntity target : targets) {
                    if (target.onGround()) {
                        target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 5, 0, false, false));
                    }
                }
            }

            if (currentCharge >= CHARGE_TIME) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    AABB area = entity.getBoundingBox().inflate(EXPLOSION_RADIUS);
                    List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
                    targets.remove(entity);

                    for (LivingEntity target : targets) {
                        if (target.onGround()) {
                            target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 80, 0, false, false));
                        }
                    }

                    // Throw blocks up
                    for (BlockPos pos : this.affectedBlocks) {
                        if (serverLevel.getBlockState(pos).getBlock() != Blocks.AIR) {
                            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(serverLevel, pos, serverLevel.getBlockState(pos));
                            Vec3 dirVec = entity.position().subtract(fallingBlock.position()).normalize();
                            fallingBlock.setDeltaMovement(dirVec.x * serverLevel.random.nextDouble() * 2.0, 0.5 + serverLevel.random.nextDouble(), dirVec.z * serverLevel.random.nextDouble() * 2.0);
                            fallingBlock.dropItem = false; // Usually we don't want them to drop items

                            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                            serverLevel.addFreshEntity(fallingBlock);
                        }
                    }

                    this.affectedBlocks.clear();
                }

                this.isCharging = false;
                this.startCooldown(entity, COOLDOWN);
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.isCharging = false;
        this.affectedBlocks.clear();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Ryu no Ibuki");
    }
}
