package xyz.pixelatedw.mineminenomi.quests.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class BlackLegTrial04Quest extends Quest {
    
    public BlackLegTrial04Quest(QuestId<BlackLegTrial04Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 75 enemies using kicks"), 75, (player, victim, source) -> player.getMainHandItem().isEmpty()));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.DIABLE_JAMBE.get().getAbilityId());
                stats.grantAbility(ModAbilities.SKYWALK.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Diable Jambe and Skywalk!"));
            }
        });
    }
}
