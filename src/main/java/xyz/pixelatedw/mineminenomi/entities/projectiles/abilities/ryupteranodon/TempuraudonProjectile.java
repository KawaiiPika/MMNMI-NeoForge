package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import net.minecraft.world.level.Explosion;

public class TempuraudonProjectile extends net.minecraft.world.entity.projectile.ThrowableItemProjectile {

    public TempuraudonProjectile(EntityType<? extends TempuraudonProjectile> type, Level level) {
        super(type, level);
    }

    public TempuraudonProjectile(Level level, LivingEntity owner) {
        super(ModEntities.TEMPURAUDON.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 15.0F);
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(
                this.getOwner(),
                result.getLocation().x, result.getLocation().y, result.getLocation().z,
                3.0F,
                false,
                Level.ExplosionInteraction.BLOCK
            );
            this.discard();
        }
    }
}
