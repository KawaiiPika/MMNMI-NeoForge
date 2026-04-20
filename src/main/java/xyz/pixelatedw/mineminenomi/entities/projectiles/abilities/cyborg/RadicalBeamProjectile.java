package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class RadicalBeamProjectile extends AbilityProjectile {

    public RadicalBeamProjectile(EntityType<? extends RadicalBeamProjectile> type, Level world) {
        super(type, world);
    }

    public RadicalBeamProjectile(Level world, LivingEntity owner) {
        super(ModEntities.RADICAL_BEAM.get(), owner, world);
        this.setDamage(50.0F);
        this.setMaxLife(100);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
        }
    }
}
