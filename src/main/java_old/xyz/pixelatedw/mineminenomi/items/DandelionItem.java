package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class DandelionItem extends Item {
   public DandelionItem() {
      super((new Item.Properties()).m_41489_(Foods.f_38828_));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player player) {
         player.m_36335_().m_41524_(this.m_5456_(), 200);
         entity.m_5810_();
         entity.m_9236_().m_7605_(entity, (byte)30);
         entity.m_5634_(getHealAmount(itemStack));
         itemStack.m_41774_(1);
      }

      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.EAT;
   }

   public static float getHealAmount(ItemStack itemStack) {
      return itemStack.m_41784_().m_128457_("amount");
   }

   public static void setHealAmount(ItemStack itemStack, float amount) {
      float maxHealth = (float)(ServerConfig.getDorikiLimit() / ServerConfig.getHealthGainFrequency());
      itemStack.m_41784_().m_128350_("amount", Mth.m_14036_(amount, 0.0F, maxHealth));
   }
}
