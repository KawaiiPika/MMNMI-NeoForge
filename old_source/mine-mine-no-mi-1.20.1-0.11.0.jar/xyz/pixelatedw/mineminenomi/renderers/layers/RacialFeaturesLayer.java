package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.morph.RacialRenderFeature;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class RacialFeaturesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private RenderLayerParent<T, M> parentRenderer;

   public RacialFeaturesLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.parentRenderer = renderer;
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      IEntityStats entityProps = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (entityProps != null) {
         Class<?>[] allowedModels = new Class[]{PlayerModel.class, HumanoidModel.class};
         boolean hasModel = false;

         for(Class<?> clz : allowedModels) {
            if (this.m_117386_().getClass() == clz) {
               hasModel = true;
               break;
            }
         }

         if (hasModel) {
            Race race = (Race)entityProps.getRace().orElse((Object)null);
            if (!(entity instanceof Player) || race != ModRaces.FISHMAN.get()) {
               boolean isInvisible = entity.m_21023_(MobEffects.f_19609_);
               if (race != null && race.hasRenderFeatures() && !isInvisible) {
                  RacialRenderFeature feature = (RacialRenderFeature)ModMorphs.Client.RACIAL_RENDERERS.get(race);
                  if (race.hasSubRaces() && entityProps.getSubRace().isPresent()) {
                     Race subRace = (Race)entityProps.getSubRace().get();
                     feature = (RacialRenderFeature)ModMorphs.Client.RACIAL_RENDERERS.get(subRace);
                  }

                  if (feature == null) {
                     if (entity.f_19797_ % 20 == 0) {
                        WyDebug.debug("Trying to render non existent feature for a race marked as having features for entity: " + String.valueOf(entity));
                     }

                     return;
                  }

                  HumanoidModel<T> model = feature.<T>getModel();
                  ResourceLocation res = feature.getTexture();
                  RacialRenderFeature.ILayerRenderExtension<T> extension = feature.<T>getExtension();
                  boolean replace = extension != null && extension.render(matrixStack, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, this.parentRenderer);
                  if (!replace && model != null && res != null) {
                     float r = 1.0F;
                     float g = 1.0F;
                     float b = 1.0F;
                     if (entityProps.getSkinTint().isPresent()) {
                        int c = (Integer)entityProps.getSkinTint().get();
                        r = (float)(c >> 16 & 255) / 255.0F;
                        g = (float)(c >> 8 & 255) / 255.0F;
                        b = (float)(c & 255) / 255.0F;
                     }

                     this.m_117386_().m_102624_(model);
                     EntityModel var25 = this.m_117386_();
                     if (var25 instanceof HumanoidModel) {
                        HumanoidModel humanoidParent = (HumanoidModel)var25;
                        humanoidParent.m_102872_(model);
                     }

                     model.m_6839_(entity, limbSwing, limbSwingAmount, partialTicks);
                     model.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                     model.m_7695_(matrixStack, buffer.m_6299_(RenderType.m_110470_(res)), packedLight, LivingEntityRenderer.m_115338_(entity, 0.0F), r, g, b, 1.0F);
                  }
               }

            }
         }
      }
   }
}
