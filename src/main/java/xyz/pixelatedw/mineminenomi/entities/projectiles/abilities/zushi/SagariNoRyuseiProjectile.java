package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class SagariNoRyuseiProjectile extends NuProjectileEntity {

    private float size = 10.0f;

    public SagariNoRyuseiProjectile(EntityType<? extends SagariNoRyuseiProjectile> type, Level level) {
        super(type, level);
    }

    public SagariNoRyuseiProjectile(Level level, LivingEntity owner, Ability ability) {
        super((net.minecraft.world.entity.EntityType<? extends xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity>)ModEntities.SAGARI_NO_RYUSEI_PROJECTILE.get(), owner, level);
    }

    public void setSize(float s) {
        this.size = s;
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        float mult = this.size / 30.0F;
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 20.0F * mult, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
        this.discard();
    }

    public void tick() {
        super.tick();
        float mult = this.size / 30.0F;
        if (this.tickCount < 1) {
            this.setBoundingBox(this.getBoundingBox().inflate((double)mult));
        }

        if (!this.level().isClientSide) {
            for(int i = 0; i < 25; ++i) {
                net.minecraft.core.particles.ParticleType<xyz.pixelatedw.mineminenomi.particles.SimpleParticleData> pt = this.tickCount % 2 == 0 ? xyz.pixelatedw.mineminenomi.init.ModParticleTypes.MOKU.get() : xyz.pixelatedw.mineminenomi.init.ModParticleTypes.MERA.get();
                double offsetX = this.level().random.nextDouble() * 5.0D * mult;
                double offsetY = this.level().random.nextDouble();
                double offsetZ = this.level().random.nextDouble() * 5.0D * mult;
                xyz.pixelatedw.mineminenomi.particles.SimpleParticleData data = new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(pt);
                if (this.level() instanceof net.minecraft.server.level.ServerLevel sl) {
                    for(net.minecraft.server.level.ServerPlayer p : sl.players()) {
                        sl.sendParticles(p, data, true, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeFloat(this.size);
    }

    public void readSpawnData(RegistryFriendlyByteBuf buffer) {
        super.readSpawnData(buffer);
        this.size = buffer.readFloat();
    }
}
