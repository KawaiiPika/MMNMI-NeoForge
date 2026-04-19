package xyz.pixelatedw.mineminenomi.renderers.morphs;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PlayerMorphRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends MorphRenderer<T, M> {
   public PlayerMorphRenderer(EntityRendererProvider.Context ctx, MorphInfo info, M model, boolean isSlim) {
      super(ctx, info, model);
      HumanoidArmorModel<T> inner = new HumanoidArmorModel(ctx.m_174023_(isSlim ? ModelLayers.f_171167_ : ModelLayers.f_171164_));
      HumanoidArmorModel<T> outer = new HumanoidArmorModel(ctx.m_174023_(isSlim ? ModelLayers.f_171168_ : ModelLayers.f_171165_));
      this.m_115326_(new HumanoidArmorLayer(this, inner, outer, ctx.m_266367_()));
   }

   public static class Factory implements EntityRendererProvider<LivingEntity> {
      private MorphInfo info;
      private HumanoidModel<LivingEntity> model;
      private boolean isSlim;

      public Factory(MorphInfo info, HumanoidModel<LivingEntity> model, boolean isSlim) {
         this.info = info;
         this.model = model;
         this.isSlim = isSlim;
      }

      public EntityRenderer<LivingEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new PlayerMorphRenderer(ctx, this.info, this.model, this.isSlim);
      }
   }
}
