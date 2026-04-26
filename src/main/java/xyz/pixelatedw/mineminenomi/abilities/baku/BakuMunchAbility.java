package xyz.pixelatedw.mineminenomi.abilities.baku;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.ArrayList;
import java.util.List;
public class BakuMunchAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "baku_baku_no_mi");
    public BakuMunchAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            Vec3 startVec = entity.position().add(0, entity.getEyeHeight(), 0);
            Vec3 endVec = startVec.add(entity.getLookAngle().scale(16.0));
            BlockHitResult mop = entity.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
            if (mop.getType() == HitResult.Type.BLOCK && entity.distanceToSqr(mop.getLocation()) < 25.0) {
                serverLevel.getServer().execute(() -> {
                    List<BlockPos> positions = new ArrayList<>();
                    BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
                    int hitX = mop.getBlockPos().getX(); int hitY = mop.getBlockPos().getY(); int hitZ = mop.getBlockPos().getZ();
                    for (int x = -2; x < 2; ++x) {
                        for (int y = 0; y < 3; ++y) {
                            for (int z = -2; z < 2; ++z) {
                                mutpos.set(hitX + x, hitY - y, hitZ + z);
                                BlockState tempBlock = serverLevel.getBlockState(mutpos);
                                if (tempBlock.getDestroySpeed(serverLevel, mutpos) >= 0.0F && !tempBlock.isAir()) {
                                    if (serverLevel.setBlock(mutpos, Blocks.AIR.defaultBlockState(), 2)) {
                                        if (entity instanceof Player player) { player.getInventory().placeItemBackInInventory(new ItemStack(tempBlock.getBlock())); }
                                        positions.add(mutpos.immutable());
                                    }
                                }
                            }
                        }
                    }
                });
            }
            this.startCooldown(entity, 60);
        }
        this.stop(entity);
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.baku_munch"); }
}
