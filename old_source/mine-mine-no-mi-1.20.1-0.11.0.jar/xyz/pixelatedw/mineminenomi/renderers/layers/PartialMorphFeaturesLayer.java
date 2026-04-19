package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class PartialMorphFeaturesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public PartialMorphFeaturesLayer(LivingEntityRenderer<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      IDevilFruit fruitProps = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (fruitProps != null) {
         boolean isInvisible = entity.m_21023_(MobEffects.f_19609_);
         if (!isInvisible) {
            boolean hasPartial = false;

            for(MorphInfo morphInfo : fruitProps.getActiveMorphs()) {
               if (morphInfo.isPartial() && morphInfo.canRender(entity)) {
                  EntityModel<T> model = (EntityModel)ModMorphs.Client.PARTIAL_MORPHS_RENDERERS.get(morphInfo);
                  ResourceLocation res = morphInfo.getTexture(entity);
                  if (model != null && res != null) {
                     this.m_117386_().m_102624_(model);
                     EntityModel var19 = this.m_117386_();
                     if (var19 instanceof HumanoidModel) {
                        HumanoidModel humanoidParent = (HumanoidModel)var19;
                        humanoidParent.m_102624_(model);
                     }

                     model.m_6839_(entity, limbSwing, limbSwingAmount, partialTicks);
                     model.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                     model.m_7695_(matrixStack, buffer.m_6299_(RenderType.m_110473_(res)), packedLight, LivingEntityRenderer.m_115338_(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
                  }

                  hasPartial = true;
               }
            }

            if (hasPartial) {
               ((IAgeableListModelExtension)this.m_117386_()).getParts().forEach(ModelPart::m_233569_);
            }

         }
      }
   }
}
