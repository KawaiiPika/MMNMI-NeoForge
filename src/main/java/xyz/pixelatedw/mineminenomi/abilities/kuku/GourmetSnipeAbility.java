package xyz.pixelatedw.mineminenomi.abilities.kuku;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
public class GourmetSnipeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kuku_kuku_no_mi");
    private static final List<Item> FOODS = Arrays.asList(Items.APPLE, Items.BREAD, Items.MUSHROOM_STEW, Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.PUMPKIN_PIE, Items.BEETROOT);
    private int initialY;
    private final Set<Integer> hitTargets = new HashSet<>();
    private final Random random = new Random();
    public GourmetSnipeAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        initialY = (int) entity.getY(); hitTargets.clear();
        entity.setDeltaMovement(entity.getLookAngle().multiply(6.0, 6.0, 6.0));
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            Vec3 speed = entity.getLookAngle().multiply(1.6, 1.0, 1.6);
            entity.setDeltaMovement(speed.x, entity.getDeltaMovement().y, speed.z);
            if (entity.horizontalCollision) {
                ServerLevel serverLevel = (ServerLevel) entity.level();
                AABB area = entity.getBoundingBox().inflate(2.0);
                for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
                    if (target != entity && !hitTargets.contains(target.getId())) { target.hurt(entity.damageSources().mobAttack(entity), 20.0F); hitTargets.add(target.getId()); }
                }
                BlockPos.betweenClosedStream(area).forEach(pos -> {
                    if (pos.getY() >= initialY && serverLevel.getBlockState(pos).canBeReplaced()) {
                        serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        if (random.nextFloat() > 0.4f) { serverLevel.addFreshEntity(new ItemEntity(serverLevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FOODS.get(random.nextInt(FOODS.size()))))); }
                    }
                });
            }
        }
        if (getDuration(entity) >= 10) { this.stop(entity); }
    }
    @Override protected void stopUsing(LivingEntity entity) { hitTargets.clear(); this.startCooldown(entity, 400); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gourmet_snipe"); }
}
