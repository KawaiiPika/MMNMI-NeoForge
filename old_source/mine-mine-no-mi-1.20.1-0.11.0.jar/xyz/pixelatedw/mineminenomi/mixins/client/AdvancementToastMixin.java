package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDisplayInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

@Mixin({AdvancementToast.class})
public class AdvancementToastMixin {
   @Shadow
   @Final
   private Advancement f_94795_;

   @Inject(
      method = {"render"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/item/ItemStack;II)V",
   shift = Shift.BEFORE
)},
      cancellable = true
   )
   public void render(GuiGraphics graphics, ToastComponent pToastComponent, long p_230444_3_, CallbackInfoReturnable<Toast.Visibility> callback) {
      DisplayInfo var7 = this.f_94795_.m_138320_();
      if (var7 instanceof AbilityDisplayInfo ablInfo) {
         if (ablInfo.getAbility() != null) {
            RendererHelper.drawIcon(ablInfo.getAbility().getIcon(), graphics.m_280168_(), 8.0F, 8.0F, 1.0F, 16.0F, 16.0F);
            callback.setReturnValue(p_230444_3_ >= 5000L ? Visibility.HIDE : Visibility.SHOW);
         }
      }

   }
}
