package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

import javax.annotation.Nullable;

public class ThrownSpearEntity extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BOOLEAN);
    
    private ItemStack itemStack = new ItemStack(Items.AIR);
    private float attackDamage;
    private boolean dealtDamage;
    public int clientSideReturnTickCount;

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level world) {
        super(type, world);
    }

    public ThrownSpearEntity(LivingEntity thrower, Level world, ItemStack stack) {
        super(ModEntities.THROWN_SPEAR.get(), thrower, world, stack, null);
        this.itemStack = stack.copy();
        // TODO: Handle Loyalty and Foil properly in 1.21.1
        this.entityData.set(ID_LOYALTY, (byte) 0);
        this.entityData.set(ID_FOIL, stack.isEnchanted());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_LOYALTY, (byte) 0);
        builder.define(ID_FOIL, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (loyalty > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double) loyalty, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05 * (double) loyalty;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnTickCount == 0) {
                    this.playSound(net.minecraft.sounds.SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity owner = this.getOwner();
        if (owner != null && owner.isAlive()) {
            return !(owner instanceof ServerPlayer) || !owner.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return this.itemStack.copy();
    }

    public void setAttackDamage(float damage) {
        this.attackDamage = damage;
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 start, Vec3 end) {
        return this.dealtDamage ? null : super.findHitEntity(start, end);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        float damage = this.attackDamage;
        if (target instanceof LivingEntity livingTarget) {
            // damage += EnchantmentHelper.getDamageBonus(this.itemStack, livingTarget.getType()); // API changed in 1.21.1
        }

        Entity owner = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (owner == null ? this : owner));
        this.dealtDamage = true;
        SoundEvent soundevent = net.minecraft.sounds.SoundEvents.TRIDENT_HIT;
        if (target.hurt(damagesource, damage)) {
            if (target instanceof LivingEntity livingTarget) {
                if (owner instanceof LivingEntity livingOwner && this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    EnchantmentHelper.doPostAttackEffects(serverLevel, livingTarget, damagesource);
                }
                this.doPostHurtEffects(livingTarget);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return net.minecraft.sounds.SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.getOwner() == null || this.getOwner().getUUID().equals(player.getUUID())) {
            super.playerTouch(player);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Item", 10)) {
            this.itemStack = ItemStack.parseOptional(this.registryAccess(), tag.getCompound("Item"));
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
        // TODO: Re-calc loyalty
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Item", this.itemStack.save(this.registryAccess()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
