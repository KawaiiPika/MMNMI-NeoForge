package xyz.pixelatedw.mineminenomi.abilities.kachi;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class EvaporateAbility extends Ability {

    public EvaporateAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kachi_kachi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            BlockPos center = entity.blockPosition();
            for (BlockPos pos : BlockPos.betweenClosed(center.offset(-6, -6, -6), center.offset(6, 6, 6))) {
                if (pos.distSqr(center) <= 36) {
                    var state = entity.level().getBlockState(pos);
                    if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
                        entity.level().setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
                    } else if (state.is(Blocks.WATER)) {
                        entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    } else if (state.is(Blocks.ICE) || state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK)) {
                        entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
            this.startCooldown(entity, 240);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.evaporate");
    }
}
