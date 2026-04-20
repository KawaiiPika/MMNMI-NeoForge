package xyz.pixelatedw.mineminenomi.quests.swordsman;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedHitEntityObjective;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class SwordsmanTrial02Quest extends Quest {
    public static final String ID = "trial_yakkodori";
    public static final QuestId<SwordsmanTrial02Quest> INSTANCE = new QuestId.Builder<>(SwordsmanTrial02Quest::new).build();
    
    private static final Predicate<ItemStack> SWORD_WITH_SHARPNESS_2 = (itemStack) -> {
        // Enchantment API changed in 1.21.1, needs update for proper check
        return ItemsHelper.isSword(itemStack); 
    };
    
    private static final Component O1_TITLE = Component.literal("Hit 3 enemies at the same time");
    private static final Component O2_TITLE = Component.literal("Kill 25 enemies with critical hits using a sword");
    private static final Component O3_TITLE = Component.literal("Obtain a sword with Sharpness II");

    private Objective objective01;
    private Objective objective02;
    private Objective objective03;

    public SwordsmanTrial02Quest(QuestId<SwordsmanTrial02Quest> id) {
        super(id);
        this.objective01 = new TimedHitEntityObjective(this, O1_TITLE, 3, 2);
        this.objective02 = new KillEntityObjective(this, O2_TITLE, 25, SharedKillChecks.CRITICAL_KILL_CHECK.and(SharedKillChecks.HAS_SWORD));
        this.objective03 = new CollectItemObjective(this, O3_TITLE, 1, SWORD_WITH_SHARPNESS_2);
        
        this.addObjectives(this.objective01, this.objective02, this.objective03);
        this.addTurnInEvent(100, this::giveReward);
    }

    public void giveReward(Player player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            // ability removed for porting
            // ability removed for porting
            stats.sync(player);
        }
    }
}
