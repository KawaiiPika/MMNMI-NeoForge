package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

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
            // Logic: Knock out "weak" enemies using HakiHelper
            if (xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper.isWeakenedByHaoshoku(entity, target)) {
                target.addEffect(new MobEffectInstance(xyz.pixelatedw.mineminenomi.init.ModEffects.UNCONSCIOUS, 200, 0));
                
                // Knockback effect
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

        // Visual effects on server to sync to all clients
        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);
            serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 0.5f);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.haoshoku_haki");
    }
}
