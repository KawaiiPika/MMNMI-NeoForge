package xyz.pixelatedw.mineminenomi.quests.swordsman;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.QuestHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class SwordsmanTrial01Quest extends Quest {
   public static final String ID = "trial_shi_shishi_sonson";
   public static final QuestId<SwordsmanTrial01Quest> INSTANCE = (new QuestId.Builder<SwordsmanTrial01Quest>(SwordsmanTrial01Quest::new)).build();
   private static final int O1_DAMAGE_CHECK = 7;
   private static final int O2_COUNT = 30;
   private static final int O3_COUNT = 20;
   private static final Predicate<ItemStack> O1_CHECK = (itemStack) -> ItemsHelper.isSword(itemStack) && ItemsHelper.getItemDamage(itemStack) > 7.0F;
   private static final Predicate<ItemStack> O2_CHECK = (itemStack) -> itemStack.m_41720_().equals(Items.f_42500_);
   private static final Component O1_TITLE = ModRegistry.registerObjectiveTitle("trial_shi_shishi_sonson", 0, "Obtain a sword with over %s damage", 7);
   private static final Component O2_TITLE = ModRegistry.registerObjectiveTitle("trial_shi_shishi_sonson", 1, "Collect %s bones", 30);
   private static final Component O3_TITLE = ModRegistry.registerObjectiveTitle("trial_shi_shishi_sonson", 2, "Kill %s enemies using a sword", 20);
   private Objective objective01;
   private Objective objective02;
   private Objective objective03;

   public SwordsmanTrial01Quest(QuestId<SwordsmanTrial01Quest> core) {
      super(core);
      this.objective01 = new CollectItemObjective(this, O1_TITLE, 1, O1_CHECK);
      this.objective02 = (new CollectItemObjective(this, O2_TITLE, 30, O2_CHECK)).addRequirement(this.objective01);
      this.objective03 = (new KillEntityObjective(this, O3_TITLE, 20, SharedKillChecks.HAS_SWORD)).addRequirement(this.objective01);
      this.addObjectives(new Objective[]{this.objective01, this.objective02, this.objective03});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      if (QuestHelper.removeQuestItem(player, Items.f_42500_, 30)) {
         AbilityCapability.get(player).ifPresent((props) -> props.addUnlockedAbility((AbilityCore)ShiShishiSonsonAbility.INSTANCE.get(), AbilityUnlock.PROGRESSION));
      }
   }
}
