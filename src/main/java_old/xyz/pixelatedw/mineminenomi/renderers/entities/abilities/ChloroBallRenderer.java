package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileRenderer;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class ChloroBallRenderer extends NuProjectileRenderer<ChloroBallProjectile, SphereModel<ChloroBallProjectile>> {
   private static final Color NORMAL_COLOR = WyHelper.hexToRGB("#A020F0");
   private static final Color DEMON_COLOR = WyHelper.hexToRGB("#5A0000");
   private static final Vec3 NORMAL_SCALE = new Vec3((double)2.0F, (double)2.0F, (double)2.0F);
   private static final Vec3 DEMON_SCALE = new Vec3((double)4.0F, (double)4.0F, (double)4.0F);

   public ChloroBallRenderer(EntityRendererProvider.Context ctx) {
      super(ctx, new SphereModel(ctx.m_174023_(SphereModel.LAYER_LOCATION)));
      this.setNormalDetails();
   }

   public void render(ChloroBallProjectile entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.isDemonMode()) {
         this.setDemonDetails();
      } else {
         this.setNormalDetails();
      }

      super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   private void setDemonDetails() {
      this.setColor(DEMON_COLOR);
      this.setScale(DEMON_SCALE);
   }

   private void setNormalDetails() {
      this.setColor(NORMAL_COLOR);
      this.setScale(NORMAL_SCALE);
   }

   public static class Factory implements EntityRendererProvider<ChloroBallProjectile> {
      public EntityRenderer<ChloroBallProjectile> m_174009_(EntityRendererProvider.Context ctx) {
         return new ChloroBallRenderer(ctx);
      }
   }
}
