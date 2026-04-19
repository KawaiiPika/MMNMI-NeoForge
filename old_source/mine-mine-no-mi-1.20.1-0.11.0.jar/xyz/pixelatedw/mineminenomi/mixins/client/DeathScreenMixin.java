package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

@Mixin({DeathScreen.class})
public class DeathScreenMixin {
   @Shadow
   @Final
   @Mutable
   public boolean f_95908_;

   @Inject(
      method = {"init"},
      at = {@At("HEAD")}
   )
   public void mineminenomi$init(CallbackInfo callback) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (props != null) {
         if (props.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && !props.hasYomiPower()) {
            this.f_95908_ = false;
         }

      }
   }
}
