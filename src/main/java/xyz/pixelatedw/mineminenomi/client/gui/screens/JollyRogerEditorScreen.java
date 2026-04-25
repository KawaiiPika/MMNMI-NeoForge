package xyz.pixelatedw.mineminenomi.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;


import xyz.pixelatedw.mineminenomi.networking.packets.CUpdateJollyRogerPacket;
import xyz.pixelatedw.mineminenomi.client.gui.components.JollyRogerElementColorComponent;
import xyz.pixelatedw.mineminenomi.client.gui.panels.JollyRogerElementsScrollPanel;


public class JollyRogerEditorScreen extends Screen {
   private static final Component VERTICAL_MIRROR = Component.literal("|");
   private static final Component HORIZONTAL_MIRROR = Component.literal("―");
   private Player player;
   private final Crew crew;
   private JollyRoger jollyRoger;
   private float animationTime = 0.0F;
   private boolean isSmallScreen = false;
   private int trueLayerIndex;
   private int selectedLayerIndex;
   private JollyRogerElement.LayerType selectedLayerType;
   private List<Button> layerButtons;
   private JollyRogerElementsScrollPanel elementsPanel;
   private JollyRogerElementColorComponent elementColorComponent;
   private Button flipVertical;
   private Button flipHorizontal;
   private Button moveLayerUp;
   private Button moveLayerDown;
   private final List<JollyRogerElement> availableElements;
   private final List<JollyRogerElement> availableBases;
   private final List<JollyRogerElement> availableDetails;
   private final List<JollyRogerElement> availableBackgrounds;

