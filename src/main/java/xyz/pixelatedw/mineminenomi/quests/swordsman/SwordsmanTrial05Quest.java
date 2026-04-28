package xyz.pixelatedw.mineminenomi.quests.swordsman;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class SwordsmanTrial05Quest extends Quest {
    public static final String ID = "trial_hiryu_kaen";
    public static final QuestId<SwordsmanTrial05Quest> INSTANCE = new QuestId.Builder<>(SwordsmanTrial05Quest::new)
            .addRequirements(null)
            .build();
    
    private static final Component O1_TITLE = Component.literal("Kill 30 enemies while they're on fire");
    private static final Component O2_TITLE = Component.literal("Kill 10 enemies while you're on fire");

    private Objective objective01;
    private Objective objective02;

    public SwordsmanTrial05Quest(QuestId<SwordsmanTrial05Quest> id) {
        super(id);
        this.objective01 = new KillEntityObjective(this, O1_TITLE, 30, SharedKillChecks.ON_FIRE_ENEMY_CHECK);
        this.objective02 = new KillEntityObjective(this, O2_TITLE, 10, SharedKillChecks.ON_FIRE_PLAYER_CHECK);
        
        this.addObjectives(this.objective01, this.objective02);
        this.addTurnInEvent(100, this::giveReward);
    }

    public void giveReward(Player player) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        // In 1.21.1, adding enchantments to books might need a different helper or component
        player.addItem(stack);
        
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            stats.grantAbility(xyz.pixelatedw.mineminenomi.init.ModAbilities.HIRYU_KAEN.get().getAbilityId());
            stats.sync(player);
        }
    }
}
