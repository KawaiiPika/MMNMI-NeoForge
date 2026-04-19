package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class YakkodoriProjectile extends AbilityProjectile {

    public YakkodoriProjectile(EntityType<? extends YakkodoriProjectile> type, Level world) {
        super(type, world);
    }

    public YakkodoriProjectile(Level world, LivingEntity owner) {
        super(ModEntities.YAKKODORI.get(), owner, world);
        this.setDamage(15.0F);
        this.setMaxLife(40);
    }
}
