package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SanbyakurokujuPoundHoProjectile extends AbilityProjectile {

    public SanbyakurokujuPoundHoProjectile(EntityType<? extends SanbyakurokujuPoundHoProjectile> type, Level world) {
        super(type, world);
    }

    public SanbyakurokujuPoundHoProjectile(Level world, LivingEntity owner) {
        super(ModEntities.SANBYAKUROKUJU_POUND_HO.get(), owner, world);
        this.setDamage(30.0F);
        this.setMaxLife(60);
    }
}
