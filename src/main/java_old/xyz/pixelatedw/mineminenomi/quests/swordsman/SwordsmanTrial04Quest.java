package xyz.pixelatedw.mineminenomi.quests.swordsman;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedKillEntityObjective;

public class SwordsmanTrial04Quest extends Quest {
   public static final String ID = "trial_tatsu_maki";
   public static final QuestId<SwordsmanTrial04Quest> INSTANCE;
   private static final int O1_COUNT = 20;
   private static final int O2_COUNT = 3;
   private static final int O2_TIME = 5;
   private static final int O3_COUNT = 5;
   private static final Component O1_TITLE;
   private static final Component O2_TITLE;
   private static final Component O3_TITLE;
   private Objective objective01;
   private Objective objective02;
   private Objective objective03;

   public SwordsmanTrial04Quest(QuestId id) {
      super(id);
      this.objective01 = new HitEntityObjective(this, O1_TITLE, 20);
      this.objective02 = new TimedKillEntityObjective(this, O2_TITLE, 3, 5, SharedKillChecks.HAS_SWORD);
      this.objective03 = new KillEntityObjective(this, O3_TITLE, 5, SharedKillChecks.AIRBORNE_ENEMY_CHECK.and(SharedKillChecks.HAS_SWORD));
      this.addObjectives(new Objective[]{this.objective01, this.objective02, this.objective03});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      AbilityCapability.get(player).ifPresent((props) -> props.addUnlockedAbility((AbilityCore)OTatsumakiAbility.INSTANCE.get(), AbilityUnlock.PROGRESSION));
   }

   static {
      INSTANCE = (new QuestId.Builder<SwordsmanTrial04Quest>(SwordsmanTrial04Quest::new)).addRequirements(ModQuests.SWORDSMAN_TRIAL_03).build();
      O1_TITLE = ModRegistry.registerObjectiveTitle("trial_tatsu_maki", 0, "Damage %s enemies with sweeping attacks", 20);
      O2_TITLE = ModRegistry.registerObjectiveTitle("trial_tatsu_maki", 1, "Kill %s enemies in less than %s seconds using a sword", 3, 5);
      O3_TITLE = ModRegistry.registerObjectiveTitle("trial_tatsu_maki", 2, "Kill %s airborne enemies using a sword", 5);
   }
}
