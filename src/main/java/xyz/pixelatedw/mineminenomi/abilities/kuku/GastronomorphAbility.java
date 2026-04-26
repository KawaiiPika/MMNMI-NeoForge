package xyz.pixelatedw.mineminenomi.abilities.kuku;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GastronomorphAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kuku_kuku_no_mi");
    private final Random random = new Random();
    private final List<BlockPos> queue = new ArrayList<>();
    public GastronomorphAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            queue.clear();
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                double maxDist = 384.0;
                BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
                for (double y = -3.0; y < 3.0; ++y) {
                    for (double x = -128.0; x < 128.0; ++x) {
                        for (double z = -128.0; z < 128.0; ++z) {
                            double dist = x*x + z*z + y*y;
                            if (dist < maxDist && random.nextDouble() >= dist / maxDist) {
                                mutpos.set(entity.getX() + x, entity.getY() + y, entity.getZ() + z);
                                if (serverLevel.getBlockState(mutpos.above()).isAir()) { queue.add(mutpos.immutable()); }
                            }
                        }
                    }
                }
            });
        }
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4, false, false));
            ServerLevel serverLevel = (ServerLevel) entity.level();
            BlockState cake = Blocks.CAKE.defaultBlockState();
            int count = Math.min(50, queue.size());
            for (int i = 0; i < count; i++) {
                BlockPos pos = queue.remove(0);
                if (serverLevel.getBlockState(pos).canBeReplaced()) { serverLevel.setBlock(pos, cake, 3); }
            }
            if (getDuration(entity) >= 60) { this.stop(entity); }
        }
    }
    @Override protected void stopUsing(LivingEntity entity) { queue.clear(); this.startCooldown(entity, 300); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gastronomorph"); }
}
