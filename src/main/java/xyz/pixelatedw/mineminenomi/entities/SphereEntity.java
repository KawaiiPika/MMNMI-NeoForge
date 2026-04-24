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
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import org.jspecify.annotations.Nullable;
import java.awt.Color;

public class SphereEntity extends Entity implements IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(SphereEntity.class, EntityDataSerializers.FLOAT);
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/default.png");
    

    private @Nullable LivingEntity owner;
    private int lifeTicks = 0;
    private int maxLife = -1;

    private ResourceLocation[] textures = new ResourceLocation[]{DEFAULT_TEXTURE};
    private Color color = Color.WHITE;
    private int animationSpeed = 1;
    private int detailLevel = 8;
    private boolean isFullWrapping = false;
    private boolean move = false;

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

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.textures.length);
        for (ResourceLocation texture : this.textures) {
            buffer.writeResourceLocation(texture);
        }
        buffer.writeBoolean(this.isFullWrapping);
        buffer.writeInt(this.animationSpeed);
        buffer.writeInt(this.color.getRGB());
        buffer.writeInt(this.color.getAlpha());
        buffer.writeInt(this.detailLevel);
        buffer.writeBoolean(this.move);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf buffer) {
        int texturesLength = buffer.readInt();
        this.textures = new ResourceLocation[texturesLength];
        for (int i = 0; i < texturesLength; ++i) {
            this.textures[i] = buffer.readResourceLocation();
        }
        this.isFullWrapping = buffer.readBoolean();
        this.animationSpeed = buffer.readInt();
        int rgb = buffer.readInt();
        int alpha = buffer.readInt();
        this.color = new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, alpha);
        this.detailLevel = buffer.readInt();
        this.move = buffer.readBoolean();
    }
}
