package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;

public class CSetDyeLayerPacket {
   private int layer;

   public CSetDyeLayerPacket() {
   }

   public CSetDyeLayerPacket(int layer) {
      this.layer = layer;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.layer);
   }

   public static CSetDyeLayerPacket decode(FriendlyByteBuf buffer) {
      CSetDyeLayerPacket msg = new CSetDyeLayerPacket();
      msg.layer = buffer.readInt();
      return msg;
   }

   public static void handle(CSetDyeLayerPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            AbstractContainerMenu patt1444$temp = player.f_36096_;
            if (patt1444$temp instanceof CraftingMenu craftingMenu) {
               Container container = ((Slot)craftingMenu.f_38839_.get(1)).f_40218_;
               if (container instanceof CraftingContainer craftingContainer) {
                  Optional<CraftingRecipe> recipe = player.m_20194_().m_129894_().m_44015_(RecipeType.f_44107_, craftingContainer, player.m_9236_());
                  if (recipe.isPresent()) {
                     ItemStack resultStack = ((Slot)craftingMenu.f_38839_.get(0)).m_7993_();
                     Object patt2029$temp = recipe.get();
                     if (patt2029$temp instanceof MultiChannelDyeRecipe) {
                        MultiChannelDyeRecipe dyeRecipe = (MultiChannelDyeRecipe)patt2029$temp;
                        Item patt2097$temp = resultStack.m_41720_();
                        if (patt2097$temp instanceof IMultiChannelColorItem) {
                           IMultiChannelColorItem colorItem = (IMultiChannelColorItem)patt2097$temp;
                           if (colorItem.canBeDyed()) {
                              int layer = Math.min(message.layer, colorItem.getMaxLayers());
                              dyeRecipe.setLayer(layer);
                              dyeRecipe.assemble(craftingContainer, player.m_9236_().m_9598_());
                              craftingMenu.m_6199_(craftingContainer);
                           }
                        }
                     }
                  }
               }
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
