package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nTutorials;
import xyz.pixelatedw.mineminenomi.packets.client.ability.node.CUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.ui.widget.AnimatedTextButton;

public class AbilityTreeScreen extends Screen {
   private static final int NODE_SIZE = 16;
   private static final int MARGIN_SIZE = 2;
   private static final int GRID_STEP = 20;
   private static final float LINE_THICKNESS = 15.0F;
   private float zoom = 1.0F;
   private LocalPlayer player;
   private IAbilityData abilityProps;
   private boolean editMode = false;
   private AbilityNode draggingNode;
   private AbilityNode hoveredNode;
   private AbilityNode clickedNode;
   private Map<AbilityNode, AbilityNode.NodePos> editedPositions = new HashMap();
   private float dragNodeOffsetX = 0.0F;
   private float dragNodeOffsetY = 0.0F;
   private float dragOffsetX = 0.0F;
   private float dragOffsetY = 0.0F;
   private float dragOriginX;
   private float dragOriginY;
   private float dragStartX;
   private float dragStartY;
   private boolean isDragging;
   private int dragTicks;
   private int iconRadius;
   private int marginScaled;
   private float gridStepScaled;
   private int centerX;
   private int centerY;

   public AbilityTreeScreen() {
      super(Component.m_237119_());
      this.dragOriginX = this.dragOffsetX;
      this.dragOriginY = this.dragOffsetY;
      this.dragStartX = -1.0F;
      this.dragStartY = -1.0F;
      this.isDragging = true;
      this.dragTicks = 0;
   }

