package xyz.pixelatedw.mineminenomi.quests.doctor;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class DoctorTrial01Quest extends Quest {
    
    public DoctorTrial01Quest(QuestId<DoctorTrial01Quest> id) {
        super(id);
        this.addObjectives(new xyz.pixelatedw.mineminenomi.api.quests.objectives.HealObjective("Restore 100 health points", 100));
        this.addCompleteEvent(100, (player) -> {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                stats.sync(player);
                player.sendSystemMessage(Component.literal("You have unlocked: First Aid!"));
            }
        });
    }
}
