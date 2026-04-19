package xyz.pixelatedw.mineminenomi.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.UUID;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.blocks.FlagModel;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {
   public static final ResourceLocation ON_FIRE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/misc/flagflame.png");
   private static final float[] SIZE_SCALE = new float[]{1.0F, 2.0F, 4.0F, 6.0F};
   private static final float[] OFFSET_X = new float[]{-0.5F, -0.75F, -0.87F, -0.916F};
   private static final float[] OFFSET_Y = new float[]{0.0F, -0.5F, -0.75F, -0.833F};
   private FlagModel flagModel;

   public FlagBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
      this.flagModel = new FlagModel(ctx.m_173582_(FlagModel.LAYER_LOCATION));
   }

   public void render(FlagBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
      if (!blockEntity.isSub()) {
         Level level = blockEntity.m_58904_();
         UUID id = blockEntity.getOwnerUUID();
         level.m_46003_(id);
         BlockState blockstate = blockEntity.m_58900_();
         int idx = ((CanvasSize)blockstate.m_61143_(FlagBlock.SIZE)).ordinal();
         float scale = SIZE_SCALE[idx];
         Direction dir = ((Direction)blockstate.m_61143_(FlagBlock.FACING)).m_122424_();
         BlockPos supportPos = blockEntity.m_58899_().m_7918_(dir.m_122429_(), dir.m_122430_(), dir.m_122431_());
         BlockState supportBlock = level.m_8055_(supportPos);
         double supportOffset = (double)1.0F - supportBlock.m_60808_(level, supportPos).m_83297_(dir.m_122434_());
         matrixStack.m_252880_(0.5F, 0.0F, 0.5F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-((Direction)blockstate.m_61143_(FlagBlock.FACING)).m_122435_() + 90.0F));
         matrixStack.m_85837_(supportOffset, (double)0.0F, (double)0.0F);
         matrixStack.m_85841_(scale, scale, 1.0F);
         matrixStack.m_252880_(OFFSET_X[idx], OFFSET_Y[idx], 0.5F);
         long flutterTick = level.m_46467_() + (long)(blockEntity.hashCode() / 100000);
         matrixStack.m_85836_();
         float flutterAnim = (float)(Math.cos((double)((float)flutterTick * 0.1F) + Math.PI) * (double)0.02F);
         matrixStack.m_85836_();
         matrixStack.m_85837_(0.65, 0.9 - (double)flutterAnim, -0.537);
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         matrixStack.m_85841_(0.9F, 0.9F, 1.0F);
         matrixStack.m_252781_(Axis.f_252436_.m_252961_(Mth.m_14089_((float)flutterTick * 0.12F + (float)Math.PI) * 0.023F));
         if (blockEntity.getFaction().isPresent()) {
            RendererHelper.drawPlayerFactionIcon((Faction)blockEntity.getFaction().get(), (Crew)blockEntity.getCrew().orElse((Object)null), (LivingEntity)null, matrixStack, buffer, combinedLight);
         }

         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_85837_((double)0.75F, 0.9 - (double)flutterAnim, -0.485);
         matrixStack.m_85841_(1.0F, -1.0F, 1.0F);
         matrixStack.m_85841_(0.9F, 0.9F, 1.0F);
         matrixStack.m_252781_(Axis.f_252392_.m_252961_(Mth.m_14089_((float)flutterTick * 0.12F + (float)Math.PI) * 0.02F));
         matrixStack.m_85837_((double)-1.0F, (double)0.0F, (double)0.0F);
         if (blockEntity.getFaction().isPresent()) {
            RendererHelper.drawPlayerFactionIcon((Faction)blockEntity.getFaction().get(), (Crew)blockEntity.getCrew().orElse((Object)null), (LivingEntity)null, matrixStack, buffer, combinedLight);
         }

         matrixStack.m_85849_();
         matrixStack.m_85849_();
         RenderType renderType = ModRenderTypes.FLAG;
         Color backgroundColor = (Color)blockEntity.getFaction().map(Faction::getFlagBackgroundColor).orElse(Color.WHITE);
         if (blockEntity.isOnFire()) {
            renderType = ModRenderTypes.FLAG_ON_FIRE;
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)0.25F, (double)0.0F, -0.6);
            this.renderFlame(matrixStack, buffer, 1.0F, 0.55F);
            matrixStack.m_85841_(-1.0F, 1.0F, 1.0F);
            matrixStack.m_85837_((double)0.0F, (double)0.0F, 0.15);
            this.renderFlame(matrixStack, buffer, 1.0F, 0.55F);
            matrixStack.m_85849_();
         }

         matrixStack.m_85836_();
         matrixStack.m_85837_((double)0.5F, (double)1.5F, -0.51);
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         VertexConsumer ivertexbuilder = buffer.m_6299_(renderType);
         this.flagModel.flagAnimation(level, flutterTick);
         this.flagModel.m_7695_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, (float)backgroundColor.getRed() / 255.0F, (float)backgroundColor.getGreen() / 255.0F, (float)backgroundColor.getBlue() / 255.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }

   public void renderFlame(PoseStack matrixStack, MultiBufferSource buffer, float width, float height) {
      TextureAtlasSprite fire0Atlas = ModelBakery.f_119219_.m_119204_();
      TextureAtlasSprite fire1Atlas = ModelBakery.f_119220_.m_119204_();
      matrixStack.m_85836_();
      float f = width * 1.4F;
      matrixStack.m_85841_(f, f, f);
      float f1 = 0.5F;
      float f3 = height / f;
      float f4 = 0.0F;
      float f5 = 0.0F;
      int i = 0;
      VertexConsumer ivertexbuilder = buffer.m_6299_(Sheets.m_110790_());

      for(PoseStack.Pose matrixEntry = matrixStack.m_85850_(); f3 > 0.0F; ++i) {
         TextureAtlasSprite textureAtlas = i % 2 == 0 ? fire0Atlas : fire1Atlas;
         float f6 = textureAtlas.m_118409_();
         float f7 = textureAtlas.m_118411_();
         float f8 = textureAtlas.m_118410_();
         float f9 = textureAtlas.m_118412_();
         if (i / 2 % 2 == 0) {
            float f10 = f8;
            f8 = f6;
            f6 = f10;
         }

         fireVertex(matrixEntry, ivertexbuilder, f1 - 0.0F, 0.0F - f4, f5, f8, f9);
         fireVertex(matrixEntry, ivertexbuilder, -f1 - 0.0F, 0.0F - f4, f5, f6, f9);
         fireVertex(matrixEntry, ivertexbuilder, -f1 - 0.0F, 1.4F - f4, f5, f6, f7);
         fireVertex(matrixEntry, ivertexbuilder, f1 - 0.0F, 1.4F - f4, f5, f8, f7);
         f3 -= 0.45F;
         f4 -= 0.45F;
         f1 *= 0.9F;
         f5 += 0.03F;
      }

      matrixStack.m_85849_();
   }

   private static void fireVertex(PoseStack.Pose matrixEntry, VertexConsumer buffer, float x, float y, float z, float texU, float texV) {
      buffer.m_252986_(matrixEntry.m_252922_(), x, y, z).m_6122_(255, 255, 255, 255).m_7421_(texU, texV).m_7122_(0, 10).m_85969_(240).m_252939_(matrixEntry.m_252943_(), 0.0F, 1.0F, 0.0F).m_5752_();
   }
}
