package xyz.pixelatedw.mineminenomi.abilities.ori;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.HashSet;
import java.util.Set;
public class GreatCageAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ori_ori_no_mi");
    private final Set<BlockPos> placedBlocks = new HashSet<>();
    public GreatCageAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                int size = 20; int half = size / 2; BlockPos center = entity.blockPosition(); BlockState bars = Blocks.IRON_BARS.defaultBlockState();
                for (int x = -half; x <= half; x++) {
                    for (int y = -half; y <= half; y++) {
                        for (int z = -half; z <= half; z++) {
                            if (x == -half || x == half || y == -half || y == half || z == -half || z == half) {
                                BlockPos pos = center.offset(x, y, z);
                                if (serverLevel.getBlockState(pos).canBeReplaced()) { serverLevel.setBlock(pos, bars, 3); placedBlocks.add(pos); }
                            }
                        }
                    }
                }
            });
        }
    }
    @Override protected void onTick(LivingEntity entity, long duration) { if (getDuration(entity) >= 1200) { this.stop(entity); } }
    @Override protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                for (BlockPos pos : placedBlocks) {
                    if (serverLevel.getBlockState(pos).is(Blocks.IRON_BARS)) { serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); }
                }
                placedBlocks.clear();
            });
            this.startCooldown(entity, (long) (40.0F + this.getDuration(entity) / 4.0F));
        }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.great_cage"); }
}
