package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class IceBallAbility extends Ability {

    public IceBallAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            Vec3 startPos = entity.getEyePosition();
            
            for (double i = 0; i < 32.0; i += 0.5) {
                double x = startPos.x + look.x * i;
                double y = startPos.y + look.y * i;
                double z = startPos.z + look.z * i;
                
                for (LivingEntity livingTarget : entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5))) {
                    if (livingTarget != entity) {
                        livingTarget.hurt(entity.damageSources().indirectMagic(entity, entity), 10.0F);
                        livingTarget.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 100, 5));
                        
                        // Place ice blocks around target
                        net.minecraft.core.BlockPos targetPos = livingTarget.blockPosition();
                        for (int ox = -1; ox <= 1; ox++) {
                            for (int oy = 0; oy <= 2; oy++) {
                                for (int oz = -1; oz <= 1; oz++) {
                                    net.minecraft.core.BlockPos p = targetPos.offset(ox, oy, oz);
                                    if (entity.level().isEmptyBlock(p)) {
                                        entity.level().setBlockAndUpdate(p, net.minecraft.world.level.block.Blocks.BLUE_ICE.defaultBlockState());
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.GLASS_BREAK, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 0.5F);
            
            this.startCooldown(entity, 200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ice_ball");
    }
}
