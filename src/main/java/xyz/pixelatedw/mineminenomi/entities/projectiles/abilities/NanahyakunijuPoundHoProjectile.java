package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class NanahyakunijuPoundHoProjectile extends AbilityProjectile {

    public NanahyakunijuPoundHoProjectile(EntityType<? extends NanahyakunijuPoundHoProjectile> type, Level world) {
        super(type, world);
    }

    public NanahyakunijuPoundHoProjectile(Level world, LivingEntity owner) {
        super(ModEntities.NANAHYAKUNIJU_POUND_HO_PROJECTILE.get(), owner, world);
        this.setDamage(35.0F);
        this.setMaxLife(60);
    }
}
