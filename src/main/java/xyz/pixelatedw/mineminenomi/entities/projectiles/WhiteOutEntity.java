package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class WhiteOutEntity extends ThrowableProjectile {

    public WhiteOutEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public WhiteOutEntity(Level level, LivingEntity shooter) {
        super(ModEntities.WHITE_OUT.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }
}
