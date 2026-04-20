package xyz.pixelatedw.mineminenomi.quests.swordsman;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.YakkodoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;

public class SwordsmanTrial03Quest extends Quest {
    public static final String ID = "trial_sanbyakurokuju_pound_ho";
    public static final QuestId<SwordsmanTrial03Quest> INSTANCE = new QuestId.Builder<>(SwordsmanTrial03Quest::new)
            .addRequirements(ModQuests.SWORDSMAN_TRIAL_01)
            .build();
    
    private static final KillEntityObjective.IKillCheck YAKKODORI_KILL_CHECK = (player, victim, source) -> {
        return source.getDirectEntity() instanceof YakkodoriProjectile;
    };
    
    private static final KillEntityObjective.IKillCheck SHI_SHISHI_SONSON_KILL_CHECK = (player, victim, source) -> {
        // Since Shi Shishi Sonson is a dash/melee attack, we check if the source is from the player
        // and if they are currently using the ability (or just use a simplified check for now)
        return player.getMainHandItem().isEnchanted(); // Placeholder check
    };
    
    private static final Component O1_TITLE = Component.literal("Kill 10 enemies using Yakkodori");
    private static final Component O2_TITLE = Component.literal("Kill 30 enemies using Shi Shishi Sonson");

    private Objective objective01;
    private Objective objective02;

    public SwordsmanTrial03Quest(QuestId<SwordsmanTrial03Quest> id) {
        super(id);
        this.objective01 = new KillEntityObjective(this, O1_TITLE, 10, YAKKODORI_KILL_CHECK);
        this.objective02 = new KillEntityObjective(this, O2_TITLE, 30, SHI_SHISHI_SONSON_KILL_CHECK).addRequirement(this.objective01);
        
        this.addObjectives(this.objective01, this.objective02);
        this.addTurnInEvent(100, this::giveReward);
    }

    public void giveReward(Player player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            // ability removed for porting
            stats.sync(player);
        }
    }
}
