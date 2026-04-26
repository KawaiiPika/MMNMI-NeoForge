package xyz.pixelatedw.mineminenomi.abilities.beta;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class HanamizuShinkenAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_beta_no_mi");
    public HanamizuShinkenAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 4, false, false));
            Vec3 lookVec = entity.getLookAngle();
            for (int i = 0; i < 4; ++i) {
                double distance = i / 2.0;
                Vec3 pos = new Vec3(entity.getX() + lookVec.x * distance, entity.getY() + entity.getEyeHeight() + lookVec.y * distance, entity.getZ() + lookVec.z * distance);
                for (Entity target : entity.level().getEntities(entity, new AABB(pos.x - 1.2, pos.y - 1.2, pos.z - 1.2, pos.x + 1.2, pos.y + 2.4, pos.z + 1.2), t -> t != entity)) {
                    if (target instanceof ThrowableProjectile || target instanceof AbstractArrow) { target.setDeltaMovement(-target.getDeltaMovement().x / 2.0, target.getDeltaMovement().y, -target.getDeltaMovement().z / 2.0); }
                }
            }
        }
        if (getDuration(entity) >= 60) { this.stop(entity); }
    }
    @Override protected void stopUsing(LivingEntity entity) { this.startCooldown(entity, 100); }
    @Override public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        if (this.isUsing(entity)) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity) { attacker.setDeltaMovement(entity.getLookAngle().multiply(4.0, 4.0, 4.0)); }
            return true;
        }
        return false;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.hanamizu_shinken_shirahadori"); }
}
