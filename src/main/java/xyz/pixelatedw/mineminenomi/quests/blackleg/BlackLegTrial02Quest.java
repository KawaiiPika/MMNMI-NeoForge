package xyz.pixelatedw.mineminenomi.quests.blackleg;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BlackLegTrial02Quest extends Quest {
    
    public BlackLegTrial02Quest(QuestId<BlackLegTrial02Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 30 enemies using kicks"), 30, (player, victim, source) -> player.getMainHandItem().isEmpty()));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Party-Table Kick Course and Black Leg Damage Perk!"));
            }
        });
    }
}
