package xyz.pixelatedw.mineminenomi.api.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class NuProjectileEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {

    protected Ability parentAbility;
    protected float damage = 10.0F;

    public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, LivingEntity owner, Level level) {
        super(type, owner, level);
    }

    public NuProjectileEntity(Level level, LivingEntity owner, Ability parent) {
        super(net.minecraft.world.entity.EntityType.SNOWBALL, owner, level); // Assume a generic one exists or will be added
        this.parentAbility = parent;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
            target.hurt(this.level().damageSources().mobAttack(owner), this.damage);
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        // Required method
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        boolean hasOwner = this.getOwner() != null;
        buffer.writeBoolean(hasOwner);
        if (hasOwner) {
            buffer.writeInt(this.getOwner().getId());
        }
        buffer.writeFloat(this.damage);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf buffer) {
        boolean hasOwner = buffer.readBoolean();
        if (hasOwner) {
            int ownerId = buffer.readInt();
            Entity owner = this.level().getEntity(ownerId);
            if (owner instanceof LivingEntity livingOwner) {
                this.setOwner(livingOwner);
            }
        }
        this.damage = buffer.readFloat();
    }
}
