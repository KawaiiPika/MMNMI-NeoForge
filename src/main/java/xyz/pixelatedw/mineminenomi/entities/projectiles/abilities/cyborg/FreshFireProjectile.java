package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class FreshFireProjectile extends AbilityProjectile {

    public FreshFireProjectile(EntityType<? extends FreshFireProjectile> type, Level world) {
        super(type, world);
    }

    public FreshFireProjectile(Level world, LivingEntity owner) {
        super(ModEntities.FRESH_FIRE.get(), owner, world);
        this.setDamage(1.0F);
        this.setMaxLife(15);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide() && result.getEntity() != null) {
            result.getEntity().igniteForSeconds(4.0F);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            BlockPos above = result.getBlockPos().above();
            if (this.level().getBlockState(above).isAir()) {
                this.level().setBlockAndUpdate(above, Blocks.FIRE.defaultBlockState());
            }
        }
    }
}
