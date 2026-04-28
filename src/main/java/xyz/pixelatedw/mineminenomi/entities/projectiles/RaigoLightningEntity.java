package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class RaigoLightningEntity extends ThrowableProjectile {

    public RaigoLightningEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public RaigoLightningEntity(Level level, LivingEntity shooter) {
        super(EntityType.SNOWBALL, shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }
}
