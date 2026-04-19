package xyz.pixelatedw.mineminenomi.quests.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class WeatherTrial01Quest extends Quest {
    
    public WeatherTrial01Quest(QuestId<WeatherTrial01Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 20 enemies using weather balls"), 20, (player, victim, source) -> source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.HEAT_BALL.get().getAbilityId());
                stats.grantAbility(ModAbilities.COOL_BALL.get().getAbilityId());
                stats.grantAbility(ModAbilities.THUNDER_BALL.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Heat Ball, Cool Ball, and Thunder Ball!"));
            }
        });
    }
}
