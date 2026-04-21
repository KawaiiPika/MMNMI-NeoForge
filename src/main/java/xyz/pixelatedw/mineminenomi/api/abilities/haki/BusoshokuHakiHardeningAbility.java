package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BusoshokuHakiHardeningAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setBusoshokuActive(true);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_hardening.on"), true);
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
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_hardening.off"), true);
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null && stats.isBusoshokuActive()) {
            if (entity.level().isClientSide && duration % 5 == 0) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 1.0, 
                    entity.getY() + entity.getRandom().nextDouble() * 2.0, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 1.0, 
                    0, 0, 0);
            }
            
            // Stamina drain is handled globally in HakiHelper.tickHaki, so we just do visual here.
        }
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.busoshoku_haki_hardening");
    }
}
