package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public abstract class AbilityProjectile extends ThrowableItemProjectile {
    protected float damage = 0;
    protected int life = 0;
    protected int maxLife = 100;

    public AbilityProjectile(EntityType<? extends AbilityProjectile> type, Level world) {
        super(type, world);
    }

    public AbilityProjectile(EntityType<? extends AbilityProjectile> type, LivingEntity owner, Level world) {
        super(type, owner, world);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (life > maxLife) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
            target.hurt(this.damageSources().mobAttack(owner), this.damage);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
