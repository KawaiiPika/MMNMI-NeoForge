package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEventReceiverScreen<T extends INBTSerializable<CompoundTag>> {
   void handleEvent(T var1);

   INBTSerializable<CompoundTag> decode(CompoundTag var1);

   static <T extends INBTSerializable<CompoundTag>> void tryHandle(Screen screen, CompoundTag eventData) {
      if (screen instanceof IEventReceiverScreen eventReceiver) {
         eventReceiver.handleEvent(eventReceiver.decode(eventData));
      }

   }
}
