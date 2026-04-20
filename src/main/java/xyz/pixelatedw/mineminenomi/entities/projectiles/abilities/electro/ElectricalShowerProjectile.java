package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ElectricalShowerProjectile extends AbilityProjectile {

    public ElectricalShowerProjectile(EntityType<? extends ElectricalShowerProjectile> type, Level world) {
        super(type, world);
    }

    public ElectricalShowerProjectile(Level world, LivingEntity owner) {
        super(ModEntities.ELECTRICAL_SHOWER.get(), owner, world);
        this.setMaxLife(40);
        this.setDamage(20.0F);
    }
}
