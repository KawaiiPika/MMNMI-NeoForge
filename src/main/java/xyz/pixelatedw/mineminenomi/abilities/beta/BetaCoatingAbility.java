package xyz.pixelatedw.mineminenomi.abilities.beta;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
public class BetaCoatingAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_beta_no_mi");
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_coating");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(MODIFIER_ID, -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    private boolean exploded = false;
    public BetaCoatingAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        exploded = false;
        var inst = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (inst != null && !inst.hasModifier(MODIFIER_ID)) { inst.addTransientModifier(SPEED_MODIFIER); }
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration % 10 == 0) { checkIfOnFire(entity); }
            if (!exploded) { climbingMovement(entity); }
        }
        if (getDuration(entity) >= 800 || exploded) { this.stop(entity); }
    }
    private void climbingMovement(LivingEntity entity) {
        boolean isNearBlock = false;
        AABB bb = entity.getBoundingBox().inflate(0.25);
        Level level = entity.level();
        BlockPos min = BlockPos.containing(bb.minX, bb.minY, bb.minZ);
        BlockPos max = BlockPos.containing(bb.maxX, bb.maxY, bb.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (level.getBlockState(pos).isFaceSturdy(level, pos, net.minecraft.core.Direction.UP)) { isNearBlock = true; break; }
        }
        if ((entity.horizontalCollision && !entity.verticalCollision) || (entity.isCrouching() && isNearBlock)) {
            double climbSpeed = Math.min(0.1, entity.getLookAngle().y * 0.5);
            if (entity.isCrouching()) { entity.setDeltaMovement(entity.getDeltaMovement().x, 0.0, entity.getDeltaMovement().z); }
            else { entity.setDeltaMovement(entity.getDeltaMovement().x, climbSpeed, entity.getDeltaMovement().z); }
            entity.fallDistance = 0.0F;
        }
    }
    private void checkIfOnFire(LivingEntity entity) {
        if (entity.isOnFire()) { exploded = true; } else {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                for (String activeId : stats.getActiveAbilities()) {
                    if (activeId.contains("mera_mera") || activeId.contains("magu_magu")) { exploded = true; break; }
                }
            }
        }
    }
    @Override protected void stopUsing(LivingEntity entity) {
        var inst = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (inst != null) { inst.removeModifier(MODIFIER_ID); }
        long bonus = 0;
        if (exploded && !entity.level().isClientSide) {
            bonus = 600; entity.clearFire();
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 6.0F, false, Level.ExplosionInteraction.MOB);
        }
        this.startCooldown(entity, Math.min(100, this.getDuration(entity) / 2) + bonus);
    }
    @Override public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) { return amount; }
    @Override public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        if (this.isUsing(entity)) {
            if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.PLAYER_EXPLOSION) || source.is(DamageTypes.LIGHTNING_BOLT)) {
                exploded = true; this.stop(entity); return false;
            }
            if (source.getEntity() instanceof LivingEntity attacker && attacker.isOnFire()) { return false; }
            return true;
        }
        return false;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.beta_coating"); }
}
