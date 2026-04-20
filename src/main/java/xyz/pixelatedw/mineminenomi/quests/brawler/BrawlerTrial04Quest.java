package xyz.pixelatedw.mineminenomi.quests.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class BrawlerTrial04Quest extends Quest {
    
    public BrawlerTrial04Quest(QuestId<BrawlerTrial04Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 75 enemies using fists"), 75, (player, victim, source) -> player.getMainHandItem().isEmpty()));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Jishin Ho and Genkotsu Meteor!"));
            }
        });
    }
}
