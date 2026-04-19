package xyz.pixelatedw.mineminenomi.api.caps.command;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class CommandReceiverCapability {
   public static final Capability<ICommandReceiver> INSTANCE = CapabilityManager.get(new CapabilityToken<ICommandReceiver>() {
   });
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "command_receiver");

   public static LazyOptional<ICommandReceiver> get(Mob entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }
}
