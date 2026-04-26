package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

import java.util.HashSet;
import java.util.Set;

public class TokudaiCandleAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 6000;
    private static final int MAX_DURATION = 1200;
    private static final float RANGE = 100.0F;

    private BlockPos centerPos;
    private final Set<BlockPos> placedBlocks = new HashSet<>();

    public TokudaiCandleAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 startVec = entity.position().add(0, entity.getEyeHeight(), 0);
            Vec3 endVec = startVec.add(entity.getLookAngle().scale(128.0));
            BlockHitResult result = entity.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));

            if (result.getType() == HitResult.Type.BLOCK) {
                centerPos = result.getBlockPos();
            } else {
                centerPos = BlockPos.containing(endVec);
            }

            ServerLevel serverLevel = (ServerLevel) entity.level();

            AABB area = new AABB(centerPos).inflate(RANGE);
            for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    Vec3 pushVec = target.position().subtract(centerPos.getCenter()).normalize().scale(10.0).add(0, 1.0, 0);
                    target.setDeltaMovement(pushVec);
                }
            }

            serverLevel.getServer().execute(() -> {
                BlockState wax = ModBlocks.WAX.get().defaultBlockState();
                generateCube(serverLevel, centerPos, 15, wax);
                generateCube(serverLevel, centerPos.above(15), 10, wax);
                generateCube(serverLevel, centerPos.above(25), 5, wax);
                generateCube(serverLevel, centerPos.above(35), 3, wax);
            });
        }
    }

    private void generateCube(ServerLevel level, BlockPos origin, int size, BlockState state) {
        int half = size / 2;
        for (int x = -half; x <= half; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = -half; z <= half; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    if (level.getBlockState(pos).canBeReplaced()) {
                        level.setBlock(pos, state, 3);
                        placedBlocks.add(pos);
                    }
                }
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % 100 == 0 && centerPos != null) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            AABB area = new AABB(centerPos).inflate(RANGE);
            for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0)); // Stubbed proxy effect
                }
            }
        }

        if (getDuration(entity) >= MAX_DURATION) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                for (BlockPos pos : placedBlocks) {
                    if (serverLevel.getBlockState(pos).is(ModBlocks.WAX.get())) {
                        serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
                placedBlocks.clear();
            });
            this.startCooldown(entity, COOLDOWN);
        }
        centerPos = null;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tokudai_candle");
    }
}
