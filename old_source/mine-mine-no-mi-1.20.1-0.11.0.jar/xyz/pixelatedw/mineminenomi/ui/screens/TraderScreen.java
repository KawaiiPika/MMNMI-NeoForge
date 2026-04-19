package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.FlickeringString;
import xyz.pixelatedw.mineminenomi.api.ui.SequencedString;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CBuyFromTraderPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.TradeItemListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widget.FactionButton;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class TraderScreen extends Screen {
   private static final Quaternionf TRADER_ANGLE = (new Quaternionf()).rotationXYZ(0.0F, -40.0F, (float)Math.PI);
   private TradeState guiState;
   private LocalPlayer player;
   private IEntityStats statsData;
   private TraderEntity trader;
   private int wantedAmount;
   private TradeEntry selectedStack;
   private TradeEntry previousStack;
   private TradeEntry hoveredStack;
   private SequencedString startMessage;
   private FlickeringString skipMessage;
   private EditBox quantityEdit;
   private TradeItemListScrollPanel listPanel;

   public TraderScreen(TraderEntity trader) {
      super(Component.m_237119_());
      this.guiState = TraderScreen.TradeState.Welcome;
      this.wantedAmount = 1;
      this.player = Minecraft.m_91087_().f_91074_;
      this.statsData = (IEntityStats)EntityStatsCapability.get(this.player).orElse((Object)null);
      this.trader = trader;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      switch (this.guiState) {
         case Welcome -> this.renderMenu(graphics, mouseX, mouseY, partialTicks);
         case Sell -> this.renderSellShop(graphics, mouseX, mouseY, partialTicks);
         case Buy -> this.renderBuyShop(graphics, mouseX, mouseY, partialTicks);
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   private void renderMenu(GuiGraphics graphics, int x, int y, float partialTicks) {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      if (!this.trader.canBuyFromPlayers()) {
         this.startMessage.render(graphics, this.getMinecraft().f_91062_, posX - 200, posY - 50, partialTicks);
         this.skipMessage.render(graphics, posX - 100, posY + 60, partialTicks);
         if ((float)this.startMessage.getTickCount() * partialTicks > (float)this.startMessage.getDelayTicks()) {
            this.switchToState(TraderScreen.TradeState.Sell);
         }
      } else {
         this.startMessage.render(graphics, this.getMinecraft().f_91062_, posX - 150, posY - 105, partialTicks);
      }

      this.renderTrader(graphics, posX, posY);
   }

   private void renderSellShop(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      graphics.m_280218_(ModResources.BLANK2, posX - 128, posY - 110, 0, 0, 256, 256);
      this.renderUpperColumn(graphics);
      this.drawSizedString(graphics, ModI18n.GUI_NAME, posX - 20, posY - 63, 0.9F, -1);
      this.drawSizedString(graphics, ModI18n.GUI_PRICE, posX + 50, posY - 63, 0.9F, -1);
      int type = this.trader.getCurrency() == Currency.BELLY ? 0 : 34;
      graphics.m_280218_(ModResources.CURRENCIES, posX + 53, posY - 76, type, 32, 32, 64);
      this.hover(mouseX, mouseY);
   }

   private void renderBuyShop(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.startMessage.render(graphics, this.getMinecraft().f_91062_, posX - 150, posY - 105, partialTicks);
      this.renderTrader(graphics, posX, posY);
   }

   public void renderUpperColumn(GuiGraphics graphics) {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      String amount = "";
      TradeEntry shownStack = this.hoveredStack != null ? this.hoveredStack : this.getSelectedStack();
      if (shownStack != null) {
         this.quantityEdit.m_88315_(graphics, 0, 0, 0.0F);
         RendererHelper.drawIcon(ModResources.BLANK, graphics.m_280168_(), (float)(posX - 117), (float)(posY - 105), 1.0F, 32.0F, 42.0F);
         graphics.m_280480_(shownStack.getItemStack(), posX - 110, posY - 100);
         amount = "/" + shownStack.getCount();
         if (shownStack.hasInfiniteStock()) {
            amount = "/∞";
         }
      }

      long currency = this.trader.getCurrency() == Currency.BELLY ? this.statsData.getBelly() : this.statsData.getExtol();
      this.drawSizedString(graphics, Component.m_237113_(amount), posX - 45, posY - 94, 1.2F, -1);
      this.drawSizedString(graphics, Component.m_237113_("" + currency), posX + 85, posY - 95, 0.9F, -1);
      int type = this.trader.getCurrency() == Currency.BELLY ? 0 : 34;
      graphics.m_280218_(ModResources.CURRENCIES, posX + 102, posY - 108, type, 32, 32, 64);
   }

   public void m_7856_() {
      if (this.statsData == null) {
         this.m_7379_();
      } else {
         this.setupScreen();
      }
   }

   private void setupScreen() {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.m_169413_();
      switch (this.guiState) {
         case Welcome:
            Component comp = null;
            if (this.trader.canTrade(this.player)) {
               comp = ModI18n.TRADER_WELCOME_MESSAGE;
            } else {
               comp = this.trader.getTradeFailMessage();
            }

            String message = comp.getString();
            this.startMessage = new SequencedString(message, 250, this.f_96547_.m_92895_(message) / 2);
            if (this.skipMessage == null) {
               this.skipMessage = new FlickeringString(ModI18n.GUI_CLICK_TO_SKIP, 20);
            }

            if (this.trader.canBuyFromPlayers()) {
               FactionButton buyButton = new FactionButton(posX - 180, posY - 50, 100, 20, ModI18n.GUI_BUY, (btn) -> this.switchToState(TraderScreen.TradeState.Sell));
               this.m_142416_(buyButton);
               FactionButton sellButton = new FactionButton(posX - 180, posY - 20, 100, 20, ModI18n.GUI_SELL, (btn) -> this.switchToState(TraderScreen.TradeState.Buy));
               this.m_142416_(sellButton);
            }
            break;
         case Sell:
            this.listPanel = new TradeItemListScrollPanel(this, this.trader.getTradingItems());
            this.m_142416_(this.listPanel);
            this.quantityEdit = new EditBox(this.f_96547_, posX - 80, posY - 100, 20, 20, Component.m_237119_());
            this.quantityEdit.m_94199_(2);
            this.quantityEdit.m_94164_("1");
            this.m_142416_(this.quantityEdit);
            PlankButton buyBtn = new PlankButton(posX - 10, posY - 100, 64, 22, ModI18n.GUI_BUY, this::onBuy);
            this.m_142416_(buyBtn);
            if (this.trader.canBuyFromPlayers()) {
               FactionButton backButton = new FactionButton(posX - 200, posY + 78, 70, 20, ModI18n.GUI_CANCEL, (btn) -> this.switchToState(TraderScreen.TradeState.Welcome));
               this.m_142416_(backButton);
            }
      }

   }

   public boolean m_6375_(double mouseX, double mouseY, int partialTicks) {
      switch (this.guiState) {
         case Welcome:
            this.switchToState(TraderScreen.TradeState.Sell);
         default:
            return super.m_6375_(mouseX, mouseY, partialTicks);
      }
   }

   public void m_6574_(Minecraft minecraft, int x, int y) {
      if (this.quantityEdit != null) {
         String amount = this.quantityEdit.m_94155_();
         this.m_6575_(minecraft, x, y);
         this.quantityEdit.m_94164_(amount);
      }

   }

   public void m_86600_() {
      if (this.quantityEdit != null && this.getSelectedStack() != null) {
         this.quantityEdit.m_94120_();

         try {
            int tempAmount = Integer.parseInt(this.quantityEdit.m_94155_());
            boolean forceUpdate = false;
            if (this.hoveredStack != null & this.hoveredStack != this.getSelectedStack()) {
               tempAmount = 1;
               forceUpdate = true;
            }

            if (tempAmount != this.wantedAmount || this.getSelectedStack() != this.previousStack || forceUpdate) {
               if (!this.getSelectedStack().hasInfiniteStock()) {
                  tempAmount = Mth.m_14045_(tempAmount, 0, this.getSelectedStack().getCount());
               }

               int cursor = this.quantityEdit.m_94207_();
               this.quantityEdit.m_94144_("" + tempAmount);
               this.quantityEdit.m_94192_(cursor);
               this.wantedAmount = tempAmount;
               this.previousStack = this.getSelectedStack();
            }
         } catch (Exception var4) {
            this.quantityEdit.m_94144_("");
         }
      }

   }

   private void switchToState(TradeState state) {
      if (this.trader.canTrade(this.player)) {
         this.guiState = state;
         this.m_6575_(this.getMinecraft(), this.f_96543_, this.f_96544_);
      } else {
         this.m_7379_();
      }

   }

   public void onBuy(Button btn) {
      if (this.getSelectedStack() != null) {
         if (this.getWantedAmount() <= this.getSelectedStack().getCount() || this.getSelectedStack().hasInfiniteStock()) {
            if (this.getEmptySlots() >= this.calculateSlotsFromCount(this.getWantedAmount())) {
               int totalPrice = this.getSelectedStack().getPrice() * this.getWantedAmount();
               long currency = this.trader.getCurrency() == Currency.BELLY ? this.statsData.getBelly() : this.statsData.getExtol();
               if (currency >= (long)totalPrice) {
                  ModNetwork.sendToServer(new CBuyFromTraderPacket(this.trader.m_19879_(), this.getSelectedStack().getItemStack(), this.getWantedAmount()));
                  if (!this.getSelectedStack().hasInfiniteStock()) {
                     int count = this.getSelectedStack().getCount() - this.wantedAmount;
                     if (count <= 0) {
                        this.trader.getTradingItems().remove(this.getSelectedStack());
                        this.setSelectedStack((TradeEntry)null);
                        this.setWantedAmount(1);
                        this.quantityEdit.m_94144_("" + this.wantedAmount);
                     } else {
                        this.getSelectedStack().setCount(count);
                     }
                  }

               }
            }
         }
      }
   }

   public void onIncreaseQuantity(Button btn) {
      if (this.getSelectedStack() != null && (this.getWantedAmount() < this.getSelectedStack().getCount() || this.getSelectedStack().hasInfiniteStock())) {
         this.setWantedAmount(this.getWantedAmount() + 1);
      }

   }

   public void onDecreaseQuantity(Button btn) {
      if (this.getSelectedStack() != null && this.getWantedAmount() > 1) {
         this.setWantedAmount(this.getWantedAmount() - 1);
      }

   }

   public void drawSizedString(GuiGraphics graphics, Component text, int x, int y, float scale, int color) {
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)x, (float)y, 0.0F);
      graphics.m_280168_().m_85841_(scale, scale, scale);
      if (color == -1) {
         color = 16777215;
      }

      graphics.m_280653_(this.f_96547_, text, 0, 0, color);
      graphics.m_280168_().m_85849_();
   }

   public void hover(int mouseX, int mouseY) {
      TradeEntry entry = this.listPanel.findStackEntry(mouseX, mouseY);
      if (entry != null) {
         this.hoveredStack = entry;
         this.setWantedAmount(1);
      } else {
         this.hoveredStack = null;
      }

   }

   public int getEmptySlots() {
      int i = 0;

      for(ItemStack stack : this.player.m_150109_().f_35974_) {
         if (stack.m_41619_()) {
            ++i;
         }
      }

      return i;
   }

   public int calculateSlotsFromCount(int count) {
      double val = (double)count / (double)64.0F;
      return Mth.m_14165_(val);
   }

   public List<Integer> getStacks(int count) {
      List<Integer> list = new ArrayList();
      int j = 0;

      for(int i = 0; i < count; i += 64) {
         if (count - 64 * j < 64) {
            list.add(count - 64 * j);
         } else {
            list.add(64);
         }

         ++j;
      }

      return list;
   }

   public void renderTrader(GuiGraphics graphics, int posX, int posY) {
      graphics.m_280168_().m_85836_();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      InventoryScreen.renderEntityInInventoryFollowsAngle(graphics, posX + 150, posY + 150, 100, (float)Math.toRadians((double)40.0F), (float)Math.toRadians((double)5.0F), this.trader);
      RenderSystem.disableBlend();
      graphics.m_280168_().m_85849_();
   }

   public TradeEntry getSelectedStack() {
      return this.selectedStack;
   }

   public void setSelectedStack(TradeEntry selectedStack) {
      this.selectedStack = selectedStack;
   }

   public int getWantedAmount() {
      return this.wantedAmount;
   }

   public void setWantedAmount(int wantedAmount) {
      this.wantedAmount = wantedAmount;
   }

   public boolean m_7043_() {
      return false;
   }

   static enum TradeState {
      Welcome,
      Menu,
      Buy,
      Sell;

      // $FF: synthetic method
      private static TradeState[] $values() {
         return new TradeState[]{Welcome, Menu, Buy, Sell};
      }
   }
}
