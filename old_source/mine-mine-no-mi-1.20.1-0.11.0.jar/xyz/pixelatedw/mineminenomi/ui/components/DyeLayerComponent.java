package xyz.pixelatedw.mineminenomi.ui.components;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.ui.CSetDyeLayerPacket;
import xyz.pixelatedw.mineminenomi.ui.widget.SimpleButton;

public class DyeLayerComponent implements Renderable, GuiEventListener, NarratableEntry {
   private Minecraft minecraft;
   private CraftingScreen parentScreen;
   private List<GuiEventListener> buttons = new ArrayList();
   private List<Renderable> renderable = new ArrayList();
   private boolean isVisible = false;
   private int layer;
   private int maxLayers = 2;
   private int lastLeftPos;
   private int posX;
   private int posY;

   public DyeLayerComponent(Minecraft minecraft, CraftingScreen parentScreen, int posX, int posY) {
      this.minecraft = minecraft;
      this.parentScreen = parentScreen;
      this.posX = posX;
      this.posY = posY;
      this.lastLeftPos = parentScreen.getGuiLeft();
      this.init();
   }

   public void init() {
      this.buttons.clear();
      this.renderable.clear();
      SimpleButton forwardLayerBtn = new SimpleButton(this.posX + 18, this.posY, 10, 8, Component.m_237113_("▷"), this::forwardEvent);
      forwardLayerBtn.setTextOnly();
      this.addWidget(forwardLayerBtn);
      SimpleButton backwardLayerBtn = new SimpleButton(this.posX + 5, this.posY, 10, 8, Component.m_237113_("◁"), this::backwardEvent);
      backwardLayerBtn.setTextOnly();
      this.addWidget(backwardLayerBtn);
   }

   private void forwardEvent(Button btn) {
      this.layer = Mth.m_14045_(this.layer + 1, 0, this.maxLayers - 1);
      ModNetwork.sendToServer(new CSetDyeLayerPacket(this.layer));
   }

   private void backwardEvent(Button btn) {
      this.layer = Mth.m_14045_(this.layer - 1, 0, this.maxLayers - 1);
      ModNetwork.sendToServer(new CSetDyeLayerPacket(this.layer));
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isVisible) {
         if (this.lastLeftPos != this.parentScreen.getGuiLeft()) {
            this.lastLeftPos = this.parentScreen.getGuiLeft();
            this.posX = this.lastLeftPos;
            this.init();
         }

         int layer = Math.min(this.layer, this.maxLayers - 1);
         Component title = Component.m_237113_("" + (layer + 1));
         int posX = this.posX - this.minecraft.f_91062_.m_92852_(title) / 2;
         graphics.m_280614_(this.minecraft.f_91062_, title, posX + 16, this.posY - 10, 4210752, false);

         for(Renderable btn : this.renderable) {
            btn.m_88315_(graphics, mouseX, mouseY, partialTicks);
         }

      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int mouseType) {
      if (!this.isVisible) {
         return false;
      } else {
         for(GuiEventListener btn : this.buttons) {
            boolean flag = btn.m_6375_(mouseX, mouseY, mouseType);
            if (flag) {
               return true;
            }
         }

         return false;
      }
   }

   public <T extends GuiEventListener & Renderable> void addWidget(T widget) {
      this.buttons.add(widget);
      this.renderable.add(widget);
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_93692_(boolean p_265728_) {
   }

   public boolean m_93696_() {
      return false;
   }

   public void m_142291_(NarrationElementOutput output) {
   }

   public void setLayer(int layer) {
      this.layer = layer;
   }

   public void setMaxLayers(int layers) {
      this.maxLayers = layers;
   }

   public void setVisible(boolean flag) {
      this.isVisible = flag;
   }
}
