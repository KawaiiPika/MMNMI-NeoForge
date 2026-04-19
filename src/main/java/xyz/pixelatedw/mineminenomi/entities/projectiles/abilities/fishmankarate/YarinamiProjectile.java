package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class YarinamiProjectile extends AbilityProjectile {

    public YarinamiProjectile(EntityType<? extends YarinamiProjectile> type, Level world) {
        super(type, world);
    }

    public YarinamiProjectile(Level world, LivingEntity owner) {
        super(ModEntities.YARINAMI.get(), owner, world);
        this.setDamage(12.0F);
        this.setMaxLife(60);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
        }
    }
}
