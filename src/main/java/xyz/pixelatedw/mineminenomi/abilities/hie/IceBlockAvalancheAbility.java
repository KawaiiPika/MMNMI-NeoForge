package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class IceBlockAvalancheAbility extends Ability {
    private static final int CHARGE_TICKS = 100; // 5 seconds

    public IceBlockAvalancheAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 0.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 5 == 0) {
                 for (int i = 0; i < 5; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        entity.getY() + 3 + entity.getRandom().nextDouble() * 2, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        0, -0.1, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                HitResult mop = entity.pick(64.0D, 0.0F, false);
                Vec3 hitPos = mop.getType() == HitResult.Type.MISS ? entity.getEyePosition().add(entity.getLookAngle().scale(64.0D)) : mop.getLocation();
                
                net.minecraft.world.entity.item.FallingBlockEntity fallingBlock = net.minecraft.world.entity.item.FallingBlockEntity.fall(
                    entity.level(), 
                    net.minecraft.core.BlockPos.containing(hitPos.x, hitPos.y + 20, hitPos.z), 
                    net.minecraft.world.level.block.Blocks.BLUE_ICE.defaultBlockState()
                );
                fallingBlock.dropItem = false;
                entity.level().addFreshEntity(fallingBlock);
                
                entity.level().playSound(null, hitPos.x, hitPos.y, hitPos.z, 
                    net.minecraft.sounds.SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
                
                this.startCooldown(entity, 360);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ice_block_avalanche");
    }
}
