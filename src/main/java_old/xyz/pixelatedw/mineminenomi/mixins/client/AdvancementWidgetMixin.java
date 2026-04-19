package xyz.pixelatedw.mineminenomi.mixins.client;

import java.util.List;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementWidgetType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDisplayInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

@Mixin({AdvancementWidget.class})
public abstract class AdvancementWidgetMixin {
   private static final ResourceLocation WIDGETS_LOCATION = ResourceLocation.parse("textures/gui/advancements/widgets.png");
   @Shadow
   @Final
   private DisplayInfo f_97243_;
   @Shadow
   @Final
   private int f_97251_;
   @Shadow
   @Final
   private int f_97252_;
   @Shadow
   private AdvancementProgress f_97250_;
   @Shadow
   @Final
   private List<AdvancementWidget> f_97249_;

   @Inject(
      method = {"draw"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void draw(GuiGraphics graphics, int pX, int pY, CallbackInfo callback) {
      DisplayInfo var6 = this.f_97243_;
      if (var6 instanceof AbilityDisplayInfo ablInfo) {
         if (ablInfo.getAbility() != null) {
            if (!this.f_97243_.m_14997_() || this.f_97250_ != null && this.f_97250_.m_8193_()) {
               float f = this.f_97250_ == null ? 0.0F : this.f_97250_.m_8213_();
               AdvancementWidgetType advancementstate;
               if (f >= 1.0F) {
                  advancementstate = AdvancementWidgetType.OBTAINED;
               } else {
                  advancementstate = AdvancementWidgetType.UNOBTAINED;
               }

               graphics.m_280218_(WIDGETS_LOCATION, pX + this.f_97251_ + 3, pY + this.f_97252_, this.f_97243_.m_14992_().m_15551_(), 128 + advancementstate.m_97325_() * 26, 26, 26);
               RendererHelper.drawIcon(ablInfo.getAbility().getIcon(), graphics.m_280168_(), (float)(pX + this.f_97251_ + 8), (float)(pY + this.f_97252_ + 5), 1.0F, 16.0F, 16.0F);
            }

            for(AdvancementWidget advancemententrygui : this.f_97249_) {
               advancemententrygui.m_280229_(graphics, pX, pY);
            }

            callback.cancel();
         }
      }

   }

   @Inject(
      method = {"drawHover"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V",
   shift = Shift.BEFORE
)},
      cancellable = true
   )
   public void drawHover(GuiGraphics graphics, int pX, int pY, float pFade, int pWidth, int pHeight, CallbackInfo callback) {
      DisplayInfo var9 = this.f_97243_;
      if (var9 instanceof AbilityDisplayInfo ablInfo) {
         if (ablInfo.getAbility() != null) {
            RendererHelper.drawIcon(ablInfo.getAbility().getIcon(), graphics.m_280168_(), (float)(pX + this.f_97251_ + 8), (float)(pY + this.f_97252_ + 5), 1.0F, 16.0F, 16.0F);
            callback.cancel();
         }
      }

   }
}
