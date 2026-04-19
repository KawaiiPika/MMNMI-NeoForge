package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

@Mixin({ServerGamePacketListenerImpl.class})
public abstract class CreativeSlotChangeMixin {
   @Unique
   private boolean mineminenomi$duplicateCall = false;

   @Shadow
   public abstract ServerPlayer m_142253_();

   @Inject(
      method = {"handleSetCreativeModeSlot"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/inventory/InventoryMenu;getSlot(I)Lnet/minecraft/world/inventory/Slot;",
   shift = Shift.BEFORE
)},
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   public void mineminenomi$onFruitFromCreativeSlotChange(ServerboundSetCreativeModeSlotPacket slotPacket, CallbackInfo ci, boolean flag, ItemStack itemstack, CompoundTag compoundtag, boolean flag1, boolean flag2) {
      ServerGamePacketListenerImpl handler;
      label18: {
         handler = (ServerGamePacketListenerImpl)this;
         Item var10 = itemstack.m_41720_();
         if (var10 instanceof AkumaNoMiItem devilFruit) {
            if (!this.mineminenomi$duplicateCall) {
               OneFruitWorldData worldData = OneFruitWorldData.get();
               worldData.inventoryOneFruit(devilFruit, handler.m_142253_(), "Pulled from Creative Inventory");
               this.mineminenomi$duplicateCall = true;
               break label18;
            }
         }

         this.mineminenomi$duplicateCall = false;
      }

      if (itemstack.m_41619_()) {
         Item var13 = this.m_142253_().f_36095_.m_38853_(slotPacket.m_134561_()).m_7993_().m_41720_();
         if (var13 instanceof AkumaNoMiItem) {
            AkumaNoMiItem devilFruit = (AkumaNoMiItem)var13;
            OneFruitWorldData worldData = OneFruitWorldData.get();
            worldData.lostOneFruit(devilFruit.getRegistryKey(), handler.m_142253_(), "Removed from Creative Inventory");
         }
      }

   }
}
