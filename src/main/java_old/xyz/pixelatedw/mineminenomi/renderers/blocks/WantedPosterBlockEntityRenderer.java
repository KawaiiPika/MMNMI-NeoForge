package xyz.pixelatedw.mineminenomi.renderers.blocks;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.blocks.WantedPosterBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.blocks.WantedPosterModel;

public class WantedPosterBlockEntityRenderer implements BlockEntityRenderer<WantedPosterBlockEntity> {
   private static final float[] SIZE_SCALE = new float[]{1.0F, 2.0F, 4.0F, 6.0F};
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/wantedposter.png");
   private final WantedPosterModel posterModel;
   private final WantedPosterModel face;
   private final WantedPosterModel faceOverlay;
   private final Font font;

   public WantedPosterBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
      this.posterModel = new WantedPosterModel(ctx.m_173582_(WantedPosterModel.LAYER_LOCATION));
      this.face = new WantedPosterModel(ctx.m_173582_(WantedPosterModel.FACE_LAYER_LOCATION));
      this.faceOverlay = new WantedPosterModel(ctx.m_173582_(WantedPosterModel.FACE_OVERLAY_LAYER_LOCATION));
      this.font = ctx.m_173586_();
   }

   public void render(WantedPosterBlockEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
      BlockState blockstate = tileEntity.m_58900_();
      WantedPosterData wantedPosterData = tileEntity.getWantedPosterData();
      String name = wantedPosterData.getOwnerName();
      UUID uuid = wantedPosterData.getOwnerUuid();
      ResourceLocation skinTexture = null;
      int idx = ((CanvasSize)blockstate.m_61143_(WantedPosterBlock.SIZE)).ordinal();
      float scale = SIZE_SCALE[idx];
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.5F, (double)0.0F, (double)0.5F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(-((Direction)blockstate.m_61143_(WantedPosterBlock.FACING)).m_122435_() + 180.0F));
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      matrixStack.m_85841_(scale, scale, 1.0F);
      matrixStack.m_85836_();
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      matrixStack.m_85837_(0.005, 0.6, (double)0.5F);
      matrixStack.m_85841_(0.6F, -0.6F, -0.01F);
      VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(TEXTURE));
      this.posterModel.m_7695_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
      if (Strings.isNullOrEmpty(name)) {
         matrixStack.m_85849_();
      } else {
         String bounty = wantedPosterData.getBountyString();
         matrixStack.m_85836_();
         if (wantedPosterData.isExpired()) {
            matrixStack.m_85836_();
            matrixStack.m_85837_(-0.345, -0.9, 0.496);
            matrixStack.m_85841_(0.173F, 0.132F, 0.001F);
            VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(ModResources.EXPIRED));
            RendererHelper.drawQuad(matrixStack, ivertexbuilder, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 3.9F, 3.9F, 0.0F, 1.0F, 0.0F, 1.0F, combinedLight);
            matrixStack.m_85849_();
         }

         matrixStack.m_85836_();
         if (wantedPosterData.getOwnerProfile().isPresent()) {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.m_91087_().m_91109_().m_118815_((GameProfile)wantedPosterData.getOwnerProfile().get());
            if (map.containsKey(Type.SKIN)) {
               skinTexture = Minecraft.m_91087_().m_91109_().m_118825_((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
            } else {
               skinTexture = DefaultPlayerSkin.m_118627_(uuid);
            }
         } else if (wantedPosterData.getOwnerTexture().isPresent()) {
            skinTexture = (ResourceLocation)wantedPosterData.getOwnerTexture().get();
         } else {
            skinTexture = DefaultPlayerSkin.m_118627_(uuid);
         }

         matrixStack.m_85837_(-0.21, -0.92, 0.498);
         matrixStack.m_85841_(0.8F, 0.8F, 0.001F);
         VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(skinTexture));
         this.face.m_7695_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
         this.faceOverlay.m_7695_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
         matrixStack.m_85836_();
         if (tileEntity.isPirate()) {
            if (wantedPosterData.getOwnerCrew().isPresent()) {
               matrixStack.m_252880_(0.12F, -0.59F, 0.497F);
               RenderSystem.enableDepthTest();
               ((Crew)wantedPosterData.getOwnerCrew().get()).getJollyRoger().render(matrixStack, 0, 0, 6.5E-4F);
               RenderSystem.disableDepthTest();
            }
         } else if (tileEntity.isRevolutionary()) {
            matrixStack.m_252880_(-0.01F, -0.7F, 0.497F);
            ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(ModResources.REVOLUTIONARY_ARMY_ICON));
            RendererHelper.drawQuad(matrixStack, ivertexbuilder, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.4F, 0.4F, 0.0F, 1.0F, 0.0F, 1.0F, combinedLight);
         }

         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_85837_(-0.32, -0.895, 0.499);
         matrixStack.m_85841_(0.16F, 0.126F, 0.001F);
         ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/wantedposters/backgrounds/" + wantedPosterData.getBackground() + ".jpg");
         VertexConsumer ivertexbuilder = buffer.m_6299_(ModRenderTypes.getWantedPoster(texture));
         RendererHelper.drawQuad(matrixStack, ivertexbuilder, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 3.9F, 3.9F, 0.0F, 1.0F, 0.0F, 1.0F, combinedLight);
         matrixStack.m_85849_();
         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_85841_(0.007F, 0.007F, 0.007F);
         matrixStack.m_85837_((double)-10.0F, (double)-42.5F, (double)71.0F);
         if (name.length() > 13) {
            name = name.substring(0, 10) + "...";
         }

         Font var10000 = this.font;
         String var10001 = String.valueOf(ChatFormatting.BOLD);
         var10000.m_271703_(var10001 + name, 9.0F - (float)this.font.m_92895_(name) / 1.75F, -1.0F, -11455469, false, matrixStack.m_85850_().m_252922_(), buffer, DisplayMode.NORMAL, 0, combinedLight);
         matrixStack.m_85841_(1.2F, 1.2F, 1.2F);
         boolean flag = bounty.length() > 9;
         if (flag) {
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)-5.0F, (double)1.5F, (double)0.0F);
            matrixStack.m_85841_(0.72F, 0.89F, 1.005F);
         }

         this.font.m_271703_(String.valueOf(ChatFormatting.BOLD) + bounty, -18.0F, 9.5F, -11455469, false, matrixStack.m_85850_().m_252922_(), buffer, DisplayMode.NORMAL, 0, combinedLight);
         if (flag) {
            matrixStack.m_85849_();
         }

         matrixStack.m_252880_(0.0F, 1.0F, 0.0F);
         matrixStack.m_85841_(0.7F, 0.9F, 0.8F);
         this.font.m_271703_(String.valueOf(ChatFormatting.BOLD) + wantedPosterData.getDate(), -40.0F, 25.0F, -11455469, false, matrixStack.m_85850_().m_252922_(), buffer, DisplayMode.NORMAL, 0, combinedLight);
         matrixStack.m_85849_();
         matrixStack.m_85849_();
      }
   }
}
