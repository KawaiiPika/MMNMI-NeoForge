package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class TatsuMakiProjectile extends AbilityProjectile {

    public TatsuMakiProjectile(EntityType<? extends TatsuMakiProjectile> type, Level world) {
        super(type, world);
    }

    public TatsuMakiProjectile(Level world, LivingEntity owner) {
        super(ModEntities.TATSU_MAKI_PROJECTILE.get(), owner, world);
        this.setDamage(15.0F);
        this.setMaxLife(60);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 5 == 0) {
            ((net.minecraft.server.level.ServerLevel)this.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
