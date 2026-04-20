package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class HaoshokuHakiAbility extends Ability {
    
    @Override
    public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper.isHaoshokuBorn(player)) {
                return xyz.pixelatedw.mineminenomi.api.util.Result.success();
            } else {
                return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.mineminenomi.haoshoku_haki.not_born"));
            }
        }
        return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.error.not_player"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level().isClientSide()) {
            if (entity instanceof Player player) {
                xyz.pixelatedw.mineminenomi.client.gui.overlays.HaoshokuOverlay.INSTANCE.trigger();
                player.displayClientMessage(Component.translatable("ability.mineminenomi.haoshoku_haki.on"), true);
            }
            return;
        }

        double radius = 15.0;
        AABB aabb = entity.getBoundingBox().inflate(radius);
        entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity).forEach(target -> {
            if (xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper.isWeakenedByHaoshoku(entity, target)) {
                target.addEffect(new MobEffectInstance(xyz.pixelatedw.mineminenomi.init.ModEffects.UNCONSCIOUS, 200, 0));
                
                double dx = target.getX() - entity.getX();
                double dz = target.getZ() - entity.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > 0) {
                    target.setDeltaMovement(dx / dist * 1.5, 0.2, dz / dist * 1.5);
                    target.hurtMarked = true;
                }
                
                if (target instanceof Mob mob) {
                    mob.setTarget(null);
                    mob.getNavigation().stop();
                }
            }
        });

        if (entity.level() instanceof ServerLevel serverLevel) {
            int size = 15; // Default burst size
            int color = 16711680; // Red

            double posX = entity.getX();
            double posY = entity.getY();
            double posZ = entity.getZ();
            BlockPos ogPos = new BlockPos((int)posX, (int)posY, (int)posZ);

            // Block particles
            for(double x = (double)(-size); x < (double)size; ++x) {
               for(double z = (double)(-size); z < (double)size; ++z) {
                  BlockPos pos = new BlockPos((int)(posX + x), (int)posY, (int)(posZ + z));
                  if (ogPos.closerToCenterThan(new Vec3(pos.getX(), pos.getY(), pos.getZ()), (double)size) && ogPos.hashCode() % 20 >= 5) {
                     BlockState blockState = serverLevel.getBlockState(pos.below());
                     if (!blockState.isAir()) {
                        for(int i = 0; i < 10; ++i) {
                           serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), posX + WyHelper.randomWithRange(-3, 3) + x, posY, posZ + WyHelper.randomWithRange(-3, 3) + z, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.1);
                        }
                     }
                  }
               }
            }

            // CHIYU particles wave
            Vec3 playerPos = new Vec3(posX, posY, posZ);
            double r = (double)2.0F;

            for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += 0.04908738521234052) {
               double x = r * Math.cos(phi) + WyHelper.randomDouble() / (double)5.0F;
               double z = r * Math.sin(phi) + WyHelper.randomDouble() / (double)5.0F;
               Vec3 pos = playerPos.add(new Vec3(x, 0, z));
               Vec3 dirVec = playerPos.vectorTo(pos).normalize().multiply((double)3.5F, (double)1.0F, (double)3.5F);

               java.awt.Color clientRGB = new java.awt.Color(color);
               SimpleParticleData data = new SimpleParticleData(ModParticleTypes.CHIYU.get());
               data.setLife(15);
               data.setSize(3.0F);
               data.setMotion(-dirVec.x, (double)0.0F, -dirVec.z);
               data.setColor((float)clientRGB.getRed() / 255.0F, (float)clientRGB.getGreen() / 255.0F, (float)clientRGB.getBlue() / 255.0F, 0.4F);
               serverLevel.sendParticles(data, posX + x, posY + 0.3, posZ + z, 1, 0, 0, 0, 0);

               SimpleParticleData data2 = new SimpleParticleData(ModParticleTypes.CHIYU.get());
               data2.setLife(15);
               data2.setSize(2.0F);
               data2.setMotion(-dirVec.x, (double)0.0F, -dirVec.z);
               data2.setColor(0.0F, 0.0F, 0.0F, 0.6F);
               serverLevel.sendParticles(data2, posX + x, posY + 0.3, posZ + z, 1, 0, 0, 0, 0);
            }

            serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 0.5f);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.haoshoku_haki");
    }
}
