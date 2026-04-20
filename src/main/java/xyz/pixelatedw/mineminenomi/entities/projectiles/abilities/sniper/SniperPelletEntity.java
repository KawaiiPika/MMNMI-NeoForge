package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SniperPelletEntity extends ThrowableItemProjectile {

    private float damage = 5.0f;
    private int fireTicks = 0;
    private boolean explosive = false;
    private MobEffectInstance effect = null;

    public SniperPelletEntity(EntityType<? extends SniperPelletEntity> type, Level level) {
        super(type, level);
    }

    public SniperPelletEntity(Level level, LivingEntity owner) {
        super(null, owner, level); // Need to register EntityType
    }

    public void setDamage(float damage) { this.damage = damage; }
    public void setFireTicks(int ticks) { this.fireTicks = ticks; }
    public void setExplosive(boolean explosive) { this.explosive = explosive; }
    public void setEffect(MobEffectInstance effect) { this.effect = effect; }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), this.damage);
            if (this.fireTicks > 0) {
                target.setRemainingFireTicks(this.fireTicks);
            }
            if (this.effect != null) {
                target.addEffect(new MobEffectInstance(this.effect));
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.explosive) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0f, Level.ExplosionInteraction.MOB);
        }
        this.discard();
    }
}
