package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class HydraEntity extends ThrowableProjectile {
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    public HydraEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public HydraEntity(Level level, LivingEntity thrower) {
        super(ModEntities.HYDRA.get(), thrower, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 15.0F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0)).forEach(target -> {
                if (target != this.getOwner()) {
                }
            });
            this.discard();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
