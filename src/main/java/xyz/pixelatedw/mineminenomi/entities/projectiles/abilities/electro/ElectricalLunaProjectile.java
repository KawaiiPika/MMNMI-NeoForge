package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

import java.util.List;

public class ElectricalLunaProjectile extends AbilityProjectile {

    public ElectricalLunaProjectile(EntityType<? extends ElectricalLunaProjectile> type, Level world) {
        super(type, world);
    }

    public ElectricalLunaProjectile(Level world, LivingEntity owner) {
        super(ModEntities.ELECTRICAL_LUNA.get(), owner, world);
        this.setDamage(40.0F);
        this.setMaxLife(20);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide() && this.getOwner() instanceof LivingEntity owner) {
            float range = 6.0F;
            List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(range), e -> e != owner && e.isAlive());
            for (LivingEntity target : targets) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 2, false, false, true));
            }

            for (int j = 0; j < 16; j++) {
                float boltSize = 3 + this.random.nextFloat() * (range - 3);
                ElectroVisualProjectile bolt = new ElectroVisualProjectile(this.level(), owner);
                bolt.setPos(this.position());
                this.level().addFreshEntity(bolt);
            }
        }
    }
}
