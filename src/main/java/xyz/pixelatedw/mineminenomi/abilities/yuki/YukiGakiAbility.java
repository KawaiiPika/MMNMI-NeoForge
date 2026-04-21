package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YukiGakiAbility extends Ability {

    private static final int COOLDOWN = 100;

    public YukiGakiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Direction dir = entity.getDirection();
            BlockPos center = entity.blockPosition().relative(dir, 2);
            BlockState snow = net.minecraft.world.level.block.Blocks.POWDER_SNOW.defaultBlockState();

            for (int w = -1; w <= 1; w++) {
                for (int h = 0; h < 4; h++) {
                    for (int d = 0; d < 2; d++) {
                        BlockPos pos = center.relative(dir.getClockWise(), w).above(h).relative(dir, d);
                        if (entity.level().isEmptyBlock(pos) || entity.level().getBlockState(pos).canBeReplaced()) {
                            entity.level().setBlock(pos, snow, 3);
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
        return Component.translatable("ability.mineminenomi.yuki_gaki");
    }
}
