package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ElectroVisualProjectile extends AbilityProjectile {

    public ElectroVisualProjectile(EntityType<? extends ElectroVisualProjectile> type, Level world) {
        super(type, world);
    }

    public ElectroVisualProjectile(Level world, LivingEntity owner) {
        super(ModEntities.ELECTRO_VISUAL.get(), owner, world);
        this.setMaxLife(20);
        this.setDamage(0.0F);
        this.setYRot(this.random.nextFloat() * 360.0F);
        this.setXRot((this.random.nextFloat() - 0.5F) * 40.0F);
    }
}
