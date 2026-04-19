package xyz.pixelatedw.mineminenomi.ui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.TextTable;
import net.minecraftforge.common.util.TextTable.Alignment;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class FactionButton extends Button {
   private final Minecraft minecraft;
   private IEntityStats entityData;
   private boolean isSelected;
   private TextTable.Alignment textAlignment;
   private int lineThickness;
   private boolean hasIcons;
   private int blackColor;

   public FactionButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      this(posX, posY, width, height, string, onPress, Button.f_252438_);
   }

   public FactionButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
      this.textAlignment = Alignment.LEFT;
      this.lineThickness = 1;
      this.hasIcons = true;
      this.blackColor = 0;
      this.entityData = (IEntityStats)EntityStatsCapability.get(Minecraft.m_91087_().f_91074_).orElse((Object)null);
      this.minecraft = Minecraft.m_91087_();
   }

   public void setTextAlignment(TextTable.Alignment alignment) {
      this.textAlignment = alignment;
   }

   public void setLineThickness(int thickness) {
      this.lineThickness = thickness;
   }

   public void disableIcons() {
      this.hasIcons = false;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      graphics.m_280168_().m_85836_();
      if (this.f_93624_) {
         this.f_93622_ = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
         int rgb = 16777215;
         int lineColor = 15395562;
         if (!this.f_93623_) {
            lineColor = 7039851;
            rgb = 7039851;
         }

         if (this.f_93622_ && this.f_93623_) {
            graphics.m_280168_().m_85837_((double)0.0F, (double)0.5F, (double)0.0F);
            int factionColor = FactionHelper.getFactionRGBColor(this.entityData);
            lineColor = factionColor;
            rgb = factionColor;
         }

         int textPosX;
         if (this.textAlignment == Alignment.CENTER) {
            textPosX = this.m_252754_() - this.minecraft.f_91062_.m_92852_(this.m_6035_()) / 2 + this.f_93618_ / 2;
         } else if (this.textAlignment == Alignment.RIGHT) {
            textPosX = this.m_252754_();
         } else {
            textPosX = this.m_252754_();
         }

         graphics.m_280024_(this.m_252754_() - 4, this.m_252907_() + this.f_93619_ - this.lineThickness + 2, this.f_93618_ + this.m_252754_() + 1, this.m_252907_() + this.f_93619_, this.blackColor, this.blackColor);
         graphics.m_280024_(this.m_252754_() - 5, this.m_252907_() + this.f_93619_ - this.lineThickness, this.f_93618_ + this.m_252754_(), this.m_252907_() + this.f_93619_, lineColor, lineColor);
         int textOffset = 0;
         if (this.hasIcons) {
            ResourceLocation factionIcon = FactionHelper.getFactionIcon(this.entityData);
            if (factionIcon != null) {
               RendererHelper.drawIcon(factionIcon, graphics.m_280168_(), (float)(this.m_252754_() - 12), (float)(this.m_252907_() - 4), 1.0F, 32.0F, 32.0F, this.blackColor);
               RendererHelper.drawIcon(factionIcon, graphics.m_280168_(), (float)(this.m_252754_() - 13), (float)(this.m_252907_() - 5), 1.0F, 32.0F, 32.0F, lineColor);
               textOffset = 13;
            }
         }

         RendererHelper.drawStringWithBorder(this.minecraft.f_91062_, graphics, this.m_6035_().getString(), textPosX + textOffset, this.m_252907_() + this.f_93619_ / 2 - 4, rgb);
      }

      graphics.m_280168_().m_85849_();
   }
}
