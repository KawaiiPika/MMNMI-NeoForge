package xyz.pixelatedw.mineminenomi.quests.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class BlackLegTrial05Quest extends Quest {
    
    public BlackLegTrial05Quest(QuestId<BlackLegTrial05Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 100 enemies using kicks"), 100, (player, victim, source) -> player.getMainHandItem().isEmpty()));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.CONCASSE.get().getAbilityId());
                stats.grantAbility(ModAbilities.BIEN_CUIT_GRILL_SHOT.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Concasse and Bien Cuit: Grill Shot!"));
            }
        });
    }
}
