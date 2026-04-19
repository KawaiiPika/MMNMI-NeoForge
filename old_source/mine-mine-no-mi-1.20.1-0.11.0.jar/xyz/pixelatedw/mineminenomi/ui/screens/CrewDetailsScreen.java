package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CLeaveCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.CrewMembersScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class CrewDetailsScreen extends Screen {
   private Player player;
   private JollyRoger jollyRoger;
   private final Crew crew;
   private CrewMembersScrollPanel membersListPanel;
   private Component crewNameLabel;
   private Component crewMembersLabel;

   public CrewDetailsScreen(Crew crew) {
      super(Component.m_237119_());
      this.crew = crew;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.crewNameLabel, this.f_96543_ / 5, posY + 140, -1);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.crewMembersLabel, this.f_96543_ - this.f_96543_ / 3, 30, -1);
      if (this.jollyRoger != null) {
         this.jollyRoger.render(graphics.m_280168_(), this.f_96543_ / 5, posY + 20, 0.4F);
      }

      if (this.membersListPanel != null) {
         this.membersListPanel.m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      this.m_169413_();
      if (this.crew != null) {
         this.player = this.f_96541_.f_91074_;
         this.jollyRoger = this.crew.getJollyRoger();
         String var10001 = String.valueOf(ChatFormatting.BOLD);
         this.crewNameLabel = Component.m_237113_(var10001 + ModI18n.GUI_NAME.getString() + ": " + String.valueOf(ChatFormatting.RESET) + this.crew.getName());
         var10001 = String.valueOf(ChatFormatting.BOLD);
         this.crewMembersLabel = Component.m_237113_(var10001 + ModI18n.GUI_CREW_MEMBERS.getString() + ": ");
         this.membersListPanel = new CrewMembersScrollPanel(this, this.crew.getMembers());
         this.m_142416_(this.membersListPanel);
         this.m_7522_(this.membersListPanel);
         int posX = (this.f_96543_ - 256) / 2;
         int posY = (this.f_96544_ - 256) / 2;
         this.m_142416_(new PlankButton(posX, this.f_96544_ - 40, 80, 26, ModI18n.GUI_LEAVE, this::leaveCrew));
         if (this.isClientCaptain()) {
            this.m_142416_(new PlankButton(posX + 90, this.f_96544_ - 40, 120, 26, ModI18n.GUI_CHANGE_JOLLY_ROGER, this::openJollyRogerEditor));
         }

      }
   }

   public boolean isClientCaptain() {
      if (this.crew != null && this.player != null && this.crew.getCaptain() != null) {
         UUID captainUUID = this.crew.getCaptain().getUUID();
         Player crewCaptain = this.f_96541_.f_91073_.m_46003_(captainUUID);
         return crewCaptain != null && this.player.equals(crewCaptain);
      } else {
         return false;
      }
   }

   private void leaveCrew(Button btn) {
      ModNetwork.sendToServer(new CLeaveCrewPacket());
      this.m_7379_();
   }

   private void openJollyRogerEditor(Button btn) {
      ModNetwork.sendToServer(new COpenJollyRogerEditorScreenPacket());
   }
}
