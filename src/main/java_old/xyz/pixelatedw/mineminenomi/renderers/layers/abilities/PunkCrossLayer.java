package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PunkCrossLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public PunkCrossLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.m_21023_((MobEffect)ModEffects.PUNK_CROSS.get())) {
         float blocksWidth = (float)(Math.ceil((double)entity.m_20205_()) + (double)1.0F);
         float blocksHeight = (float)(Math.ceil((double)entity.m_20206_()) + (double)1.0F);
         matrixStack.m_252880_(0.4F - blocksWidth / 2.0F, 1.4F - entity.m_20206_() / 2.0F - blocksHeight / 2.0F, 0.4F - blocksWidth / 2.0F);
         matrixStack.m_252880_(0.0F, -2.0F, 0.0F);
         Random rand = new Random();

         for(int x = 0; (float)x < blocksWidth; ++x) {
            for(int y = 0; (float)y < blocksHeight + 2.0F; ++y) {
               for(int z = 0; (float)z < blocksWidth; ++z) {
                  int seed = 1 + x + y + z;
                  rand.setSeed((long)seed);
                  int w = rand.nextInt(180);
                  matrixStack.m_85836_();
                  matrixStack.m_252880_((float)x, (float)y, (float)z);
                  matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)w));
                  matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)(-w)));
                  Minecraft.m_91087_().m_91291_().m_269128_(new ItemStack(Blocks.f_50075_), ItemDisplayContext.HEAD, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, entity.m_9236_(), 0);
                  matrixStack.m_85849_();
               }
            }
         }

         matrixStack.m_85837_((double)-1.5F, (double)1.0F, (double)0.5F);

         for(int x = 0; (float)x < blocksHeight + 2.0F; ++x) {
            for(int y = 0; (float)y < blocksWidth; ++y) {
               int seed = 1 + x + y;
               rand.setSeed((long)seed);
               int w = rand.nextInt(180);
               matrixStack.m_85836_();
               matrixStack.m_252880_((float)x, (float)y, 0.0F);
               matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)w));
               matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)(-w)));
               Minecraft.m_91087_().m_91291_().m_269128_(new ItemStack(Blocks.f_50075_), ItemDisplayContext.HEAD, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, entity.m_9236_(), 0);
               matrixStack.m_85849_();
            }
         }

      }
   }
}
