package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class NanajuniPoundHoProjectile extends AbilityProjectile {

    public NanajuniPoundHoProjectile(EntityType<? extends NanajuniPoundHoProjectile> type, Level world) {
        super(type, world);
    }

    public NanajuniPoundHoProjectile(Level world, LivingEntity owner) {
        super(ModEntities.NANAJUNI_POUND_HO_PROJECTILE.get(), owner, world);
        this.setDamage(20.0F);
        this.setMaxLife(50);
    }
}
