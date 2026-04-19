package xyz.pixelatedw.mineminenomi.quests.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class DoctorTrial05Quest extends Quest {
    
    public DoctorTrial05Quest(QuestId<DoctorTrial05Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.api.quests.objectives.HealObjective("Restore 1000 health points", 1000));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                stats.grantAbility(ModAbilities.VIRUS_ZONE.get().getAbilityId());
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have reached medical mastery and unlocked: Virus Zone!"));
            }
        });
    }
}