   public void m_7856_() {
      Minecraft mc = super.getMinecraft();
      this.player = mc.f_91074_;
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.getLazy(this.player).orElse((Object)null);
      if (abilityProps == null) {
         super.m_7379_();
      }

      this.abilityProps = abilityProps;
      if (WyDebug.isDebug()) {
         this.m_142416_(Button.m_253074_(Component.m_237113_("Edit Mode: " + (this.editMode ? "On" : "Off")), (button) -> {
            this.editMode = !this.editMode;
            button.m_93666_(Component.m_237113_("Edit Mode: " + (this.editMode ? "On" : "Off")));
         }).m_252794_(10, 10).m_253046_(100, 20).m_253136_());

         for(AbilityNode node : this.abilityProps.getNodes()) {
            this.editedPositions.put(node, node.getPosition());
         }
      }

      int posX = this.f_96543_;
      AnimatedTextButton helpButton = new AnimatedTextButton(posX - 20, this.f_96544_ - 20, 20, 20, ModI18n.GUI_HELP_SHORT, (btn) -> {
      });
      helpButton.m_257544_(this.getHelpTooltips());
      if (ClientConfig.getHelpButtonShown()) {
         this.m_142416_(helpButton);
      }

   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.m_280273_(graphics);
      if (WyDebug.isDebug() && !this.editMode) {
         this.m_7522_((GuiEventListener)null);
      }

      Minecraft mc = super.getMinecraft();
      LocalPlayer player = mc.f_91074_;
      this.iconRadius = Math.round(16.0F * this.zoom / 2.0F);
      this.marginScaled = Math.round(2.0F * this.zoom);
      this.gridStepScaled = 20.0F * this.zoom;
      this.hoveredNode = null;

      for(AbilityNode node : this.abilityProps.getNodes()) {
         graphics.m_280168_().m_85836_();
         AbilityNode.NodePos pos = this.getNodePosition(node);
         this.centerX = this.getCenteredDragX(pos);
         this.centerY = this.getCenteredDragY(pos);
         int colour = -7829368;
         int backgroundColour = 0;
         if (node.isUnlocked(player)) {
            colour = -15486189;
            backgroundColour = -15826930;
         } else if (node.canUnlock(player)) {
            colour = -23296;
            backgroundColour = -3374080;
         } else {
            colour = -4713452;
            backgroundColour = -8057073;
         }

         int fillMinX = this.centerX - this.iconRadius - this.marginScaled;
         int fillMinY = this.centerY - this.iconRadius - this.marginScaled;
         int fillMaxX = this.centerX + this.iconRadius + this.marginScaled;
         int fillMaxY = this.centerY + this.iconRadius + this.marginScaled;
         graphics.m_280046_(fillMinX, fillMinY, fillMaxX, fillMaxY, 10, backgroundColour);
         graphics.m_280046_(fillMinX + 2, fillMinY + 2, fillMaxX - 2, fillMaxY - 2, 15, colour);
         int iconLeft = this.centerX - this.iconRadius;
         int iconTop = this.centerY - this.iconRadius;
         int iconSizeI = this.iconRadius * 2;
         RendererHelper.drawIcon(node.getIcon(), graphics.m_280168_(), (float)iconLeft, (float)iconTop, 15.0F, (float)iconSizeI, (float)iconSizeI);
         if (!this.isDragging && mouseX >= this.centerX - this.iconRadius && mouseX <= this.centerX + this.iconRadius && mouseY >= this.centerY - this.iconRadius && mouseY <= this.centerY + this.iconRadius) {
            this.hoveredNode = node;
            if (this.draggingNode == null) {
               this.drawNodeTooltip(graphics, this.hoveredNode);
            }

            int overlayMinX = this.centerX - this.iconRadius;
            int overlayMinY = this.centerY - this.iconRadius;
            int overlayMaxX = this.centerX + this.iconRadius;
            int overlayMaxY = this.centerY + this.iconRadius;
            graphics.m_280046_(overlayMinX, overlayMinY, overlayMaxX, overlayMaxY, 20, 1090519039);
            if (this.editMode) {
               AbilityNode.NodePos hoveredPos = (AbilityNode.NodePos)this.editedPositions.getOrDefault(node, node.getPosition());
               float var10000 = hoveredPos.x();
               String label = "Grid Position: (" + var10000 + ", " + hoveredPos.y() + ")";
               int labelX = super.f_96543_ / 2 - super.f_96547_.m_92895_(label) / 2;
               int labelY = 5;
               graphics.m_280056_(super.f_96547_, label, labelX, labelY, 16777215, true);
            }
         }

         graphics.m_280168_().m_85837_((double)0.0F, (double)0.0F, (double)-30.0F);

         for(AbilityNode parentNode : node.getPrerequisites()) {
            AbilityNode.NodePos parentPos = this.getNodePosition(parentNode);
            float parentCenterXF = (float)this.getCenteredDragX(parentPos);
            float parentCenterYF = (float)this.getCenteredDragY(parentPos);
            this.drawLine(graphics, parentNode, node, parentCenterXF, parentCenterYF, (float)this.centerX, (float)this.centerY, -8355712, 15.0F * this.zoom);
         }

         graphics.m_280168_().m_85849_();
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTick);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      this.dragStartX = (float)mouseX;
      this.dragStartY = (float)mouseY;
      this.dragOriginX = this.dragOffsetX;
      this.dragOriginY = this.dragOffsetY;
      if (button == 0) {
         if (this.hoveredNode != null) {
            this.clickedNode = this.hoveredNode;
         }

         if (this.editMode) {
            for(Map.Entry<AbilityNode, AbilityNode.NodePos> entry : this.editedPositions.entrySet()) {
               AbilityNode node = (AbilityNode)entry.getKey();
               AbilityNode.NodePos pos = (AbilityNode.NodePos)entry.getValue();
               float centerX = (float)this.getCenteredDragX(pos);
               float centerY = (float)this.getCenteredDragY(pos);
               if (mouseX >= (double)(centerX - (float)this.iconRadius) && mouseX <= (double)(centerX + (float)this.iconRadius) && mouseY >= (double)(centerY - (float)this.iconRadius) && mouseY <= (double)(centerY + (float)this.iconRadius)) {
                  this.draggingNode = node;
                  this.dragNodeOffsetX = (float)mouseX - centerX;
                  this.dragNodeOffsetY = (float)mouseY - centerY;
                  return true;
               }
            }
         }

         this.dragStartX = this.getScreenCenterX();
         this.dragStartY = this.getScreenCenterY();
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_7979_(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.dragStartX != -1.0F && this.dragStartY != -1.0F) {
         if (this.clickedNode != null) {
            return false;
         } else {
            this.isDragging = true;
            ++this.dragTicks;
            if (button == 0) {
               if (this.editMode && this.draggingNode != null) {
                  float newCenterX = (float)mouseX - this.dragNodeOffsetX - this.dragOffsetX * this.zoom;
                  float newCenterY = (float)mouseY - this.dragNodeOffsetY - this.dragOffsetY * this.zoom;
                  float exactGridX = (newCenterX * 2.0F - (float)this.f_96543_) / (2.0F * this.gridStepScaled);
                  float exactGridY = (newCenterY * 2.0F - (float)this.f_96544_) / (2.0F * this.gridStepScaled);
                  float snappedGridX = (float)Math.round(exactGridX);
                  float snappedGridY = (float)Math.round(exactGridY);
                  this.editedPositions.put(this.draggingNode, new AbilityNode.NodePos(snappedGridX, snappedGridY));
                  return true;
               }

               double posX = (double)(this.f_96541_.m_91268_().m_85443_() / 2);
               double posY = (double)(this.f_96541_.m_91268_().m_85444_() / 2);
               InputConstants.m_84833_(this.f_96541_.m_91268_().m_85439_(), 212995, posX, posY);
               if (this.dragTicks <= 1) {
                  return false;
               }

               float dragSpeedX = (float)(mouseX - (double)this.dragStartX);
               float dragSpeedY = (float)(mouseY - (double)this.dragStartY);
               this.dragOffsetX += dragSpeedX / this.zoom;
               this.dragOffsetY += dragSpeedY / this.zoom;
               this.dragStartX = this.getScreenCenterX();
               this.dragStartY = this.getScreenCenterY();
            }

            return super.m_7979_(mouseX, mouseY, button, dragX, dragY);
         }
      } else {
         return super.m_7979_(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      if (button == 0) {
         GLFW.glfwSetInputMode(this.f_96541_.m_91268_().m_85439_(), 208897, 212993);
         if (!this.isDragging && this.clickedNode != null && this.hoveredNode == this.clickedNode) {
            Pair<AbilityCore<? extends IAbility>, Integer> pair = this.abilityProps.getCoreIndexPair(this.clickedNode);
            if (pair != null) {
               ModNetwork.sendToServer(new CUnlockAbilityNodePacket((AbilityCore)pair.getKey(), (Integer)pair.getValue()));
            }
         }

         this.clickedNode = null;
         this.draggingNode = null;
      }

      this.dragStartX = -1.0F;
      this.dragStartY = -1.0F;
      this.isDragging = false;
      this.dragTicks = 0;
      return super.m_6348_(mouseX, mouseY, button);
   }

   public boolean m_6050_(double mouseX, double mouseY, double delta) {
      this.zoom = Mth.m_14036_(this.zoom + 0.1F * (float)delta, 0.2F, 5.0F);
      return true;
   }

   public boolean m_7043_() {
      return false;
   }

   private static AbilityNode.NodePos clipToSquareEdge(float cx, float cy, float tx, float ty, float halfSize) {
      float dx = tx - cx;
      float dy = ty - cy;
      if (dx == 0.0F && dy == 0.0F) {
         return new AbilityNode.NodePos(cx, cy);
      } else {
         float absDx = Math.abs(dx);
         float absDy = Math.abs(dy);
         float scale;
         if (absDx > absDy) {
            scale = halfSize / absDx;
         } else {
            scale = halfSize / absDy;
         }

         float clippedX = cx + dx * scale;
         float clippedY = cy + dy * scale;
         return new AbilityNode.NodePos(clippedX, clippedY);
      }
   }

   private void drawLine(GuiGraphics graphics, AbilityNode parentNode, AbilityNode node, float startPosX, float startPosY, float endPosX, float endPosY, int color, float thickness) {
      double dx = (double)(endPosX - startPosX);
      double dy = (double)(endPosY - startPosY);
      double len = Math.sqrt(dx * dx + dy * dy);
      if (len != (double)0.0F) {
         RenderSystem.enableBlend();
         RenderSystem.setShader(GameRenderer::m_172757_);
         BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
         Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
         buffer.m_166779_(Mode.LINE_STRIP, DefaultVertexFormat.f_166851_);
         RenderSystem.lineWidth(thickness);
         int a = color >> 24 & 255;
         int r = color >> 16 & 255;
         int g = color >> 8 & 255;
         int b = color & 255;
         int unlockedCount = 0;
         if (node.isUnlocked(this.player)) {
            r = 0;
            g = 128;
            b = 0;
            ++unlockedCount;
         }

         buffer.m_252986_(matrix, startPosX, startPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)((int)dx), (float)((int)dy), 0.0F).m_5752_();
         buffer.m_252986_(matrix, endPosX, endPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)(-((int)dx)), (float)(-((int)dy)), 0.0F).m_5752_();
         buffer.m_252986_(matrix, startPosX, startPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)((int)dx), (float)((int)dy), 0.0F).m_5752_();
         BufferUploader.m_231202_(buffer.m_231175_());
         buffer.m_166779_(Mode.LINE_STRIP, DefaultVertexFormat.f_166851_);
         RenderSystem.lineWidth(thickness / 4.0F);
         if (parentNode.isUnlocked(this.player)) {
            r = 0;
            g = 179;
            b = 0;
            ++unlockedCount;
         } else {
            r = (int)((float)r * 1.2F);
            g = (int)((float)g * 1.2F);
            b = (int)((float)b * 1.2F);
         }

         if (unlockedCount >= 2) {
            RenderSystem.lineWidth(thickness / 2.0F);
         }

         buffer.m_252986_(matrix, startPosX, startPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)((int)dx), (float)((int)dy), 0.0F).m_5752_();
         buffer.m_252986_(matrix, endPosX, endPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)(-((int)dx)), (float)(-((int)dy)), 0.0F).m_5752_();
         buffer.m_252986_(matrix, startPosX, startPosY, 0.0F).m_6122_(r, g, b, a).m_5601_((float)((int)dx), (float)((int)dy), 0.0F).m_5752_();
         BufferUploader.m_231202_(buffer.m_231175_());
         RenderSystem.disableBlend();
         RenderSystem.lineWidth(1.0F);
      }
   }

   private void drawNodeTooltip(GuiGraphics graphics, AbilityNode node) {
      int iconLeft = this.centerX - this.iconRadius;
      int iconTop = this.centerY - this.iconRadius;
      int iconSize = this.iconRadius * 2;
      List<Component> components = List.of(node.getLocalizedName(), Component.m_237113_(" "), node.getTooltip());
      List<FormattedCharSequence> lines = new ArrayList();
      int maxWidth = 200;

      for(Component component : components) {
         lines.addAll(super.f_96547_.m_92923_(component, maxWidth));
      }

      Stream var10000 = lines.stream();
      Font var10001 = super.f_96547_;
      Objects.requireNonNull(var10001);
      int tooltipWidth = var10000.mapToInt(var10001::m_92724_).max().orElse(0);
      Objects.requireNonNull(this.f_96547_);
      int lineHeight = 9;
      int tooltipHeight = lines.size() * lineHeight;
      int gapPixels = 8;
      int tooltipX = iconLeft + iconSize + gapPixels;
      int tooltipY = iconTop + 2;
      int padding = 4;
      int backgroundColor = -267386864;
      int borderColorStart = 1347420415;
      int borderColorEnd = 1344798847;
      int x1 = tooltipX - padding;
      int y1 = tooltipY - padding;
      int z1 = 50;
      int x2 = tooltipX + tooltipWidth + padding;
      int y2 = tooltipY + tooltipHeight + padding;
      if (x2 > this.f_96543_) {
         tooltipX = iconLeft - gapPixels - tooltipWidth - padding * 2;
         x1 = tooltipX - padding;
         x2 = tooltipX + tooltipWidth + padding;
      }

      if (y2 > this.f_96544_) {
         tooltipY = Math.max(2, this.f_96544_ - tooltipHeight - padding);
         y1 = tooltipY - padding;
         y2 = tooltipY + tooltipHeight + padding;
      }

      graphics.m_280120_(x1, y1, x2, y2, z1, backgroundColor, backgroundColor);
      graphics.m_280120_(x1, y1, x1 + 1, y2, z1, borderColorStart, borderColorEnd);
      graphics.m_280120_(x2 - 1, y1, x2, y2, z1, borderColorStart, borderColorEnd);
      graphics.m_280120_(x1, y1, x2, y1 + 1, z1, borderColorStart, borderColorStart);
      graphics.m_280120_(x1, y2 - 1, x2, y2, z1, borderColorEnd, borderColorEnd);
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_(0.0F, 0.0F, (float)z1);

      for(int i = 0; i < lines.size(); ++i) {
         graphics.m_280649_(super.f_96547_, (FormattedCharSequence)lines.get(i), tooltipX, tooltipY + i * lineHeight, -1, false);
      }

      graphics.m_280168_().m_85849_();
   }

   private int getCenteredDragX(AbilityNode.NodePos pos) {
      return Math.round(this.dragOffsetX * this.zoom + (float)this.f_96543_ / 2.0F + this.gridStepScaled * pos.x());
   }

   private int getCenteredDragY(AbilityNode.NodePos pos) {
      return Math.round(this.dragOffsetY * this.zoom + (float)this.f_96544_ / 2.0F + this.gridStepScaled * pos.y());
   }

   private AbilityNode.NodePos getNodePosition(AbilityNode node) {
      return this.editMode ? (AbilityNode.NodePos)this.editedPositions.getOrDefault(node, node.getPosition()) : node.getPosition();
   }

   private final float getScreenCenterX() {
      double posX = (double)(this.f_96541_.m_91268_().m_85443_() / 2);
      return (float)(posX * (double)this.f_96541_.m_91268_().m_85445_() / (double)this.f_96541_.m_91268_().m_85443_());
   }

   private final float getScreenCenterY() {
      double posY = (double)(this.f_96541_.m_91268_().m_85444_() / 2);
      return (float)(posY * (double)this.f_96541_.m_91268_().m_85446_() / (double)this.f_96541_.m_91268_().m_85444_());
   }

   private Tooltip getHelpTooltips() {
      StringBuilder sb = new StringBuilder();
      sb.append(ModI18nTutorials.ABILITY_TREE.getString());
      return Tooltip.m_257550_(Component.m_237113_(sb.toString()));
   }
}
