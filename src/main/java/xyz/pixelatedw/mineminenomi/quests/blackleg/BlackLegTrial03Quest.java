package xyz.pixelatedw.mineminenomi.quests.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class BlackLegTrial03Quest extends Quest {
    
    public BlackLegTrial03Quest(QuestId<BlackLegTrial03Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 50 enemies using kicks"), 50, (player, victim, source) -> player.getMainHandItem().isEmpty()));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Extra Hachis!"));
            }
        });
    }
}
