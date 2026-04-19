package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({CreateWorldScreen.class})
public class CreateWorldScreenMixin {
   @ModifyArg(
      method = {"onCreate()V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;Z)V"
),
      index = 2
   )
   public Lifecycle mineminenomi$confirmWorldCreation(Lifecycle lifecyclee) {
      return Lifecycle.stable();
   }
}
