package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KenbunshokuHakiAuraAbility extends Ability {

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isKenbunshokuActive();
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setKenbunshokuActive(true);
            stats.sync(entity);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), xyz.pixelatedw.mineminenomi.init.ModSounds.KENBUNSHOKU_HAKI_ON_SFX.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.kenbunshoku_haki_aura.on"), true);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setKenbunshokuActive(false);
            stats.sync(entity);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), xyz.pixelatedw.mineminenomi.init.ModSounds.KENBUNSHOKU_HAKI_OFF.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.kenbunshoku_haki_aura.off"), true);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kenbunshoku_haki_aura");
    }
}
