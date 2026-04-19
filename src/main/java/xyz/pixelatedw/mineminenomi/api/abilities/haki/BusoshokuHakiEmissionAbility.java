package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BusoshokuHakiEmissionAbility extends Ability {

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isBusoshokuActive();
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setBusoshokuActive(true);
            stats.sync(entity);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_emission.on"), true);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setBusoshokuActive(false);
            stats.sync(entity);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_emission.off"), true);
            }
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        // Emission adds extra knockback and particle burst
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SONIC_BOOM, 
                target.getX(), target.getY() + 1.0, target.getZ(), 
                1, 0, 0, 0, 0);
        }
        target.push(entity.getX() - target.getX() > 0 ? -1.0 : 1.0, 0.5, entity.getZ() - target.getZ() > 0 ? -1.0 : 1.0);
        return amount * 1.2F; // Small flat damage bonus for emission
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.busoshoku_haki_emission");
    }
}
