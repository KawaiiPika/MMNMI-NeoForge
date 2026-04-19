package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CCreateCrewPacket;
import xyz.pixelatedw.mineminenomi.ui.widget.SimpleButton;

public class CreateCrewScreen extends Screen implements IEventReceiverScreen<SimpleMessageScreenEvent> {
   private static final Style ERROR_STYLE;
   private Player player;
   private EditBox nameEdit;
   private Component errorMessage = Component.m_237119_();
   private int errorMessageVisibleTicks;

   public CreateCrewScreen() {
      super(Component.m_237119_());
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.nameEdit.m_88315_(graphics, mouseX, mouseY, partialTicks);
      if (this.errorMessageVisibleTicks > 0) {
         int errorMessagePosX = posX - this.f_96547_.m_92895_(this.errorMessage.getString()) / 2 - 1;
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.errorMessage, errorMessagePosX, posY - 35, -1);
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      this.player = this.f_96541_.f_91074_;
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.nameEdit = new EditBox(this.f_96547_, posX - 152, posY - 10, 300, 20, Component.m_237119_());
      this.nameEdit.m_94199_(255);
      this.nameEdit.m_94164_(this.player.m_7755_().getString() + "'s Crew");
      this.m_142416_(this.nameEdit);
      this.m_7522_(this.nameEdit);
      SimpleButton createCrewButton = new SimpleButton(posX - 30, posY + 50, 60, 16, ModI18n.GUI_CREATE, this::createCrew);
      this.m_142416_(createCrewButton);
   }

   public void m_6574_(Minecraft minecraft, int x, int y) {
      String crewName = this.nameEdit.m_94155_();
      this.m_6575_(minecraft, x, y);
      this.nameEdit.m_94164_(crewName);
   }

   public void m_86600_() {
      this.nameEdit.m_94120_();
      if (this.errorMessageVisibleTicks > 0) {
         --this.errorMessageVisibleTicks;
      }

   }

   public void handleEvent(SimpleMessageScreenEvent event) {
      if (event.getMessage() != null) {
         this.errorMessage = event.getMessage().m_6881_().m_6270_(ERROR_STYLE);
         this.errorMessageVisibleTicks = event.getTimeVisible();
      } else {
         this.m_7379_();
      }

   }

   public SimpleMessageScreenEvent decode(CompoundTag data) {
      SimpleMessageScreenEvent event = new SimpleMessageScreenEvent();
      event.deserializeNBT(data);
      return event;
   }

   private void createCrew(Button btn) {
      ModNetwork.sendToServer(new CCreateCrewPacket(this.nameEdit.m_94155_()));
   }

   static {
      ERROR_STYLE = Style.f_131099_.m_131140_(ChatFormatting.RED);
   }
}
