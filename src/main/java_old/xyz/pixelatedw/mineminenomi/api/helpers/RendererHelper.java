package xyz.pixelatedw.mineminenomi.api.helpers;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL11;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class RendererHelper {
   public static final ResourceLocation GUI_ICONS_LOCATION = ResourceLocation.parse("textures/gui/icons.png");
   private static final Color TOOLTIP_ICON_COLOR = WyHelper.hexToRGB("#333333");
   public static final ResourceLocation FORCEFIELD_LOCATION = ResourceLocation.parse("textures/misc/forcefield.png");
   private static final Color WORLD_GOVT_COLOR = WyHelper.hexToRGB("#2B497B");

   public static void drawPlayerFactionIcon(Faction faction, @Nullable Crew crew, @Nullable LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int lightmap) {
      if (faction.equals(ModFactions.PIRATE.get()) && crew != null) {
         RenderSystem.enableDepthTest();
         crew.getJollyRoger().render(matrixStack, 0, 0, 0.0035F);
         RenderSystem.disableDepthTest();
      } else if (faction.equals(ModFactions.REVOLUTIONARY_ARMY.get())) {
         matrixStack.m_85837_((double)-0.5F, (double)-0.5F, (double)0.0F);
         matrixStack.m_85841_(4.0F, 4.0F, 1.0F);
         VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(ModResources.REVOLUTIONARY_ARMY_ICON_GREYSCALE));
         drawQuad(matrixStack, ivertexbuilder, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.0F, 1.0F, 0.0F, 1.0F, lightmap);
      } else if (faction.equals(ModFactions.MARINE.get())) {
         matrixStack.m_85837_((double)-0.5F, (double)-0.5F, (double)0.0F);
         matrixStack.m_85841_(4.0F, 4.0F, 1.0F);
         VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(ModResources.MARINE_ICON));
         drawQuad(matrixStack, ivertexbuilder, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.0F, 1.0F, 0.0F, 1.0F, lightmap);
      } else if (faction.equals(ModFactions.WORLD_GOVERNMENT.get())) {
         matrixStack.m_85837_((double)-0.5F, (double)-0.5F, (double)0.0F);
         matrixStack.m_85841_(4.0F, 4.0F, 1.0F);
         VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(ModResources.WORLD_GOV_ICON));
         drawQuad(matrixStack, ivertexbuilder, (float)WORLD_GOVT_COLOR.getRed() / 255.0F, (float)WORLD_GOVT_COLOR.getGreen() / 255.0F, (float)WORLD_GOVT_COLOR.getBlue() / 255.0F, 1.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.0F, 1.0F, 0.0F, 1.0F, lightmap);
      }

   }

   public static List<ModelPart> getArmPartsFrom(EntityModel<?> model) {
      return getArmPartsFrom(model, HumanoidArm.RIGHT);
   }

   public static List<ModelPart> getArmPartsFrom(EntityModel<?> model, HumanoidArm side) {
      List<ModelPart> limbs = new ArrayList();
      if (model instanceof HumanoidModel humanoidModel) {
         limbs.add(side == HumanoidArm.RIGHT ? humanoidModel.f_102811_ : humanoidModel.f_102812_);
         if (humanoidModel instanceof PlayerModel playerModel) {
            limbs.add(side == HumanoidArm.RIGHT ? playerModel.f_103375_ : playerModel.f_103374_);
         }
      } else if (model instanceof DugongModel dugongModel) {
         limbs.add(side == HumanoidArm.RIGHT ? dugongModel.rightArm : dugongModel.leftArm);
      }

      return limbs;
   }

   public static List<ModelPart> getLegPartsFrom(EntityModel<?> model) {
      return getLegPartsFrom(model, HumanoidArm.RIGHT);
   }

   public static List<ModelPart> getLegPartsFrom(EntityModel<?> model, HumanoidArm side) {
      List<ModelPart> limbs = new ArrayList();
      if (model instanceof HumanoidModel humanoidModel) {
         limbs.add(side == HumanoidArm.RIGHT ? humanoidModel.f_102813_ : humanoidModel.f_102814_);
         if (humanoidModel instanceof PlayerModel playerModel) {
            limbs.add(side == HumanoidArm.RIGHT ? playerModel.f_103377_ : playerModel.f_103376_);
         }
      } else if (model instanceof DugongModel dugongModel) {
         limbs.add(dugongModel.tailBase);
      }

      return limbs;
   }

   public static <A extends HumanoidModel<E>, E extends LivingEntity> void setPartVisibility(A model, EquipmentSlot slot) {
      model.m_8009_(false);
      switch (slot) {
         case HEAD:
            model.f_102808_.f_104207_ = true;
            model.f_102809_.f_104207_ = true;
            break;
         case CHEST:
            model.f_102810_.f_104207_ = true;
            model.f_102811_.f_104207_ = true;
            model.f_102812_.f_104207_ = true;
            break;
         case LEGS:
            model.f_102810_.f_104207_ = true;
            model.f_102813_.f_104207_ = true;
            model.f_102814_.f_104207_ = true;
            break;
         case FEET:
            model.f_102813_.f_104207_ = true;
            model.f_102814_.f_104207_ = true;
      }

   }

   public static <M extends EntityModel<?>> List<ModelPart> getModelParts(M model) {
      List<ModelPart> result = new ArrayList();
      if (model instanceof HierarchicalModel model2) {
         Stream var10000 = model2.m_142109_().m_171331_();
         Objects.requireNonNull(result);
         var10000.forEach(result::add);
      } else if (model instanceof AgeableListModel model2) {
         Iterable var4 = ((IAgeableListModelExtension)model2).getParts();
         Objects.requireNonNull(result);
         var4.forEach(result::add);
      }

      return result;
   }

   public static <M extends EntityModel<? extends Entity>> void resetModelToDefaultPivots(M model) {
      getModelParts(model).forEach(ModelPart::m_233569_);
   }

   public static void drawPlayerFace(Player player, GuiGraphics graphics, int x, int y, int width, int height) {
      Minecraft mc = Minecraft.m_91087_();
      ClientPacketListener netHandler = mc.f_91074_.f_108617_;
      netHandler.m_104949_(player.m_20148_());
   }

   public static void drawLivingBust(LivingEntity entity, GuiGraphics graphics, int posX, int posY, int scale, int mouseX, int mouseY, boolean isHidden) {
      Minecraft mc = Minecraft.m_91087_();
      double uiscale = mc.m_91268_().m_85449_();
      double left = (double)(posX - scale);
      double width = (double)posX;
      double top = (double)posY - (double)scale * 1.1;
      double height = (double)posY;
      float f = (float)Math.atan((double)((float)mouseX / 40.0F));
      float f1 = (float)Math.atan((double)((float)mouseY / 40.0F));
      Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
      Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
      quaternionf.mul(quaternionf1);
      float f2 = entity.f_20883_;
      float f3 = entity.m_146908_();
      float f4 = entity.m_146909_();
      float f5 = entity.f_20886_;
      float f6 = entity.f_20885_;
      entity.f_20883_ = 180.0F + f * 20.0F;
      entity.m_146922_(180.0F + f * 40.0F);
      entity.m_146926_(-f1 * 20.0F);
      entity.f_20885_ = entity.m_146908_();
      entity.f_20886_ = entity.m_146908_();
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_85837_((double)posX, (double)posY, (double)50.0F);
      graphics.m_280168_().m_252931_((new Matrix4f()).scaling((float)scale, (float)scale, (float)(-scale)));
      graphics.m_280168_().m_252781_(quaternionf);
      Lighting.m_166384_();
      EntityRenderDispatcher dispatcher = Minecraft.m_91087_().m_91290_();
      if (quaternionf1 != null) {
         quaternionf1.conjugate();
         dispatcher.m_252923_(quaternionf1);
      }

      dispatcher.m_114468_(false);
      GL11.glEnable(3089);
      GL11.glScissor((int)(left * uiscale), (int)((double)mc.m_91268_().m_85442_() - top * uiscale), (int)(width * uiscale), (int)(height * uiscale));
      float entityHeight = entity.m_6972_(Pose.STANDING).f_20378_;
      if (entityHeight > 1.6F) {
         entityHeight = 0.05F;
      } else {
         --entityHeight;
         entityHeight *= -1.0F;
      }

      dispatcher.m_114384_(entity, (double)0.0F, (double)entityHeight, (double)0.0F, 0.0F, 1.0F, graphics.m_280168_(), graphics.m_280091_(), isHidden ? 0 : 15728880);
      graphics.m_280091_().m_6299_(RenderType.m_110504_());
      GL11.glDisable(3089);
      graphics.m_280262_();
      dispatcher.m_114468_(true);
      graphics.m_280168_().m_85849_();
      Lighting.m_84931_();
      entity.f_20883_ = f2;
      entity.m_146922_(f3);
      entity.m_146926_(f4);
      entity.f_20886_ = f5;
      entity.f_20885_ = f6;
   }

   public static void renderAbilityProtectionArea(PoseStack matrixStack, Camera info, BufferBuilder buffer, double posX, double posY, double posZ, float sizeX, float sizeY, float sizeZ) {
      renderAreaBorder(matrixStack, info, buffer, posX, posY, posZ, sizeX, sizeY, sizeZ, 0.0F, 1.0F, 1.0F, 1.0F, 10000.0F, 10000.0F, FORCEFIELD_LOCATION);
   }

   public static void renderAreaBorder(PoseStack matrixStack, Camera info, BufferBuilder buffer, double posX, double posY, double posZ, float sizeX, float sizeY, float sizeZ, float red, float green, float blue, float alpha, float animUSpeed, float animVSpeed, ResourceLocation texture) {
      buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85818_);
      float animU = (float)Util.m_137550_() % animUSpeed / animUSpeed;
      float animV = (float)Util.m_137550_() % animVSpeed / animVSpeed;
      float u = animU - 0.0F;
      float v = animV - 1.0F;
      float x = (float)info.m_90583_().f_82479_;
      float y = (float)info.m_90583_().f_82480_;
      float z = (float)info.m_90583_().f_82481_;
      boolean hasYAxis = sizeY != 0.0F && sizeY < 256.0F;
      float minY = 0.0F - y;
      float maxY = 256.0F - y;
      if (hasYAxis) {
         minY = (float)((double)(-sizeY - y) + posY);
         maxY = (float)((double)(sizeY - y) + posY);
      }

      float minX = (float)((double)(-sizeX - x) + posX);
      float maxX = (float)((double)(sizeX + 1.0F - x) + posX);
      float minZ = (float)((double)(-sizeZ - z) + posZ);
      float maxZ = (float)((double)(sizeZ + 1.0F - z) + posZ);
      RenderSystem.setShader(GameRenderer::m_172814_);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.setShaderTexture(0, texture);
      RenderSystem.depthMask(Minecraft.m_91085_());
      matrixStack.m_85836_();
      RenderSystem.polygonOffset(-3.0F, -3.0F);
      RenderSystem.enablePolygonOffset();
      RenderSystem.disableCull();
      buffer.m_5483_((double)minX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
      buffer.m_5483_((double)minX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
      buffer.m_5483_((double)maxX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
      buffer.m_5483_((double)maxX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
      buffer.m_5483_((double)minX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
      buffer.m_5483_((double)minX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
      buffer.m_5483_((double)maxX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
      buffer.m_5483_((double)maxX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
      buffer.m_5483_((double)minX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
      buffer.m_5483_((double)minX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
      buffer.m_5483_((double)minX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
      buffer.m_5483_((double)minX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
      buffer.m_5483_((double)maxX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
      buffer.m_5483_((double)maxX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
      buffer.m_5483_((double)maxX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
      buffer.m_5483_((double)maxX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
      if (hasYAxis) {
         buffer.m_5483_((double)maxX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
         buffer.m_5483_((double)minX, (double)minY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
         buffer.m_5483_((double)minX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
         buffer.m_5483_((double)maxX, (double)minY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
         buffer.m_5483_((double)maxX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(u, v).m_5752_();
         buffer.m_5483_((double)minX, (double)maxY, (double)minZ).m_85950_(red, green, blue, alpha).m_7421_(v, v).m_5752_();
         buffer.m_5483_((double)minX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(v, u).m_5752_();
         buffer.m_5483_((double)maxX, (double)maxY, (double)maxZ).m_85950_(red, green, blue, alpha).m_7421_(u, u).m_5752_();
      }

      BufferUploader.m_231202_(buffer.m_231175_());
      RenderSystem.enableCull();
      RenderSystem.polygonOffset(0.0F, 0.0F);
      RenderSystem.disablePolygonOffset();
      RenderSystem.disableBlend();
      matrixStack.m_85849_();
      RenderSystem.depthMask(true);
   }

   public static void drawStringWithBorder(Font font, GuiGraphics graphics, String text, int posX, int posY, int color) {
      drawStringWithBorder(font, graphics, (Component)Component.m_237113_(text), posX, posY, color);
   }

   public static void drawStringWithBorder(Font font, GuiGraphics graphics, Component text, int posX, int posY, int color) {
      drawStringWithBorder(font, graphics, text.m_7532_(), posX, posY, color);
   }

   public static void drawStringWithBorder(Font font, GuiGraphics graphics, FormattedCharSequence text, int posX, int posY, int color) {
      drawStringWithBorder(font, graphics, text, posX, posY, color, 0);
      graphics.m_280262_();
   }

   public static void drawStringWithBorder(Font font, GuiGraphics graphics, FormattedCharSequence text, int posX, int posY, int color, int outlineColor) {
      drawStringWithBorder(font, graphics.m_280168_(), graphics.m_280091_(), text, posX, posY, color, outlineColor);
      graphics.m_280262_();
   }

   public static void drawStringWithBorder(Font font, PoseStack matrixStack, MultiBufferSource buffer, FormattedCharSequence text, int posX, int posY, int color, int outlineColor) {
      if (text != null) {
         font.m_168645_(text, (float)posX, (float)posY, color, outlineColor, matrixStack.m_85850_().m_252922_(), buffer, 255);
      }
   }

   /** @deprecated */
   @Deprecated
   public static void drawTexturedModalRect(PoseStack matrixStack, ResourceLocation texture, float x, float y, float zLevel) {
      drawTexturedModalRect(matrixStack, texture, x, y, zLevel, 0.0F, 0.0F, 256.0F, 256.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   /** @deprecated */
   @Deprecated
   public static void drawTexturedModalRect(PoseStack matrixStack, ResourceLocation texture, float x, float y, float zLevel, float width, float height) {
      drawTexturedModalRect(matrixStack, texture, x, y, zLevel, 0.0F, 0.0F, width, height, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   /** @deprecated */
   @Deprecated
   public static void drawTexturedModalRect(PoseStack matrixStack, ResourceLocation texture, float x, float y, float zLevel, float u, float v, float width, float height) {
      drawTexturedModalRect(matrixStack, texture, x, y, zLevel, u, v, width, height, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   /** @deprecated */
   @Deprecated
   public static void drawTexturedModalRect(PoseStack matrixStack, ResourceLocation texture, float x, float y, float zLevel, float u, float v, float width, float height, float red, float green, float blue, float alpha) {
      drawTexturedModalRect(matrixStack, texture, x, y, zLevel, u, v, width, height, 1.0F, 1.0F, red, green, blue, alpha);
   }

   /** @deprecated */
   @Deprecated
   public static void drawTexturedModalRect(PoseStack matrixStack, ResourceLocation texture, float x, float y, float zLevel, float u, float v, float width, float height, float uScale, float vScale, float red, float green, float blue, float alpha) {
      float uAtalsScale = 0.00390625F;
      float vAtlasScale = 0.00390625F;
      float width2 = width * uScale;
      float height2 = height * vScale;
      Tesselator tessellator = Tesselator.m_85913_();
      RenderSystem.setShaderTexture(0, texture);
      RenderSystem.setShader(GameRenderer::m_172814_);
      BufferBuilder wr = tessellator.m_85915_();
      wr.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85818_);
      Matrix4f matrix = matrixStack.m_85850_().m_252922_();
      wr.m_252986_(matrix, x, y + height2, zLevel).m_85950_(red, green, blue, alpha).m_7421_(u * 0.00390625F, (v + height) * 0.00390625F).m_5752_();
      wr.m_252986_(matrix, x + width2, y + height2, zLevel).m_85950_(red, green, blue, alpha).m_7421_((u + width) * 0.00390625F, (v + height) * 0.00390625F).m_5752_();
      wr.m_252986_(matrix, x + width2, y, zLevel).m_85950_(red, green, blue, alpha).m_7421_((u + width) * 0.00390625F, v * 0.00390625F).m_5752_();
      wr.m_252986_(matrix, x, y, zLevel).m_85950_(red, green, blue, alpha).m_7421_(u * 0.00390625F, v * 0.00390625F).m_5752_();
      tessellator.m_85914_();
   }

   public static void drawColourOnScreen(int colour, int alpha, double posX, double posY, double width, double height, double zLevel) {
      int r = colour >> 16 & 255;
      int g = colour >> 8 & 255;
      int b = colour & 255;
      drawColourOnScreen(r, g, b, alpha, posX, posY, width, height, zLevel);
   }

   public static void drawColourOnScreen(int red, int green, int blue, int alpha, double posX, double posY, double width, double height, double zLevel) {
      if (!(width <= (double)0.0F) && !(height <= (double)0.0F)) {
         Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, (float)width, (float)height, 0.0F, 1000.0F, 3000.0F);
         RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.f_276633_);
         PoseStack posestack = RenderSystem.getModelViewStack();
         posestack.m_85836_();
         posestack.m_166856_();
         posestack.m_252880_(0.0F, 0.0F, -2000.0F);
         RenderSystem.applyModelViewMatrix();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShader(GameRenderer::m_172811_);
         BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
         bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
         bufferbuilder.m_5483_(posX, posY + height, zLevel).m_6122_(red, green, blue, alpha).m_5752_();
         bufferbuilder.m_5483_(posX + width, posY + height, zLevel).m_6122_(red, green, blue, alpha).m_5752_();
         bufferbuilder.m_5483_(posX + width, posY, zLevel).m_6122_(red, green, blue, alpha).m_5752_();
         bufferbuilder.m_5483_(posX, posY, zLevel).m_6122_(red, green, blue, alpha).m_5752_();
         BufferUploader.m_231202_(bufferbuilder.m_231175_());
         RenderSystem.disableBlend();
         posestack.m_85849_();
         RenderSystem.applyModelViewMatrix();
      }
   }

   public static void drawIcon(ResourceLocation icon, PoseStack pose, float x, float y, float z, float u, float v) {
      drawIcon(icon, pose, x, y, z, u, v, 1.0F, 1.0F, 1.0F, 1.0F, false);
   }

   public static void drawIcon(ResourceLocation icon, PoseStack pose, float x, float y, float z, float u, float v, int rgba) {
      float r = (float)(rgba >> 16 & 255) / 255.0F;
      float g = (float)(rgba >> 8 & 255) / 255.0F;
      float b = (float)(rgba & 255) / 255.0F;
      drawIcon(icon, pose, x, y, z, u, v, r, g, b, 1.0F, false);
   }

   public static void drawIcon(ResourceLocation icon, PoseStack stack, float x, float y, float z, float u, float v, float red, float green, float blue, float alpha) {
      drawIcon(icon, stack, x, y, z, u, v, red, green, blue, alpha, false);
   }

   public static void drawIcon(ResourceLocation icon, PoseStack pose, float x, float y, float z, float u, float v, float red, float green, float blue, float alpha, boolean flip) {
      if (icon == null) {
         LogManager.getLogger(RendererHelper.class).warn("Trying to render a null icon!");
      } else {
         Matrix4f matrix = pose.m_85850_().m_252922_();
         float uvDir = flip ? -1.0F : 1.0F;
         RenderSystem.enableBlend();
         RenderSystem.setShaderTexture(0, icon);
         RenderSystem.setShader(GameRenderer::m_172814_);
         BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
         bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85818_);
         bufferbuilder.m_252986_(matrix, x, y + v, z).m_85950_(red, green, blue, alpha).m_7421_(0.0F, uvDir).m_5752_();
         bufferbuilder.m_252986_(matrix, x + u, y + v, z).m_85950_(red, green, blue, alpha).m_7421_(uvDir, uvDir).m_5752_();
         bufferbuilder.m_252986_(matrix, x + u, y, z).m_85950_(red, green, blue, alpha).m_7421_(uvDir, 0.0F).m_5752_();
         bufferbuilder.m_252986_(matrix, x, y, z).m_85950_(red, green, blue, alpha).m_7421_(0.0F, 0.0F).m_5752_();
         BufferUploader.m_231202_(bufferbuilder.m_231175_());
         RenderSystem.disableBlend();
      }
   }

   public static void drawAbilityTooltip(IAbility ability, GuiGraphics graphics, List<Component> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, int backgroundColorStart, int backgroundColorEnd, int borderColorStart, int borderColorEnd, Font font, ClientTooltipPositioner positioner) {
      ItemStack stack = ItemStack.f_41583_;
      if (!textLines.isEmpty()) {
         List<ClientTooltipComponent> formattedList = new ArrayList();

         for(Component comp : textLines) {
            Stream var10000 = font.m_92923_(comp, maxTextWidth).stream().map(ClientTooltipComponent::m_169948_);
            Objects.requireNonNull(formattedList);
            var10000.forEach(formattedList::add);
         }

         int maxBgWidth = 0;
         int bgHeight = textLines.size() == 1 ? -2 : 0;

         for(ClientTooltipComponent tooltipComp : formattedList) {
            int k = tooltipComp.m_142069_(font);
            if (k > maxBgWidth) {
               maxBgWidth = k;
            }

            bgHeight += tooltipComp.m_142103_();
         }

         Vector2ic vector2ic = positioner.m_262814_(graphics.m_280182_(), graphics.m_280206_(), mouseX, mouseY, maxBgWidth, bgHeight);
         int x = vector2ic.x();
         int y = vector2ic.y();
         graphics.m_280168_().m_85836_();
         int zLayer = 400;
         graphics.m_286007_(() -> TooltipRenderUtil.renderTooltipBackground(graphics, x, y, maxBgWidth, bgHeight, zLayer, backgroundColorStart, backgroundColorEnd, borderColorStart, borderColorEnd));
         graphics.m_280168_().m_252880_(0.0F, 0.0F, (float)zLayer);
         int lineY = y;
         MultiBufferSource.BufferSource bufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
         Matrix4f matrix4f = graphics.m_280168_().m_85850_().m_252922_();

         for(int line = 0; line < formattedList.size(); ++line) {
            ClientTooltipComponent tooltipComp = (ClientTooltipComponent)formattedList.get(line);
            tooltipComp.m_142440_(font, x, lineY, matrix4f, bufferSource);
            lineY += tooltipComp.m_142103_() + (line == 0 ? 2 : 0);
         }

         String abilityName = ability.getCore().getLocalizedName().getString();
         int nameWidth = font.m_92895_(abilityName);
         Set<ResourceLocation> coloredIcons = new HashSet();
         Set<ResourceLocation> staticIcons = new HashSet();
         if (ability.getCore().getSourceHakiNature() != null && ability.getCore().getSourceHakiNature().getTexture() != null) {
            coloredIcons.add(ability.getCore().getSourceHakiNature().getTexture());
         }

         if (ability.getCore().getSourceTypes() != null) {
            for(SourceType type : ability.getCore().getSourceTypes()) {
               if (type.getTexture() != null) {
                  coloredIcons.add(type.getTexture());
               }
            }
         }

         if (ability.getCore().getSourceElement() != null && ability.getCore().getSourceElement().getTexture() != null) {
            staticIcons.add(ability.getCore().getSourceElement().getTexture());
         }

         int spacing = 4;

         for(ResourceLocation icon : coloredIcons) {
            spacing += 12;
            drawIcon(icon, graphics.m_280168_(), (float)(x + nameWidth + (spacing - 12)), (float)y, 500.0F, 10.0F, 10.0F, (float)TOOLTIP_ICON_COLOR.getRed() / 255.0F, (float)TOOLTIP_ICON_COLOR.getGreen() / 255.0F, (float)TOOLTIP_ICON_COLOR.getBlue() / 255.0F, 1.0F);
         }

         for(ResourceLocation icon : staticIcons) {
            spacing += 12;
            drawIcon(icon, graphics.m_280168_(), (float)(x + nameWidth + (spacing - 12)), (float)y, 500.0F, 10.0F, 10.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         graphics.m_280168_().m_85849_();
      }

   }

   public static void drawQuad(PoseStack matrixStack, VertexConsumer buffer, float red, float green, float blue, float alpha, float x, float y, float width, float height, float u1, float u2, float v1, float v2, int lightmap) {
      PoseStack.Pose entry = matrixStack.m_85850_();
      Matrix4f matrix4f = entry.m_252922_();
      Matrix3f matrix3f = entry.m_252943_();
      drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x, y + height, u1, v2, lightmap);
      drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x + width, y + height, u2, v2, lightmap);
      drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x + width, y, u2, v1, lightmap);
      drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x, y, u1, v1, lightmap);
   }

   public static void drawVertex(Matrix4f matrixPos, Matrix3f matrixNormal, VertexConsumer buffer, float red, float green, float blue, float alpha, float x, float y, float texU, float texV, int lightmap) {
      buffer.m_252986_(matrixPos, x, y, 0.0F).m_85950_(red, green, blue, alpha).m_7421_(texU, texV).m_86008_(OverlayTexture.f_118083_).m_85969_(lightmap).m_252939_(matrixNormal, 0.0F, 1.0F, 0.0F).m_5752_();
   }
}
