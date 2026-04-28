package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class WhiteBlowEntity extends ThrowableProjectile {

    public WhiteBlowEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public WhiteBlowEntity(Level level, LivingEntity shooter) {
        super(ModEntities.WHITE_BLOW.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }
}
