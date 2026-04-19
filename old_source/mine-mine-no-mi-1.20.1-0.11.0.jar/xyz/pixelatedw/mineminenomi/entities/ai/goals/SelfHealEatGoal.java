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
   private static final Item[] FOODS;
   private ItemStack prevItem;
   private int eatTicks;

   public SelfHealEatGoal(Mob entity) {
      super(entity);
      this.prevItem = ItemStack.f_41583_;
      this.eatTicks = 0;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.entity.m_21223_() >= this.entity.m_21233_()) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.entity.m_21188_() != null && this.entity.m_21188_().m_6084_()) {
         return false;
      } else {
         return this.hasTimePassedSinceLastEnd(1200.0F);
      }
   }

   public boolean m_8045_() {
      return this.eatTicks <= 60;
   }

   public void m_8056_() {
      super.m_8056_();
      this.prevItem = this.entity.m_21205_();
      ItemStack foodItem = FOODS[this.entity.m_217043_().m_188503_(FOODS.length)].m_7968_();
      this.entity.m_8061_(EquipmentSlot.MAINHAND, foodItem);
      this.entity.m_6672_(InteractionHand.MAIN_HAND);
      this.eatTicks = 0;
   }

   public void m_8037_() {
      ++this.eatTicks;
   }

   public void m_8041_() {
      super.m_8041_();
      this.entity.m_5810_();
      this.entity.m_8061_(EquipmentSlot.MAINHAND, this.prevItem);
      this.entity.m_5634_(10.0F);
   }

   static {
      FOODS = new Item[]{Items.f_42580_, Items.f_42582_, Items.f_42530_, Items.f_42659_, Items.f_42486_, Items.f_42698_, Items.f_42531_, Items.f_42406_};
   }
}