   public JollyRogerEditorScreen(boolean isEditing, Crew crew, List<JollyRogerElement> elements) {
      super(Component.empty());
      this.selectedLayerType = JollyRogerElement.LayerType.BASE;
      this.layerButtons = new ArrayList();
      this.crew = crew;
      this.availableElements = elements;
      this.availableBases = this.getTotalElementsForType(JollyRogerElement.LayerType.BASE);
      this.availableDetails = this.getTotalElementsForType(JollyRogerElement.LayerType.DETAIL);
      this.availableBackgrounds = this.getTotalElementsForType(JollyRogerElement.LayerType.BACKGROUND);
      this.animationTime = 1.0F;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(graphics, mouseX, mouseY, partialTicks);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = this.width / 2;
      int posY = this.height / 2;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      int screenSizeBonus = this.isSmallScreen ? -30 : 0;
      if (this.jollyRoger != null) {
         this.jollyRoger.render(graphics, posX - 98 - screenSizeBonus, posY - 64, 0.4F);
      }

      graphics.pose().pushPose();
      if ((double)this.animationTime < 0.15) {
         this.animationTime = (float)((double)this.animationTime + 0.007);
         float scale = 0.35F + this.animationTime;
         graphics.pose().translate((float)(posX - 175 - screenSizeBonus), (float)(posY - 140), 1.0F);
         graphics.pose().translate(128.0F, 128.0F, 0.0F);
         graphics.pose().scale(scale, scale, scale);
         graphics.pose().translate(-128.0F, -128.0F, 0.0F);
         JollyRogerElement element = this.getLayerElement();
         if (element != null) {
            float red = 1.0F;
            float green = 0.0F;
            float blue = 0.0F;
            if (element.canBeColored()) {
               red = element.getInversedRedF();
               green = element.getInversedGreenF();
               blue = element.getInversedBlueF();
            }

            RenderSystem.setShaderColor(red, green, blue, 0.9F - this.animationTime * 4.0F);
            this.jollyRoger.drawElement(element, graphics, 0, 0, 1.0F, false);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

      graphics.pose().popPose();
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      super.render(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      this.player = this.minecraft.player;
      this.jollyRoger = this.crew.getJollyRoger();
      int posX = 0;
      int posY = this.height / 2;
      this.layerButtons.clear();
      int elementsPerRow = 3;
      if (this.width < 640) {
         elementsPerRow = 2;
         this.isSmallScreen = true;
      }

      int listWidth = elementsPerRow * 72;
      this.elementsPanel = new JollyRogerElementsScrollPanel(this, listWidth, elementsPerRow);
      this.addRenderableWidget(this.elementsPanel);

      this.elementColorComponent = new JollyRogerElementColorComponent(this, this.width - 200, this.height - 100);
      this.elementColorComponent.setVisible(true);
      this.addRenderableWidget(this.elementColorComponent);
      this.updateElementComponents(this.jollyRoger.getBase());
      int listPosY = posY - 85;
      this.addLayerButton(net.minecraft.client.gui.components.Button.builder(Component.translatable("gui.base"), btn -> this.selectLayer(btn, 0, JollyRogerElement.LayerType.BASE)).bounds(posX + 5, listPosY, 115, 16).build());
      int bgLen = this.jollyRoger.getBackgrounds().length;

      for(int i = 0; i < bgLen; ++i) {
         int id = i + 1;
         int var10002 = posX + 5;
         int var10003 = listPosY + 20 + i * 20;
         String var10006 = Component.translatable("gui.background").getString();
         net.minecraft.client.gui.components.Button bgButton = net.minecraft.client.gui.components.Button.builder(Component.literal(var10006 + " " + (i + 1)), btn -> this.selectLayer(btn, id, JollyRogerElement.LayerType.BACKGROUND)).bounds(var10002, var10003, 115, 16).build();
         this.addLayerButton(bgButton);
      }

      for(int i = 0; i < this.jollyRoger.getDetails().length; ++i) {
         int id = i + 1 + bgLen;
         int var14 = posX + 5;
         int var15 = listPosY + 60 + i * 20;
         String var16 = Component.translatable("gui.detail").getString();
         net.minecraft.client.gui.components.Button detailButton = net.minecraft.client.gui.components.Button.builder(Component.literal(var16 + " " + (i + 1)), btn -> this.selectLayer(btn, id, JollyRogerElement.LayerType.DETAIL)).bounds(var14, var15, 115, 16).build();
         this.addLayerButton(detailButton);
      }

      Button currentlySelectedButton = (Button)this.layerButtons.get(this.trueLayerIndex);
      if (currentlySelectedButton != null) {
         currentlySelectedButton.active = false;
      }

      this.updateLayerMoveButtons();
   }

   public void init() {
      net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CUpdateJollyRogerPacket(this.jollyRoger));
      super.init();
   }

   public boolean mouseScrolled(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
      if (this.isPauseScreen()) {
         JollyRogerElement currentElement = this.getLayerElement();
         if (currentElement != null && currentElement.canBeColored()) {
            currentElement.setColor(this.elementColorComponent.getRed(), this.elementColorComponent.getGreen(), this.elementColorComponent.getBlue());
         }
      }

      return super.mouseScrolled(p_94699_, p_94700_, p_94701_, p_94702_);
   }

   public void flipLayer(Button btn, boolean vertical, boolean horizontal) {
      JollyRogerElement currentElement = this.getLayerElement();
      if (currentElement != null) {
         if (vertical && currentElement.canBeFlippedVertically()) {
            currentElement.setFlipVertically(!currentElement.isFlippedVertically());
         }

         if (horizontal && currentElement.canBeFlippedHorizontally()) {
            currentElement.setFlipHorizontally(!currentElement.isFlippedHorizontally());
         }
      }

   }

   public void selectLayer(Button btn, int idx, JollyRogerElement.LayerType layer) {
      for(Button other : this.layerButtons) {
         other.active = true;
      }

      ((Button)this.layerButtons.get(idx)).active = false;
      this.trueLayerIndex = idx;
      int bgLen = this.jollyRoger.getBackgrounds().length;
      if (!layer.equals(this.selectedLayerType)) {
         this.elementsPanel.setEntries(layer);
      }

      this.selectedLayerType = layer;
      int var10001;
      switch (layer) {
         case BASE -> var10001 = 0;
         case BACKGROUND -> var10001 = idx - 1;
         case DETAIL -> var10001 = idx - 1 - bgLen;
         default -> throw new IncompatibleClassChangeError();
      }

      this.selectedLayerIndex = var10001;
      JollyRogerElement element = this.getLayerElement();
      this.updateElementComponents(element);
      this.animationTime = 0.0F;
      this.updateLayerMoveButtons();
   }

   public void moveLayer(Button btn, boolean up) {
      int nextLayer = this.selectedLayerIndex + (up ? -1 : 1);
      int bgLen = this.jollyRoger.getBackgrounds().length;
      switch (this.getSelectedLayerType()) {
         case BASE:
         default:
            break;
         case BACKGROUND:
            JollyRogerElement oldElem = this.jollyRoger.getBackgrounds()[this.selectedLayerIndex];
            this.jollyRoger.getBackgrounds()[this.selectedLayerIndex] = this.jollyRoger.getBackgrounds()[nextLayer];
            this.jollyRoger.getBackgrounds()[nextLayer] = oldElem;
            this.selectLayer(btn, nextLayer + 1, this.selectedLayerType);
            break;
         case DETAIL:
            oldElem = this.jollyRoger.getDetails()[this.selectedLayerIndex];
            this.jollyRoger.getDetails()[this.selectedLayerIndex] = this.jollyRoger.getDetails()[nextLayer];
            this.jollyRoger.getDetails()[nextLayer] = oldElem;
            this.selectLayer(btn, nextLayer + 1 + bgLen, this.selectedLayerType);
      }

   }

   public void updateJollyRoger(JollyRogerElement element) {
      boolean var10000;
      switch (this.selectedLayerType) {
         case BASE:
            if (this.jollyRoger.getBase().equals(element)) {
               var10000 = false;
            } else {
               this.jollyRoger.setBase(element);

               for(int i = 0; i < this.jollyRoger.getDetails().length; ++i) {
                  JollyRogerElement detail = this.jollyRoger.getDetails()[i];
                  if (detail != null && !detail.canUse(this.player, this.crew)) {
                     this.jollyRoger.setDetail(i, (JollyRogerElement)null);
                  }
               }

               for(int i = 0; i < this.jollyRoger.getBackgrounds().length; ++i) {
                  JollyRogerElement background = this.jollyRoger.getBackgrounds()[i];
                  if (background != null && !background.canUse(this.player, this.crew)) {
                     this.jollyRoger.setBackground(i, (JollyRogerElement)null);
                  }
               }

               var10000 = true;
            }
            break;
         case BACKGROUND:
            var10000 = this.jollyRoger.setBackground(this.selectedLayerIndex, element);
            break;
         case DETAIL:
            var10000 = this.jollyRoger.setDetail(this.selectedLayerIndex, element);
            break;
         default:
            var10000 = false;
      }

      boolean hasUpdated = var10000;
      if (hasUpdated) {
         this.updateElementComponents(element);
      }

   }

   public void updateLayerMoveButtons() {
      int posX = 0;
      int posY = this.height / 2;
      int layerMoveY = posY - 85 + this.trueLayerIndex * 20;
      if (this.moveLayerUp != null) {
         this.renderables.remove(this.moveLayerUp);
         this.addRenderableOnly(this.moveLayerUp);
      }

      if (this.moveLayerDown != null) {
         this.renderables.remove(this.moveLayerDown);
         this.addRenderableOnly(this.moveLayerDown);
      }

      boolean isBase = this.getSelectedLayerType() == JollyRogerElement.LayerType.BASE;
      boolean isTop = this.selectedLayerIndex == 0;
      boolean isBot = this.selectedLayerIndex == (this.getSelectedLayerType() == JollyRogerElement.LayerType.BACKGROUND ? 2 : 5) - 1;
      if (!isBase) {
         if (!isTop) {
            this.moveLayerUp = net.minecraft.client.gui.components.Button.builder(Component.literal("+"), btn -> this.moveLayer(btn, true)).bounds(posX + 125, layerMoveY - 7, 16, 16).build();
            this.addRenderableWidget(this.moveLayerUp);
         }

         if (!isBot) {
            this.moveLayerDown = net.minecraft.client.gui.components.Button.builder(Component.literal("-"), btn -> this.moveLayer(btn, false)).bounds(posX + 125, layerMoveY + 7, 16, 16).build();
            this.addRenderableWidget(this.moveLayerDown);
         }

      }
   }

   public void updateElementComponents(@Nullable JollyRogerElement element) {
      if (element != null) {
         this.elementColorComponent.setVisible(element.canBeColored());
         if (this.flipVertical != null) {
            this.renderables.remove(this.flipVertical);
            this.addRenderableOnly(this.flipVertical);
         }

         if (this.flipHorizontal != null) {
            this.renderables.remove(this.flipHorizontal);
            this.addRenderableOnly(this.flipHorizontal);
         }

         if (element.canBeFlippedVertically()) {
            this.flipVertical = net.minecraft.client.gui.components.Button.builder(VERTICAL_MIRROR, btn -> this.flipLayer(btn, true, false)).bounds(this.width - 270, this.height - 130, 16, 16).build();
            this.addRenderableWidget(this.flipVertical);
         }

         if (element.canBeFlippedHorizontally()) {
            this.flipHorizontal = net.minecraft.client.gui.components.Button.builder(HORIZONTAL_MIRROR, btn -> this.flipLayer(btn, false, true)).bounds(this.width - 240, this.height - 130, 16, 16).build();
            this.addRenderableWidget(this.flipHorizontal);
         }
      }

      this.elementColorComponent.setRGBValues(element);
   }

   private JollyRogerElement getLayerElement() {
      JollyRogerElement var10000;
      switch (this.selectedLayerType) {
         case BASE -> var10000 = this.jollyRoger.getBase();
         case BACKGROUND -> var10000 = this.jollyRoger.getBackgrounds()[this.selectedLayerIndex];
         case DETAIL -> var10000 = this.jollyRoger.getDetails()[this.selectedLayerIndex];
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }

   public List<JollyRogerElement> getListFromType(JollyRogerElement.LayerType type) {
      List var10000;
      switch (type) {
         case BASE -> var10000 = this.availableBases;
         case BACKGROUND -> var10000 = this.availableBackgrounds;
         case DETAIL -> var10000 = this.availableDetails;
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }

   public List<JollyRogerElement> getTotalElementsForType(JollyRogerElement.LayerType type) {
      return this.availableElements.stream().filter((reg) -> reg.getLayerType() == type).toList();
   }

   private void addLayerButton(Button btn) {
      this.layerButtons.add(btn);
      super.addRenderableWidget(btn);
   }

   public Player getPlayer() {
      return this.player;
   }

   public Crew getCrew() {
      return this.crew;
   }

   public JollyRogerElement.LayerType getSelectedLayerType() {
      return this.selectedLayerType;
   }

   public boolean isSmallScreen() {
      return this.isSmallScreen;
   }
}
