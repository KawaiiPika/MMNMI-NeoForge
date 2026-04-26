package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YukiGakiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");
    private static final int COOLDOWN = 100;

    public YukiGakiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            BlockPos pos = entity.blockPosition();
            net.minecraft.world.level.Level level = entity.level();

            for (int x = -2; x <= 2; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = -2; z <= 2; z++) {
                        if (x*x + z*z <= 4 && (y < 3 || x*x + z*z <= 1)) {
                            BlockPos targetPos = pos.offset(x, y, z);
                            if (level.isEmptyBlock(targetPos) && (x*x + z*z > 1 || y == 3)) {
                                level.setBlockAndUpdate(targetPos, net.minecraft.world.level.block.Blocks.SNOW_BLOCK.defaultBlockState());
                            }
                        }
                    }
                }
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yuki_gaki");
    }
}
