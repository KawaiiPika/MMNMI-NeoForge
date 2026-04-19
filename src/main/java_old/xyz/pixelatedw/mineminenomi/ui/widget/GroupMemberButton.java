package xyz.pixelatedw.mineminenomi.ui.widget;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class GroupMemberButton extends Button {
   private Font font;
   private LivingEntity entity;
   private LivingEntity dummy;
   private int fontColor;

   public GroupMemberButton(int x, int y, int width, int height, LivingEntity entity, LivingEntity dummy, Button.OnPress onPress) {
      super(x, y, width, height, Component.m_237119_(), onPress, Button.f_252438_);
      this.fontColor = Color.WHITE.getRGB();
      this.font = Minecraft.m_91087_().f_91062_;
      this.entity = entity;
      this.dummy = dummy;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      graphics.m_280168_().m_85836_();
      if (this.f_93624_) {
         this.f_93622_ = this.f_93623_ && mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
         this.fontColor = Color.WHITE.getRGB();
         if (this.f_93622_) {
            graphics.m_280168_().m_85837_((double)0.0F, (double)-0.5F, (double)0.0F);
            this.fontColor = Color.ORANGE.getRGB();
         }

         this.renderEntityBust(this.entity, graphics, this.m_252754_() + this.f_93618_ / 2, this.m_252907_() + this.f_93619_ / 2 + 42);
      }

      graphics.m_280168_().m_85849_();
   }

   private void renderEntityBust(@Nullable LivingEntity entity, GuiGraphics graphics, int posX, int posY) {
      if (entity != null) {
         RendererHelper.drawLivingBust(entity, graphics, posX, posY, 30, -30, 10, false);
         String entityName = this.getEntityName(entity);
         RendererHelper.drawStringWithBorder(this.font, graphics, entityName, posX - this.font.m_92895_(entityName) / 2, posY - 30, this.fontColor);
      } else {
         RendererHelper.drawLivingBust(this.dummy, graphics, posX, posY, 30, -30, 10, true);
         String entityName = ModI18n.GUI_SELECT_ONE.getString();
         RendererHelper.drawStringWithBorder(this.font, graphics, entityName, posX - this.font.m_92895_(entityName) / 2, posY - 30, this.fontColor);
      }

   }

   private String getEntityName(LivingEntity entity) {
      if (entity instanceof Player player) {
         return player.m_36316_().getName();
      } else {
         return entity.m_5446_().getString();
      }
   }
}
