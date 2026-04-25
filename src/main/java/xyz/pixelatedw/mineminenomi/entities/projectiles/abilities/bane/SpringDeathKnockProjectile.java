package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SpringDeathKnockProjectile extends xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile {
    public SpringDeathKnockProjectile(EntityType<? extends xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile> type, Level level) {
        super(type, level);
    }

    public SpringDeathKnockProjectile(Level level, LivingEntity shooter) {
        super(ModEntities.SPRING_DEATH_KNOCK_PROJECTILE.get(), shooter, level);
        this.setDamage(20.0F);
        this.setMaxLife(60);

    }
}
