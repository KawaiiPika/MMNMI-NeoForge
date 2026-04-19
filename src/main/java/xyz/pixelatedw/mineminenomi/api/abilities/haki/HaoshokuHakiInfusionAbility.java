package xyz.pixelatedw.mineminenomi.api.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class HaoshokuHakiInfusionAbility extends Ability {
    
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
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isBusoshokuActive(); // Haoshoku Infusion often uses Busoshoku as base
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setBusoshokuActive(true);
            stats.sync(entity);
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.haoshoku_haki_infusion.on"), true);
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
                player.displayClientMessage(Component.translatable("ability.mineminenomi.haoshoku_haki_infusion.off"), true);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.haoshoku_haki_infusion");
    }
}
