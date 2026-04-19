package xyz.pixelatedw.mineminenomi.items;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class NetItem extends Item {
   private static final int COOLDOWN = 200;
   private static final int USE_TIME = 20;
   private Supplier<CaughtInNetEffect> netEffect;

   public NetItem(Supplier<CaughtInNetEffect> handcuffed) {
      super((new Item.Properties()).m_41487_(1));
      this.netEffect = handcuffed;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!world.f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(player, ModAnimations.CHARGE_NET_THROW, this.m_8105_(itemstack), true), player);
      }

      player.m_6672_(hand);
      return InteractionResultHolder.m_19096_(itemstack);
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
      if (entity instanceof Player player) {
         if (!world.f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(player, ModAnimations.CHARGE_NET_THROW), player);
            int useTime = this.m_8105_(stack) - timeLeft;
            if (useTime < 20) {
               return;
            }

            this.throwNet(player, world, stack);
         }
      }

   }

   public void m_5929_(Level world, LivingEntity entity, ItemStack stack, int count) {
      if (world.f_46443_) {
         int useTime = this.m_8105_(stack) - entity.m_21212_();
         if (useTime >= 20) {
            Minecraft.m_91087_().f_91063_.f_109055_.m_109320_(InteractionHand.MAIN_HAND);
         }
      }

   }

   private void throwNet(LivingEntity entity, Level world, ItemStack stack) {
      NetEntity netEntity = NetEntity.createFromItem(world, entity, this);
      netEntity.m_6034_(entity.m_20185_(), entity.m_20188_() + (double)1.0F, entity.m_20189_());
      netEntity.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 1.25F, 0.0F);
      world.m_7967_(netEntity);
      boolean removeItem = true;
      if (entity instanceof Player player) {
         this.startCooldowns(player.m_36335_());
         removeItem = !player.m_150110_().f_35937_;
      }

      if (removeItem) {
         stack.m_41774_(1);
      }

   }

   private void startCooldowns(ItemCooldowns cooldown) {
      cooldown.m_41524_((Item)ModItems.ROPE_NET.get(), 200);
      cooldown.m_41524_((Item)ModItems.KAIROSEKI_NET.get(), 200);
   }

   public CaughtInNetEffect getEffect() {
      return (CaughtInNetEffect)this.netEffect.get();
   }

   public NetType getNetType() {
      return this.getEffect().getType();
   }

   public int m_8105_(ItemStack pStack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack pStack) {
      return UseAnim.NONE;
   }
}
