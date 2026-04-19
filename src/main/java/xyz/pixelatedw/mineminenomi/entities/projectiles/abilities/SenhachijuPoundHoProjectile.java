package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SenhachijuPoundHoProjectile extends AbilityProjectile {

    public SenhachijuPoundHoProjectile(EntityType<? extends SenhachijuPoundHoProjectile> type, Level world) {
        super(type, world);
    }

    public SenhachijuPoundHoProjectile(Level world, LivingEntity owner) {
        super(ModEntities.SENHACHIJU_POUND_HO_PROJECTILE.get(), owner, world);
        this.setDamage(40.0F);
        this.setMaxLife(60);
    }
}
