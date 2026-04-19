package xyz.pixelatedw.mineminenomi.quests.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class SniperTrial05Quest extends Quest {
    
    public SniperTrial05Quest(QuestId<SniperTrial05Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective(this, Component.literal("Kill 100 enemies using projectiles"), 100, (player, victim, source) -> source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.HI_NO_TORI_BOSHI.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: Hi No Tori Boshi!"));
            }
        });
    }
}
