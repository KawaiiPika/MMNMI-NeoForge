package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BusoshokuHakiInternalDestructionAbility extends Ability {

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
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_internal_destruction.on"), true);
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
                player.displayClientMessage(Component.translatable("ability.mineminenomi.busoshoku_haki_internal_destruction.off"), true);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.busoshoku_haki_internal_destruction");
    }
}
