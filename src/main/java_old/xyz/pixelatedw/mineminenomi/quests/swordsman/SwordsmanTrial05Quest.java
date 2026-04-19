package xyz.pixelatedw.mineminenomi.quests.swordsman;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class SwordsmanTrial05Quest extends Quest {
   public static final String ID = "trial_hiryu_kaen";
   public static final QuestId<SwordsmanTrial05Quest> INSTANCE;
   private static final int O1_COUNT = 30;
   private static final int O2_COUNT = 10;
   private static final Component O1_TITLE;
   private static final Component O2_TITLE;
   private Objective objective01;
   private Objective objective02;

   public SwordsmanTrial05Quest(QuestId id) {
      super(id);
      this.objective01 = new KillEntityObjective(this, O1_TITLE, 30, SharedKillChecks.ON_FIRE_ENEMY_CHECK);
      this.objective02 = new KillEntityObjective(this, O2_TITLE, 10, SharedKillChecks.ON_FIRE_PLAYER_CHECK);
      this.addObjectives(new Objective[]{this.objective01, this.objective02});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      ItemStack stack = new ItemStack(Items.f_42690_);
      EnchantedBookItem.m_41153_(stack, new EnchantmentInstance(Enchantments.f_44981_, 2));
      player.m_36356_(stack);
      AbilityCapability.get(player).ifPresent((props) -> props.addUnlockedAbility((AbilityCore)HiryuKaenAbility.INSTANCE.get(), AbilityUnlock.PROGRESSION));
   }

   static {
      INSTANCE = (new QuestId.Builder<SwordsmanTrial05Quest>(SwordsmanTrial05Quest::new)).addRequirements(ModQuests.SWORDSMAN_TRIAL_04).build();
      O1_TITLE = ModRegistry.registerObjectiveTitle("trial_hiryu_kaen", 0, "Kill %s enemies while they're on fire", 30);
      O2_TITLE = ModRegistry.registerObjectiveTitle("trial_hiryu_kaen", 1, "Kill %s enemies while you're on fire", 10);
   }
}
