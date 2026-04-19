package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class NewCheckboxButton extends Checkbox {
   private static final ResourceLocation TEXTURE = ResourceLocation.parse("textures/gui/checkbox.png");
   private final boolean showLabel;
   private Color iconColor;
   private Color textColor;
   private ICheckEvent event;

   public NewCheckboxButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected) {
      super(pX, pY, pWidth, pHeight, pMessage, pSelected);
      this.iconColor = Color.WHITE;
      this.textColor = Color.WHITE;
      this.showLabel = true;
   }

   public void m_5691_() {
      if (this.m_142518_()) {
         super.m_5691_();
         if (this.event != null) {
            this.event.onCheck(this);
         }
      }

   }

   public boolean m_142518_() {
      return this.f_93623_;
   }

   public void setActive(boolean flag) {
      this.f_93623_ = flag;
   }

   public void setIconColor(Color color) {
      this.iconColor = color;
   }

   public void setCheckEvent(ICheckEvent event) {
      this.event = event;
   }

   public void m_87963_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      Minecraft minecraft = Minecraft.m_91087_();
      RenderSystem.enableDepthTest();
      RenderSystem.setShaderColor((float)this.iconColor.getRed() / 255.0F, (float)this.iconColor.getGreen() / 255.0F, (float)this.iconColor.getBlue() / 255.0F, this.f_93625_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      graphics.m_280163_(TEXTURE, this.m_252754_(), this.m_252907_(), this.m_93696_() ? 20.0F : 0.0F, this.m_93840_() ? 20.0F : 0.0F, 20, this.f_93619_, 64, 64);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.showLabel) {
         RendererHelper.drawStringWithBorder(minecraft.f_91062_, graphics, this.m_6035_(), this.m_252754_() + 24, this.m_252907_() + (this.f_93619_ - 8) / 2, 14737632 | Mth.m_14167_(this.f_93625_ * 255.0F) << 24);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public interface ICheckEvent {
      void onCheck(NewCheckboxButton var1);
   }
}
