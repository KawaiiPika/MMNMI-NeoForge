package xyz.pixelatedw.mineminenomi.abilities.yomi;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.passives.StatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import java.util.function.Predicate;

public class YomiPassiveAbility extends StatBonusAbility {

    public YomiPassiveAbility() {
        super(ModFruits.YOMI_YOMI_NO_MI.getId());
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return entity -> true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yomi_passives");
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (!entity.level().isClientSide) {
            handleWaterRunning(entity);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                if (player.getFoodData().getFoodLevel() <= 18) {
                    player.getFoodData().setFoodLevel(18);
                }
            }
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 10, entity.isInWater() ? 4 : 0, false, false));
        }
    }

    private void handleWaterRunning(LivingEntity entity) {
        if (!(entity.fallDistance <= 0.0F)) {
            BlockState state = entity.level().getBlockState(entity.blockPosition().below());
            if (!state.getFluidState().isEmpty() && entity.isInWater()) {
                Vec3 moveVec = entity.getDeltaMovement().multiply(0.5F, 0.0F, 0.5F);
                xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.setDeltaMovement(entity, moveVec.x, moveVec.y, moveVec.z);
                entity.fallDistance = 0.0F;

                if (entity.level() instanceof ServerLevel serverLevel) {
                    for(int i = 0; i < 32; ++i) {
                        double newPosX = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 0.5D;
                        double newPosY = entity.getY() + (entity.getRandom().nextDouble() - 0.5D) * 0.5D;
                        double newPosZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 0.5D;
                        serverLevel.sendParticles(ParticleTypes.SPLASH, newPosX, newPosY, newPosZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    @Override
    public boolean onIncomingDamageCheckInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }
}
