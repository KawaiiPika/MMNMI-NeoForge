package xyz.pixelatedw.mineminenomi.quests.sniper;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class SniperTrial02Quest extends Quest {
    
    public SniperTrial02Quest(QuestId<SniperTrial02Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 10 enemies from long range"), 10, (player, victim, source) -> {
            if (source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)) {
                double distanceSqr = player.distanceToSqr(victim);
                return distanceSqr >= 900.0;
            }
            return false;
        }));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Sniper Accuracy Perk and Tetsu Boshi!"));
            }
        });
    }
}
