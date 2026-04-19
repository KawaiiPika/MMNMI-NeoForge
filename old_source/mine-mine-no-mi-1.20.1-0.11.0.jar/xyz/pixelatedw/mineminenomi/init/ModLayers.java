package xyz.pixelatedw.mineminenomi.init;

import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.renderers.layers.AuraLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.AwakeningSmokeLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.BodyCoatingLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.MobEffectLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.PartialMorphFeaturesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.RacialFeaturesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.AbareHimatsuriLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CandleLockLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CycloneTempoLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.DoruBallLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.GomuDawnWhipLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PunkCrossLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.SpiralEffectLayer;

public class ModLayers {
   public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
      Minecraft mc = Minecraft.m_91087_();
      EntityRendererProvider.Context ctx = event.getContext();

      for(Map.Entry<EntityType<?>, EntityRenderer<?>> entry : mc.m_91290_().f_114362_.entrySet()) {
         EntityRenderer<?> entityRenderer = (EntityRenderer)entry.getValue();
         if (entityRenderer instanceof LivingEntityRenderer renderer) {
            addExtraFeatureLayers(ctx, renderer);
            addGenericLayers(ctx, renderer);
            addAbilityLayers(ctx, renderer);
         }

         if (entityRenderer instanceof HumanoidMobRenderer renderer) {
            ;
         }
      }

      for(String skin : event.getSkins()) {
         boolean isSlim = skin.equals("slim");
         LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(skin);
         addExtraFeatureLayers(ctx, renderer);
         addGenericLayers(ctx, renderer);
         addAbilityLayers(ctx, renderer);
      }

   }

   public static <T extends LivingEntity, M extends EntityModel<T>> void addGenericLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new BodyCoatingLayer(renderer));
      renderer.m_115326_(new MobEffectLayer(renderer));
      renderer.m_115326_(new AuraLayer(renderer));
      renderer.m_115326_(new AwakeningSmokeLayer(ctx, renderer));
      renderer.m_115326_(new SpiralEffectLayer(ctx, renderer));
   }

   public static <T extends LivingEntity, M extends EntityModel<T>> void addExtraFeatureLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new RacialFeaturesLayer(renderer));
      renderer.m_115326_(new PartialMorphFeaturesLayer(renderer));
   }

   public static <T extends LivingEntity, M extends EntityModel<T>> void addAbilityLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new CycloneTempoLayer(ctx, renderer));
      renderer.m_115326_(new PunkCrossLayer(ctx, renderer));
      renderer.m_115326_(new AbareHimatsuriLayer(renderer));
      renderer.m_115326_(new GomuDawnWhipLayer(ctx, renderer));
      renderer.m_115326_(new DoruBallLayer(ctx, renderer));
      renderer.m_115326_(new CandleLockLayer(ctx, renderer));
   }
}
