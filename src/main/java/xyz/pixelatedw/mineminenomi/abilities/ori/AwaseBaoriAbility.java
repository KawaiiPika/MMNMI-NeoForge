package xyz.pixelatedw.mineminenomi.abilities.ori;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.HashSet;
import java.util.Set;
public class AwaseBaoriAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ori_ori_no_mi");
    private final Set<BlockPos> placedBlocks = new HashSet<>();
    private boolean activeProjectile = false;
    public AwaseBaoriAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) { placedBlocks.clear(); activeProjectile = true; }
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && placedBlocks.isEmpty() && !activeProjectile) { this.stop(entity); }
        if (getDuration(entity) >= 100) { this.stop(entity); }
    }
    @Override protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                for (BlockPos pos : placedBlocks) {
                    if (!serverLevel.getBlockState(pos).isAir()) { serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); }
                }
                placedBlocks.clear();
            });
            activeProjectile = false;
            this.startCooldown(entity, 200);
        }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.awase_baori"); }
}
