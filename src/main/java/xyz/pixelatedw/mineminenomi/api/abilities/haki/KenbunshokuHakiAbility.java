package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KenbunshokuHakiAbility extends Ability {

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
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.kenbunshoku_haki.on"), true);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setKenbunshokuActive(false);
            stats.sync(entity);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.kenbunshoku_haki.off"), true);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kenbunshoku_haki");
    }
}
