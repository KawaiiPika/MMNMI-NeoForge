package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KamakuraJussoshiAbility extends Ability {

    private static final int COOLDOWN = 240;

    public KamakuraJussoshiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            BlockPos center;
            if (entity.isCrouching()) {
                center = entity.blockPosition();
            } else {
                net.minecraft.world.phys.HitResult result = net.minecraft.world.entity.projectile.ProjectileUtil.getHitResultOnViewVector(entity, (entity1) -> !entity1.isSpectator(), 20.0);
                center = BlockPos.containing(result.getLocation());
            }

            int[] radii = {4, 6, 8};
            for (int radius : radii) {
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (x * x + y * y + z * z <= radius * radius && x * x + y * y + z * z >= (radius - 1) * (radius - 1)) {
                                BlockPos pos = center.offset(x, Math.max(0, y), z);
                                if (entity.level().isEmptyBlock(pos) || entity.level().getBlockState(pos).canBeReplaced()) {
                                    entity.level().setBlock(pos, Blocks.POWDER_SNOW.defaultBlockState(), 3);
                                }
                            }
                        }
                    }
                }
            }

            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kamakura_jussoshi");
    }
}
