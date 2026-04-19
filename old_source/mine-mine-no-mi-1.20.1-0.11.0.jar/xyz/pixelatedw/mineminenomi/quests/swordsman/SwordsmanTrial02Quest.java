package xyz.pixelatedw.mineminenomi.quests.swordsman;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedHitEntityObjective;

public class SwordsmanTrial02Quest extends Quest {
   public static final String ID = "trial_yakkodori";
   public static final QuestId<SwordsmanTrial02Quest> INSTANCE = (new QuestId.Builder<SwordsmanTrial02Quest>(SwordsmanTrial02Quest::new)).build();
   private static final int O1_HIT_COUNT = 3;
   private static final int O2_COUNT = 25;
   private static final Predicate<ItemStack> SWORD_WITH_SHARPNESS_2 = (itemStack) -> ItemsHelper.isSword(itemStack) && itemStack.getEnchantmentLevel(Enchantments.f_44977_) > 1;
   private static final Component O1_TITLE = ModRegistry.registerObjectiveTitle("trial_yakkodori", 0, "Hit %s enemies at the same time", 3);
   private static final Component O2_TITLE = ModRegistry.registerObjectiveTitle("trial_yakkodori", 1, "Kill %s enemies with critical hits using a sword", 25);
   private static final Component O3_TITLE = ModRegistry.registerObjectiveTitle("trial_yakkodori", 2, "Obtain a sword with Sharpness II");
   private Objective objective01;
   private Objective objective02;
   private Objective objective03;

   public SwordsmanTrial02Quest(QuestId<SwordsmanTrial02Quest> id) {
      super(id);
      this.objective01 = new TimedHitEntityObjective(this, O1_TITLE, 3, 2);
      this.objective02 = new KillEntityObjective(this, O2_TITLE, 25, SharedKillChecks.CRITICAL_KILL_CHECK.and(SharedKillChecks.HAS_SWORD));
      this.objective03 = new CollectItemObjective(this, O3_TITLE, 1, SWORD_WITH_SHARPNESS_2);
      this.addObjectives(new Objective[]{this.objective01, this.objective02, this.objective03});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      AbilityCapability.get(player).ifPresent((props) -> props.addUnlockedAbility((AbilityCore)YakkodoriAbility.INSTANCE.get(), AbilityUnlock.PROGRESSION));
   }
}
