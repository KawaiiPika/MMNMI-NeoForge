package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class GomuBazookaEntity extends ThrowableItemProjectile {

    public GomuBazookaEntity(EntityType<? extends GomuBazookaEntity> type, Level level) {
        super(type, level);
    }

    public GomuBazookaEntity(Level level, LivingEntity owner) {
        super(ModEntities.GOMU_BAZOOKA.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR; // Rendered via custom model
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 15.0F);
                // Huge knockback
                net.minecraft.world.phys.Vec3 knockback = this.getDeltaMovement().normalize().scale(3.0);
                target.setDeltaMovement(target.getDeltaMovement().add(knockback));
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
