package xyz.pixelatedw.mineminenomi.quests;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.EquippedItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class TestQuest extends Quest {
   public static final String ID = "test";
   public static final QuestId<TestQuest> INSTANCE;
   private static final int O1_DAMAGE_CHECK = 6;
   private static final int O2_COUNT = 30;
   private static final int O3_COUNT = 2000;
   private static final int O4_COUNT = 64;
   private static final Predicate<ItemStack> O1_CHECK;
   private static final Predicate<ItemStack> O4_CHECK;
   private static final Component O1_TITLE;
   private static final Component O2_TITLE;
   private static final Component O3_TITLE;
   private static final Component O4_TITLE;
   private static final Component O5_TITLE;
   private Objective objective01;
   private Objective objective02;
   private Objective objective03;
   private Objective objective04;
   private Objective objective05;

   public TestQuest(QuestId<TestQuest> core) {
      super(core);
      this.objective01 = new CollectItemObjective(this, O1_TITLE, 1, O1_CHECK);
      this.objective02 = (new KillEntityObjective(this, O2_TITLE, 30, SharedKillChecks.HAS_SWORD)).setOptional().addRequirement(this.objective01);
      this.objective03 = (new ReachDorikiObjective(this, O3_TITLE, 2000)).addRequirement(this.objective01);
      this.objective04 = (new CollectItemObjective(this, O4_TITLE, 20, O4_CHECK)).markHidden().addRequirement(this.objective03);
      this.objective05 = (new EquippedItemObjective(this, O5_TITLE, () -> Items.f_42473_, EquipmentSlot.CHEST)).markHidden().addRequirement(this.objective03);
      this.addObjectives(new Objective[]{this.objective01, this.objective02, this.objective03, this.objective04, this.objective05});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      ModMain.LOGGER.info("quest ended");
   }

   static {
      INSTANCE = (new QuestId.Builder<TestQuest>(TestQuest::new)).addRequirements(ModQuests.TEST2).build();
      O1_CHECK = (itemStack) -> ItemsHelper.isSword(itemStack) && ItemsHelper.getItemDamage(itemStack) > 6.0F;
      O4_CHECK = (itemStack) -> itemStack.m_41720_().equals(Items.f_42500_);
      O1_TITLE = ModRegistry.registerObjectiveTitle("test", 0, "Obtain a sword with over %s damage", 6);
      O2_TITLE = ModRegistry.registerObjectiveTitle("test", 1, "Kill %s enemies using a sword", 30);
      O3_TITLE = ModRegistry.registerObjectiveTitle("test", 2, "Reach %s doriki", 2000);
      O4_TITLE = ModRegistry.registerObjectiveTitle("test", 3, "Collect %s bones", 64);
      O5_TITLE = ModRegistry.registerObjectiveTitle("test", 4, "Equip a diamond chestplate");
   }
}
