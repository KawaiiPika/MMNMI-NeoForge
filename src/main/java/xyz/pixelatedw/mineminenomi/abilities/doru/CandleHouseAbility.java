package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class CandleHouseAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 1000;

    public CandleHouseAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();

            serverLevel.getServer().execute(() -> {
                int thiccness = 1;

                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null) {
                    ResourceLocation championId = ModAbilities.REGISTRY.getKey(ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion")));
                    if (championId != null && stats.isAbilityActive(championId.toString())) {
                        thiccness = 3;
                    }
                }

                BlockState defaultWaxState = ModBlocks.WAX.get().defaultBlockState();
                BlockPos origin = entity.blockPosition();

                for(int y = -3; y <= 3; ++y) {
                    for(int x = 0; x < thiccness; ++x) {
                        for(int z = -5; z < 5; ++z) {
                            placeBlock(serverLevel, origin.offset(6 - x, y, -z), defaultWaxState);
                            placeBlock(serverLevel, origin.offset(-5 - x, y, -z), defaultWaxState);
                        }
                    }

                    for(int x = -5; x < 5; ++x) {
                        for(int z = 0; z < thiccness; ++z) {
                            placeBlock(serverLevel, origin.offset(-x, y, 6 - z), defaultWaxState);
                            placeBlock(serverLevel, origin.offset(-x, y, -5 - z), defaultWaxState);
                        }
                    }
                }

                for(int x = -5; x < 5; ++x) {
                    for(int z = -5; z < 5; ++z) {
                        placeBlock(serverLevel, origin.offset(-x, 4, -z), defaultWaxState);
                    }
                }
            });

            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    private void placeBlock(ServerLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, state, 3);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.candle_house");
    }
}
