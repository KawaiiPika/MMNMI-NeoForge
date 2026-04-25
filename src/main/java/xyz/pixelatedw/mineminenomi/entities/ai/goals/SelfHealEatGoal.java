package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class SelfHealEatGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 1200;
   private static final Item[] FOODS = new Item[]{Items.APPLE, Items.GOLDEN_APPLE, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN};
   private ItemStack prevItem;
   private int eatTicks;

   public SelfHealEatGoal(Mob entity) {
      super(entity);
      this.prevItem = ItemStack.EMPTY;
      this.eatTicks = 0;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.entity.getHealth() >= this.entity.getMaxHealth()) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.entity.getLastHurtByMob() != null && this.entity.getLastHurtByMob().isAlive()) {
         return false;
      } else {
         return this.hasTimePassedSinceLastEnd(1200.0F);
      }
   }

   @Override
   public boolean canContinueToUse() {
      return this.eatTicks <= 60;
   }

   @Override
   public void start() {
      super.start();
      this.prevItem = this.entity.getMainHandItem();
      ItemStack foodItem = new ItemStack(FOODS[this.entity.getRandom().nextInt(FOODS.length)]);
      this.entity.setItemSlot(EquipmentSlot.MAINHAND, foodItem);
      this.entity.swing(InteractionHand.MAIN_HAND);
      this.eatTicks = 0;
   }

   @Override
   public void tick() {
      ++this.eatTicks;
   }

   @Override
   public void stop() {
      super.stop();
      this.entity.clearFire();
      this.entity.setItemSlot(EquipmentSlot.MAINHAND, this.prevItem);
      this.entity.heal(10.0F);
   }
}
