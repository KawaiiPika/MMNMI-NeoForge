package xyz.pixelatedw.mineminenomi.api.caps.command;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public interface ICommandReceiver {
   void setCurrentCommand(@Nullable LivingEntity var1, NPCCommand var2);

   NPCCommand getCurrentCommand();

   boolean canReceiveCommandFrom(LivingEntity var1);

   default boolean canMaintainCommand() {
      return true;
   }

   long getLastCommandTime();

   @Nullable LivingEntity getLastCommandSender();
}
