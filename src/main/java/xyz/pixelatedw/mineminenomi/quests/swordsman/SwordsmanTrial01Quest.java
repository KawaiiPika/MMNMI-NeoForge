package xyz.pixelatedw.mineminenomi.quests.swordsman;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.QuestHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class SwordsmanTrial01Quest extends Quest {
    public static final String ID = "trial_shi_shishi_sonson";
    public static final QuestId<SwordsmanTrial01Quest> INSTANCE = new QuestId.Builder<>(SwordsmanTrial01Quest::new).build();
    
    private static final Predicate<ItemStack> O1_CHECK = (itemStack) -> ItemsHelper.isSword(itemStack) && ItemsHelper.getItemDamage(itemStack) > 7.0F;
    private static final Predicate<ItemStack> O2_CHECK = (itemStack) -> itemStack.is(Items.BONE);
    
    private static final Component O1_TITLE = Component.literal("Obtain a sword with over 7 damage");
    private static final Component O2_TITLE = Component.literal("Collect 30 bones");
    private static final Component O3_TITLE = Component.literal("Kill 20 enemies using a sword");

    private Objective objective01;
    private Objective objective02;
    private Objective objective03;

    public SwordsmanTrial01Quest(QuestId<SwordsmanTrial01Quest> core) {
        super(core);
        this.objective01 = new CollectItemObjective(this, O1_TITLE, 1, O1_CHECK);
        this.objective02 = new CollectItemObjective(this, O2_TITLE, 30, O2_CHECK).addRequirement(this.objective01);
        this.objective03 = new KillEntityObjective(this, O3_TITLE, 20, SharedKillChecks.HAS_SWORD).addRequirement(this.objective01);
        
        this.addObjectives(this.objective01, this.objective02, this.objective03);
        this.addTurnInEvent(100, this::giveReward);
    }

    public void giveReward(Player player) {
        if (QuestHelper.removeQuestItem(player, Items.BONE, 30)) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // ability removed for porting
                stats.sync(player);
            }
        }
    }
}
