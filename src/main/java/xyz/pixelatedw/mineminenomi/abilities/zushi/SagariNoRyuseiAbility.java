package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class SagariNoRyuseiAbility extends Ability {

    public SagariNoRyuseiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sagari_no_ryusei");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide() && entity.level() instanceof ServerLevel serverLevel) {
            HitResult mop = entity.pick(50.0D, 1.0F, false);
            if (mop.getType() != HitResult.Type.MISS) {
                double x = mop.getLocation().x;
                double y = mop.getLocation().y;
                double z = mop.getLocation().z;

                // Ensure thread-safe modification
                serverLevel.getServer().execute(() -> {
                    serverLevel.explode(entity, x, y, z, 10.0F, Level.ExplosionInteraction.TNT);
                    // Generate Crater (simulate blocks flying)
                    int r = 5;
                    net.minecraft.core.BlockPos center = net.minecraft.core.BlockPos.containing(x, y, z);
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
                });
            }

            this.startCooldown(entity, 900);
        }
    }
}
