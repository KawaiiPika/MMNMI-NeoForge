package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.joml.Matrix4f;
import org.joml.Random;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;

public class PacifistaNumberLayer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends RenderLayer<T, M> {
   private static final DecimalFormat FORMAT = new DecimalFormat("0000");
   private static final Random RANDOM = new Random();
   private final Font font;
   private final String idLabel;
   private final float labelPosX;

   public PacifistaNumberLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.font = ctx.m_174028_();
      int pxid = RANDOM.nextInt(9999);
      this.idLabel = "PX-" + FORMAT.format((long)pxid);
      this.labelPosX = (float)(-this.font.m_92895_(this.idLabel) / 2);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float partialTicks, float pAgeInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_85836_();
      float scale = 0.003F;
      float headYPos = ((PacifistaModel)this.m_117386_()).f_102808_.f_104201_ * 0.07F;
      float headXPos = ((PacifistaModel)this.m_117386_()).f_102808_.f_104200_ * 0.02F;
      float headZPos = ((PacifistaModel)this.m_117386_()).f_102808_.f_104202_ * 0.01F;
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(netHeadYaw - 90.0F));
      matrixStack.m_252781_(Axis.f_252393_.m_252977_(headPitch));
      matrixStack.m_252880_(-0.15F + headXPos, -0.055F + headYPos, -0.251F + headZPos);
      matrixStack.m_85841_(scale, scale, 1.0F);
      Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
      float opacity = Minecraft.m_91087_().f_91066_.m_92141_(0.0F);
      int j = (int)(opacity * 255.0F) << 24;
      if (entity.m_6084_()) {
         this.font.m_271703_(this.idLabel, this.labelPosX, 0.0F, 0, false, matrix4f, buffer, DisplayMode.NORMAL, j, packedLight);
      }

      matrixStack.m_85849_();
   }
}
