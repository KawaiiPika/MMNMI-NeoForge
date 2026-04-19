package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenWantedPosterScreenPacket;

public class WantedPosterItem extends BlockItem {
   public static final String CANVAS_SIZE_TAG = "canvasSize";

   public WantedPosterItem(Block block) {
      super(block, (new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!player.m_9236_().f_46443_ && itemstack.m_41782_()) {
         WantedPosterData wantedPosterData = WantedPosterData.from(itemstack.m_41783_().m_128469_("WPData"));
         wantedPosterData.checkIfExpired();
         ModNetwork.sendTo(new SOpenWantedPosterScreenPacket(wantedPosterData), player);
      }

      return InteractionResultHolder.m_19090_(itemstack);
   }

   protected boolean m_7274_(BlockPos pos, Level world, @Nullable Player player, ItemStack itemStack, BlockState state) {
      if (player != null) {
         WantedPosterBlockEntity tileEntity = (WantedPosterBlockEntity)world.m_7702_(pos);
         if (tileEntity != null && itemStack.m_41782_()) {
            WantedPosterData wantedPosterData = WantedPosterData.from(itemStack.m_41783_().m_128469_("WPData"));
            wantedPosterData.checkIfExpired();
            tileEntity.setWantedPosterData(wantedPosterData);
            tileEntity.m_6596_();
         }
      }

      boolean flag = super.m_7274_(pos, world, player, itemStack, state);
      return flag;
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
      if (itemStack.m_41782_() && itemStack.m_41783_().m_128441_("WPData")) {
         WantedPosterData wantedPosterData = WantedPosterData.from(itemStack.m_41783_().m_128469_("WPData"));
         if (wantedPosterData.getTrackedPosition().isPresent()) {
            Vec3 pos = (Vec3)wantedPosterData.getTrackedPosition().get();
            list.add(Component.m_237119_());
            int var10001 = (int)pos.f_82479_;
            list.add(Component.m_237113_("x: " + var10001 + " y: " + (int)pos.f_82480_ + " z: " + (int)pos.f_82481_));
         }
      }

   }

   public static void setCanvasSize(ItemStack stack, CanvasSize size) {
      stack.m_41784_().m_128405_("canvasSize", size.ordinal());
      String var10001 = WyHelper.capitalize(size.m_7912_());
      stack.m_41714_(Component.m_237113_(var10001 + " " + stack.m_41720_().m_7626_(stack).getString()));
   }

   public static boolean upgradeCanvasSize(ItemStack stack) {
      CanvasSize currentSize = getCanvasSize(stack);
      if (currentSize.isMaximumSize()) {
         return false;
      } else {
         CanvasSize upgradeSize = CanvasSize.values()[currentSize.ordinal() + 1];
         setCanvasSize(stack, upgradeSize);
         return true;
      }
   }

   public static CanvasSize getCanvasSize(ItemStack stack) {
      if (!stack.m_41782_()) {
         return CanvasSize.SMALL;
      } else {
         CanvasSize size = CanvasSize.values()[stack.m_41784_().m_128451_("canvasSize")];
         return size;
      }
   }

   public static ItemStack getWantedPosterStack(Player player, long bounty) {
      WantedPosterData wantedData = new WantedPosterData(player, bounty);
      ItemStack stack = ((Block)ModBlocks.WANTED_POSTER.get()).m_5456_().m_7968_();
      stack.m_41784_().m_128365_("WPData", wantedData.write());
      return stack;
   }
}
