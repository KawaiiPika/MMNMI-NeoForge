package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SanjurokuPoundHoProjectile extends AbilityProjectile {

    public SanjurokuPoundHoProjectile(EntityType<? extends SanjurokuPoundHoProjectile> type, Level world) {
        super(type, world);
    }

    public SanjurokuPoundHoProjectile(Level world, LivingEntity owner) {
        super(ModEntities.SANJUROKU_POUND_HO_PROJECTILE.get(), owner, world);
        this.setDamage(10.0F);
        this.setMaxLife(40);
    }
}
