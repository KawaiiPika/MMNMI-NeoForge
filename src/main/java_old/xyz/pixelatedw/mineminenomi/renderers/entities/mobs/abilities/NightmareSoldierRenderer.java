package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.abilities;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CloneHumanoidRenderer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.NightmareSoldierLayer;

public class NightmareSoldierRenderer extends CloneHumanoidRenderer<NightmareSoldierEntity, HumanoidModel<NightmareSoldierEntity>> {
   public NightmareSoldierRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.setSimpleModel();
      this.m_115326_(new NightmareSoldierLayer(this));
   }

   public static class Factory implements EntityRendererProvider<NightmareSoldierEntity> {
      public EntityRenderer<NightmareSoldierEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new NightmareSoldierRenderer(ctx);
      }
   }
}
