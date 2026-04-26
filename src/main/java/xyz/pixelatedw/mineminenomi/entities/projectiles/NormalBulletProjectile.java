package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class NormalBulletProjectile extends NuProjectileEntity {
    public NormalBulletProjectile(EntityType<? extends NormalBulletProjectile> type, Level world) {
        super(type, world);
    }

    public NormalBulletProjectile(Level world, LivingEntity thrower) {
        super(ModEntities.NORMAL_BULLET.get(), thrower, world);
        this.setDamage(5.0F);
    }
}
