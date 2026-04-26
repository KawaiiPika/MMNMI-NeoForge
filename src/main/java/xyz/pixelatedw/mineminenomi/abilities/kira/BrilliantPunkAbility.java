package xyz.pixelatedw.mineminenomi.abilities.kira;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import java.util.HashSet;
import java.util.Set;
public class BrilliantPunkAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kira_kira_no_mi");
    private final Set<Integer> hitTargets = new HashSet<>();
    public BrilliantPunkAbility() { super(FRUIT); }
    @Override public xyz.pixelatedw.mineminenomi.api.util.Result canUse(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.util.Result superResult = super.canUse(entity);
        if (superResult.isFail()) return superResult;
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            ResourceLocation diamondId = ModAbilities.REGISTRY.getKey(ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "diamond_body")));
            if (diamondId != null && !stats.isAbilityActive(diamondId.toString())) { return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.mineminenomi.error.requires_diamond_body")); }
        }
        return xyz.pixelatedw.mineminenomi.api.util.Result.success();
    }
    @Override protected void startUsing(LivingEntity entity) { hitTargets.clear(); }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (entity.isAlive()) {
            Vec3 look = entity.getLookAngle(); Vec3 speed = look.multiply(2.3, 0.0, 2.3); entity.move(net.minecraft.world.entity.MoverType.SELF, speed);
            if (!entity.level().isClientSide) {
                ServerLevel serverLevel = (ServerLevel) entity.level();
                AABB hitBox = entity.getBoundingBox().expandTowards(look.scale(1.6));
                for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, hitBox)) {
                    if (target != entity && !hitTargets.contains(target.getId())) {
                        target.hurt(entity.damageSources().mobAttack(entity), 25.0F); hitTargets.add(target.getId());
                        target.setPos(entity.getX() + look.x, entity.getY(), entity.getZ() + look.z);
                    }
                }
            }
        }
        if (getDuration(entity) >= 10) { this.stop(entity); }
    }
    @Override protected void stopUsing(LivingEntity entity) { hitTargets.clear(); this.startCooldown(entity, 200); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.brilliant_punk"); }
}
