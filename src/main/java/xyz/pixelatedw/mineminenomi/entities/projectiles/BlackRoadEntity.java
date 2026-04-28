package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class BlackRoadEntity extends ThrowableProjectile {

    public BlackRoadEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public BlackRoadEntity(Level level, LivingEntity thrower) {
        super(EntityType.SNOWBALL, thrower, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }
}
