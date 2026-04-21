package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;


public class LiberationProjectile extends ThrowableProjectile {

    private int life;

    public LiberationProjectile(EntityType<? extends LiberationProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LiberationProjectile(Level level, LivingEntity owner) {
        super((net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.ThrowableProjectile>) ModEntities.LIBERATION.get(), owner, level);
        this.life = 100;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life-- <= 0) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.getOwner() != null && result.getEntity() != this.getOwner()) {
             result.getEntity().hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 5.0F);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
             this.discard();
        }
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {}
}
