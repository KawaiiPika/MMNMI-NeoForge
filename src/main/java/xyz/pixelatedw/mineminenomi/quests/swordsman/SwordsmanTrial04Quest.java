package xyz.pixelatedw.mineminenomi.quests.swordsman;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedKillEntityObjective;

public class SwordsmanTrial04Quest extends Quest {
    public static final String ID = "trial_tatsu_maki";
    public static final QuestId<SwordsmanTrial04Quest> INSTANCE = new QuestId.Builder<>(SwordsmanTrial04Quest::new)
            .addRequirements(null)
            .build();
    
    private static final Component O1_TITLE = Component.literal("Damage 20 enemies with sweeping attacks");
    private static final Component O2_TITLE = Component.literal("Kill 3 enemies in less than 5 seconds using a sword");
    private static final Component O3_TITLE = Component.literal("Kill 5 airborne enemies using a sword");

    private Objective objective01;
    private Objective objective02;
    private Objective objective03;

    public SwordsmanTrial04Quest(QuestId<SwordsmanTrial04Quest> id) {
        super(id);
        this.objective01 = new HitEntityObjective(this, O1_TITLE, 20);
        this.objective02 = new TimedKillEntityObjective(this, O2_TITLE, 3, 5, SharedKillChecks.HAS_SWORD);
        this.objective03 = new KillEntityObjective(this, O3_TITLE, 5, SharedKillChecks.AIRBORNE_ENEMY_CHECK.and(SharedKillChecks.HAS_SWORD));
        
        this.addObjectives(this.objective01, this.objective02, this.objective03);
        this.addTurnInEvent(100, this::giveReward);
    }

    public void giveReward(Player player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.O_TATSUMAKI.get().getAbilityId());
            stats.sync(player);
        }
    }
}
