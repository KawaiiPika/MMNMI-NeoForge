package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class StrongRightProjectile extends AbilityProjectile {

    public StrongRightProjectile(EntityType<? extends StrongRightProjectile> type, Level world) {
        super(type, world);
    }

    public StrongRightProjectile(Level world, LivingEntity owner) {
        super(ModEntities.STRONG_RIGHT.get(), owner, world);
        this.setDamage(20.0F);
        this.setMaxLife(15);
    }
}
