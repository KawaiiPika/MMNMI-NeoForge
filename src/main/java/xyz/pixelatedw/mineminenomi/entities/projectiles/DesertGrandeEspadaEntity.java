package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class DesertGrandeEspadaEntity extends ThrowableProjectile {
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    public DesertGrandeEspadaEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public DesertGrandeEspadaEntity(Level level, LivingEntity thrower) {
        super(ModEntities.DESERT_GRANDE_ESPADA.get(), thrower, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 28.0F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(this, result.getLocation().x, result.getLocation().y, result.getLocation().z, 4.0F, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
