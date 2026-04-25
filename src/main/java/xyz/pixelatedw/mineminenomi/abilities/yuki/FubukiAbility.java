package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FubukiAbility extends Ability {

    private static final int CHARGE_TICKS = 100;

    public FubukiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration >= CHARGE_TICKS) {
                this.stop(entity);
                this.startCooldown(entity, 400);
                return;
            }

            if (duration % 20 == 0) {
                int multiplier = (int) (duration / 20) + 1;
                int currentRange = 5 * multiplier;
                float currentDamage = 4.0F * multiplier;

                // Emulate placing snow layers using heightmap
                BlockPos center = entity.blockPosition();
                for (int x = -currentRange; x <= currentRange; x++) {
                    for (int z = -currentRange; z <= currentRange; z++) {
                        if (x * x + z * z <= currentRange * currentRange) {
                            if (entity.getRandom().nextFloat() < 0.2F) {
                                BlockPos surfacePos = entity.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, center.offset(x, 0, z));
                                if (entity.level().isEmptyBlock(surfacePos) && Blocks.SNOW.defaultBlockState().canSurvive(entity.level(), surfacePos)) {
                                    entity.level().setBlockAndUpdate(surfacePos, Blocks.SNOW.defaultBlockState());
                                }
                            }
                        }
                    }
                }

                entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(currentRange))
                    .stream()
                    .filter(target -> target != entity)
                    .filter(target -> target.distanceToSqr(entity) <= currentRange * currentRange)
                    .forEach(target -> {
                        // Apply effect and damage
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                        target.hurt(entity.damageSources().freeze(), currentDamage);
                    });
            }
        } else {
             if (duration < CHARGE_TICKS) {
                 if (duration % 5 == 0) {
                     int multiplier = (int) (duration / 20) + 1;
                     int currentRange = 5 * multiplier;
                     for(int i = 0; i < 10 * multiplier; i++) {
                         entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.ITEM_SNOWBALL,
                            entity.getX() + (entity.getRandom().nextDouble() - 0.5) * currentRange * 2,
                            entity.getY() + entity.getRandom().nextDouble() * currentRange,
                            entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * currentRange * 2,
                            0, 0, 0);
                     }
                 }
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fubuki");
    }
}
