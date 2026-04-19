package xyz.pixelatedw.mineminenomi.quests.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class WeatherTrial03Quest extends Quest {
    
    public WeatherTrial03Quest(QuestId<WeatherTrial03Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Defeat 50 enemies"), 50, (player, victim, source) -> true));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.MIRAGE_TEMPO.get().getAbilityId());
                stats.grantAbility(ModAbilities.CYCLONE_TEMPO.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Mirage Tempo and Cyclone Tempo!"));
            }
        });
    }
}
