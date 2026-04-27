package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class LavaFlowAbility extends Ability {

    public LavaFlowAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            BlockPos pos = entity.blockPosition().below();
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos targetPos = pos.offset(x, 0, z);
                    if (entity.level().getBlockState(targetPos).isSolidRender(entity.level(), targetPos)) {
                        entity.level().setBlockAndUpdate(targetPos, Blocks.MAGMA_BLOCK.defaultBlockState());
                    }
                }
            }
        }
        
        if (duration >= 200) { // 10 seconds
            stopUsing(entity);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "lava_flow");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.lava_flow");
    }
}
