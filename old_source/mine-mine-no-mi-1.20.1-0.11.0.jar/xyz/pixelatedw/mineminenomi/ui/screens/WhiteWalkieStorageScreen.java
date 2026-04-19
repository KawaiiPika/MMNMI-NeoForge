package xyz.pixelatedw.mineminenomi.ui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CUpdateInventoryPagePacket;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class WhiteWalkieStorageScreen extends AbstractContainerScreen<WhiteWalkieStorageContainer> {
   private static final ResourceLocation INVENTORY_LOCATION = ResourceLocation.parse("textures/gui/container/horse.png");
   private final WhiteWalkieEntity whiteWalkie;

   public WhiteWalkieStorageScreen(WhiteWalkieStorageContainer menu, Inventory inv) {
      super(menu, inv, menu.getWhiteWalkie().m_5446_());
      this.whiteWalkie = menu.getWhiteWalkie();
   }

   public void m_88315_(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTicks) {
      this.m_280273_(graphics);
      super.m_88315_(graphics, pMouseX, pMouseY, pPartialTicks);
      this.m_280072_(graphics, pMouseX, pMouseY);
   }

   protected void m_7286_(GuiGraphics graphics, float partialTicks, int pX, int pY) {
      int posX = (this.f_96543_ - this.f_97726_) / 2;
      int posY = (this.f_96544_ - this.f_97727_) / 2;
      graphics.m_280218_(INVENTORY_LOCATION, posX, posY, 0, 0, this.f_97726_, this.f_97727_);
      if (this.whiteWalkie.hasChest()) {
         graphics.m_280218_(INVENTORY_LOCATION, posX + 79, posY + 17, 0, this.f_97727_, this.whiteWalkie.getInventoryColumns() * 18, 54);
      }

      if (this.whiteWalkie.m_21824_()) {
         graphics.m_280218_(INVENTORY_LOCATION, posX + 7, posY + 35 - 18, 18, this.f_97727_ + 54, 18, 18);
      }

      InventoryScreen.renderEntityInInventoryFollowsAngle(graphics, posX + 50, posY + 60, 13, -2.0F, 0.0F, this.whiteWalkie);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_169413_();
      int posX = (this.f_96543_ - this.f_97726_) / 2;
      int posY = (this.f_96544_ - this.f_97727_) / 2;
      PlankButton page1 = new PlankButton(posX + 80, posY + 2, 14, 14, Component.m_237113_("1"), (b) -> {
         this.whiteWalkie.setInventoryPage(0);
         ModNetwork.sendToServer(new CUpdateInventoryPagePacket(this.whiteWalkie.m_19879_(), 0));
         this.m_7856_();
      });
      if (this.whiteWalkie.hasChest()) {
         this.m_142416_(page1);
      }

      if (this.whiteWalkie.getInventoryPage() == 0) {
         page1.f_93623_ = false;
      }

      PlankButton page2 = new PlankButton(posX + 95, posY + 2, 14, 14, Component.m_237113_("2"), (b) -> {
         this.whiteWalkie.setInventoryPage(1);
         ModNetwork.sendToServer(new CUpdateInventoryPagePacket(this.whiteWalkie.m_19879_(), 1));
         this.m_7856_();
      });
      if (this.whiteWalkie.getChests() > 1) {
         this.m_142416_(page2);
      }

      if (this.whiteWalkie.getInventoryPage() == 1) {
         page2.f_93623_ = false;
      }

   }
}
