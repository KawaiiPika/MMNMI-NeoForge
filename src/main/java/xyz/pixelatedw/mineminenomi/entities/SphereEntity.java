package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

import javax.annotation.Nullable;

public class SphereEntity extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(SphereEntity.class, EntityDataSerializers.FLOAT);
    
    @Nullable
    private LivingEntity owner;
    private int lifeTicks = 0;
    private int maxLife = -1;

    public SphereEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public SphereEntity(Level level) {
        this(ModEntities.SPHERE.get(), level);
    }

    public SphereEntity(Level level, LivingEntity owner) {
        this(ModEntities.SPHERE.get(), level);
        this.owner = owner;
        this.setPos(owner.getX(), owner.getY(), owner.getZ());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RADIUS, 1.0F);
    }

    public void setRadius(float radius) {
        this.entityData.set(RADIUS, radius);
    }

    public float getRadius() {
        return this.entityData.get(RADIUS);
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide) {
            if (this.owner != null && !this.owner.isAlive()) {
                this.discard();
                return;
            }

            if (this.maxLife != -1) {
                this.lifeTicks++;
                if (this.lifeTicks >= this.maxLife) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setRadius(tag.getFloat("Radius"));
        this.maxLife = tag.getInt("MaxLife");
        this.lifeTicks = tag.getInt("LifeTicks");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Radius", this.getRadius());
        tag.putInt("MaxLife", this.maxLife);
        tag.putInt("LifeTicks", this.lifeTicks);
    }
}
