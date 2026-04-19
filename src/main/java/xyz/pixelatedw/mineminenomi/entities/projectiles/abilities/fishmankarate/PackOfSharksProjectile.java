package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class PackOfSharksProjectile extends AbilityProjectile {

    public PackOfSharksProjectile(EntityType<? extends PackOfSharksProjectile> type, Level world) {
        super(type, world);
    }

    public PackOfSharksProjectile(Level world, LivingEntity owner) {
        super(ModEntities.PACK_OF_SHARKS.get(), owner, world);
        this.setDamage(4.0F);
        this.setMaxLife(40);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 1, 0.1, 0.1, 0.1, 0.05);
        }
    }
}
