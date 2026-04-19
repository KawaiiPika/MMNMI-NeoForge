package xyz.pixelatedw.mineminenomi.quests.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class SniperTrial03Quest extends Quest {
    
    public SniperTrial03Quest(QuestId<SniperTrial03Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 50 enemies using projectiles"), 50, (player, victim, source) -> source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.NEMURI_BOSHI.get().getAbilityId());
                stats.grantAbility(ModAbilities.RENPATSU_NAMARI_BOSHI.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Nemuri Boshi and Renpatsu Namari Boshi!"));
            }
        });
    }
}
