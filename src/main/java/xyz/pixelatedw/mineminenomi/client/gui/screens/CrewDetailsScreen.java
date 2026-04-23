package xyz.pixelatedw.mineminenomi.client.gui.screens;

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
import xyz.pixelatedw.mineminenomi.networking.ModNetworking;

import xyz.pixelatedw.mineminenomi.networking.packets.CLeaveCrewPacket;
import xyz.pixelatedw.mineminenomi.networking.packets.COpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.client.gui.panels.CrewMembersScrollPanel;
import xyz.pixelatedw.mineminenomi.client.gui.widgets.PlankButton;

public class CrewDetailsScreen extends Screen {
   private Player player;
   private JollyRoger jollyRoger;
   private final Crew crew;
   private CrewMembersScrollPanel membersListPanel;
   private Component crewNameLabel;
   private Component crewMembersLabel;

   public CrewDetailsScreen(Crew crew) {
      super(Component.empty());
      this.crew = crew;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(graphics, mouseX, mouseY, partialTicks);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = (this.width - 256) / 2;
      int posY = (this.height - 256) / 2;
      RendererHelper.drawStringWithBorder(this.font, graphics, (Component)this.crewNameLabel, this.width / 5, posY + 140, -1);
      RendererHelper.drawStringWithBorder(this.font, graphics, (Component)this.crewMembersLabel, this.width - this.width / 3, 30, -1);
      if (this.jollyRoger != null) {
         this.jollyRoger.render(graphics, this.width / 5, posY + 20, 0.4F);
      }

      if (this.membersListPanel != null) {
         this.membersListPanel.render(graphics, mouseX, mouseY, partialTicks);
      }

      super.render(graphics, mouseX, mouseY, partialTicks);
   }

   public void init() {
      this.clearWidgets();
      if (this.crew != null) {
         this.player = this.minecraft.player;
         this.jollyRoger = this.crew.getJollyRoger();
         String var10001 = String.valueOf(ChatFormatting.BOLD);
         this.crewNameLabel = Component.literal(var10001 + Component.translatable("gui.name").getString() + ": " + String.valueOf(ChatFormatting.RESET) + this.crew.getName());
         var10001 = String.valueOf(ChatFormatting.BOLD);
         this.crewMembersLabel = Component.literal(var10001 + Component.translatable("gui.crew_members").getString() + ": ");
         this.membersListPanel = new CrewMembersScrollPanel(this, this.crew.getMembers());
         this.addRenderableWidget(this.membersListPanel);
         this.addWidget(this.membersListPanel);
         int posX = (this.width - 256) / 2;
         int posY = (this.height - 256) / 2;
         this.addRenderableWidget(new PlankButton(posX, this.height - 40, 80, 26, Component.translatable("gui.leave"), this::leaveCrew));
         if (this.isClientCaptain()) {
            this.addRenderableWidget(new PlankButton(posX + 90, this.height - 40, 120, 26, Component.translatable("gui.change_jolly_roger"), this::openJollyRogerEditor));
         }

      }
   }

   public boolean isClientCaptain() {
      if (this.crew != null && this.player != null && this.crew.getCaptain() != null) {
         UUID captainUUID = this.crew.getCaptain().getUUID();
         Player crewCaptain = this.minecraft.level.getPlayerByUUID(captainUUID);
         return crewCaptain != null && this.player.equals(crewCaptain);
      } else {
         return false;
      }
   }

   private void leaveCrew(Button btn) {
      net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CLeaveCrewPacket());
      this.onClose();
   }

   private void openJollyRogerEditor(Button btn) {
      net.neoforged.neoforge.network.PacketDistributor.sendToServer(new COpenJollyRogerEditorScreenPacket());
   }
   public <T extends net.minecraft.client.gui.components.events.GuiEventListener & net.minecraft.client.gui.components.Renderable & net.minecraft.client.gui.narration.NarratableEntry> void addRenderableWidgetPublic(T widget) {
      this.addRenderableWidget(widget);
   }
}
