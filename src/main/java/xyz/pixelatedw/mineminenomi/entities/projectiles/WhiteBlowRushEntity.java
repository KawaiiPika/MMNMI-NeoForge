package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class WhiteBlowRushEntity extends ThrowableProjectile {

    public WhiteBlowRushEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public WhiteBlowRushEntity(Level level, LivingEntity shooter) {
        super(EntityType.SNOWBALL, shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }
}
