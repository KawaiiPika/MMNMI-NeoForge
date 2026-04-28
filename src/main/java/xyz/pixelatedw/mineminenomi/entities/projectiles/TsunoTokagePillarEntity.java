package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class TsunoTokagePillarEntity extends ThrowableProjectile {
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    public TsunoTokagePillarEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public TsunoTokagePillarEntity(Level level, LivingEntity thrower) {
        super(ModEntities.TSUNO_TOKAGE_PILLAR.get(), thrower, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 15.0F);
            target.setDeltaMovement(target.getDeltaMovement().add(0, 1.5, 0));
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(this, result.getLocation().x, result.getLocation().y, result.getLocation().z, 2.0F, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
