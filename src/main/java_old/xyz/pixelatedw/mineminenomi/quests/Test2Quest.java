package xyz.pixelatedw.mineminenomi.quests;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import org.slf4j.Logger;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.objectives.CollectItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.EquippedItemObjective;

public class Test2Quest extends Quest {
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String ID = "test2";
   public static final QuestId<Test2Quest> INSTANCE = (new QuestId.Builder<Test2Quest>(Test2Quest::new)).build();
   private static final int O1_COUNT = 64;
   private static final Predicate<ItemStack> O1_CHECK = (itemStack) -> itemStack.m_41720_().equals(Items.f_42500_);
   private static final Component O1_TITLE = ModRegistry.registerObjectiveTitle("test2", 0, "Collect %s bones", 64);
   private static final Component O2_TITLE = ModRegistry.registerObjectiveTitle("test2", 1, "Equip a diamond chestplate");
   private Objective objective01;
   private Objective objective02;

   public Test2Quest(QuestId<Test2Quest> core) {
      super(core);
      this.objective01 = new CollectItemObjective(this, O1_TITLE, 20, O1_CHECK);
      this.objective02 = new EquippedItemObjective(this, O2_TITLE, () -> Items.f_42473_, EquipmentSlot.CHEST);
      this.addObjectives(new Objective[]{this.objective01, this.objective02});
      this.addTurnInEvent(100, this::giveReward);
   }

   public void giveReward(Player player) {
      LOGGER.info("quest2 ended");
   }
}
