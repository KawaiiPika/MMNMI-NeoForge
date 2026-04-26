package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class CandleWallAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 120;

    public CandleWallAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();

            serverLevel.getServer().execute(() -> {
                int thickness = 1;
                int height = 4;
                int length = 3;

                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null) {
                    ResourceLocation championId = ModAbilities.REGISTRY.getKey(ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion")));
                    if (championId != null && stats.isAbilityActive(championId.toString())) {
                        thickness = 2;
                    }
                }

                BlockState wax = ModBlocks.WAX.get().defaultBlockState();

                Vec3 look = entity.getLookAngle();
                look = new Vec3(look.x, 0, look.z).normalize();
                BlockPos centerPos = entity.blockPosition().offset((int)(look.x * 4), 0, (int)(look.z * 4));
                Vec3 right = new Vec3(-look.z, 0, look.x).normalize();

                for (int t = 0; t < thickness; t++) {
                    for (int h = 0; h < height; h++) {
                        for (int l = -length; l <= length; l++) {
                            BlockPos pos = centerPos.offset(
                                (int)(look.x * t + right.x * l),
                                h,
                                (int)(look.z * t + right.z * l)
                            );

                            if (serverLevel.getBlockState(pos).canBeReplaced()) {
                                serverLevel.setBlock(pos, wax, 3);
                            }
                        }
                    }
                }
            });
            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.candle_wall");
    }
}
