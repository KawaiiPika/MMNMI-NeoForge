package xyz.pixelatedw.mineminenomi.quests.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class SniperTrial04Quest extends Quest {
    
    public SniperTrial04Quest(QuestId<SniperTrial04Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 5 Endermen or Ghasts"), 5, (player, victim, source) -> victim instanceof EnderMan || victim instanceof Ghast));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Zoom Perk and Tokuyo Abura Boshi!"));
            }
        });
    }
}
