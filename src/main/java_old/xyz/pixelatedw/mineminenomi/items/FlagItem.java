package xyz.pixelatedw.mineminenomi.items;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class FlagItem extends BlockItem {
   public static final String CANVAS_SIZE_NBT = "canvasSize";

   public FlagItem(Block block) {
      super(block, (new Item.Properties()).m_41487_(1));
   }

   protected BlockState m_5965_(BlockPlaceContext ctx) {
      Player player = ctx.m_43723_();
      ItemStack itemStack = ctx.m_43722_();
      if (!player.m_9236_().f_46443_) {
         itemStack.m_41784_().m_128362_("uuid", player.m_20148_());
         FactionsWorldData worldData = FactionsWorldData.get();
         Crew crew = worldData.getCrewWithMember(player.m_20148_());
         if (crew != null) {
            itemStack.m_41784_().m_128365_("crew", crew.write());
         }
      }

      return super.m_5965_(ctx);
   }

   protected boolean m_7274_(BlockPos pos, Level world, @Nullable Player player, ItemStack itemStack, BlockState state) {
      if (player != null) {
         FlagBlockEntity tileEntity = (FlagBlockEntity)world.m_7702_(pos);
         if (tileEntity != null && itemStack.m_41782_()) {
            Crew crew = Crew.from(itemStack.m_41783_().m_128469_("crew"));
            if (crew != null) {
               tileEntity.setCrew(crew);
            }

            tileEntity.m_6596_();
            return true;
         }
      }

      boolean flag = super.m_7274_(pos, world, player, itemStack, state);
      return flag;
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
}
