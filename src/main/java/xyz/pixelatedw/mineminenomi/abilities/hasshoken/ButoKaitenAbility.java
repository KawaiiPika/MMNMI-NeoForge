package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class ButoKaitenAbility extends Ability {

    private static final long COOLDOWN = 200L;
    private static final long HOLD_TIME = 100L;
    private static final float AOE_DAMAGE = 10.0F;

    private long continuityStartTime = 0;
    private Vec3 direction = Vec3.ZERO;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            if (entity.onGround()) {
                this.continuityStartTime = entity.level().getGameTime();
                this.direction = entity.getLookAngle();
                // Play head stand spin animation
            } else {
                this.stop(entity);
            }
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;

        if (currentDuration > HOLD_TIME) {
            this.stop(entity);
            return;
        }

        if (currentDuration % 5 == 0) {
            entity.level().playSound(null, entity.blockPosition(), ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.getRandom().nextFloat() / 4.0F);
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            float radius = entity.getBbWidth() / 2.0F + 4.0F;
            AABB area = entity.getBoundingBox().inflate(radius);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), AOE_DAMAGE)) {
                    Vec3 pushVec = target.position().subtract(entity.position()).normalize().scale(2.0).add(0.0, 0.5, 0.0);
                    target.setDeltaMovement(pushVec);
                }
            }

            if (currentDuration % 20 == 0) {
                net.minecraft.world.entity.projectile.Arrow projectile = new net.minecraft.world.entity.projectile.Arrow(serverLevel, entity, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.ARROW), null);
                // Damage logic here for the projectile
                // AbstractHurtingProjectile usage directly sets acceleration in the shoot method internally or through constructor
                // Just calling shootFromRotation is enough for regular projectiles
                // projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
                projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F); serverLevel.addFreshEntity(projectile);
            }
        }

        // Allow some control if ranbu mode (we'll simplify to just follow look angle or original look angle)
        // Original code moved the entity automatically in the chosen direction
        Vec3 velocity = new Vec3(this.direction.x, 0.0, this.direction.z).normalize().scale(0.2);
        entity.setDeltaMovement(velocity.x, entity.getDeltaMovement().y, velocity.z);
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Buto Kaiten");
    }
}
