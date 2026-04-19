package xyz.pixelatedw.mineminenomi.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModLayers;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ModEventHandler {
   public static void init() {
   }

   @EventBusSubscriber(
      modid = "mineminenomi",
      bus = Bus.MOD
   )
   public static class Common {
      @SubscribeEvent
      public static void onSpawnPlacement(SpawnPlacementRegisterEvent event) {
         ModMobs.setupSpawnPlacement(event);
      }

      @SubscribeEvent
      public static void onAttributeCreation(EntityAttributeCreationEvent event) {
         ModMobs.createAttributes(event);
      }

      @SubscribeEvent
      public static void onAttributeModification(EntityAttributeModificationEvent event) {
         ModAttributes.changeAttributes(event);
      }
   }

   @EventBusSubscriber(
      modid = "mineminenomi",
      value = {Dist.CLIENT},
      bus = Bus.MOD
   )
   public static class Client {
      @SubscribeEvent
      public static void creativeTabSetup(BuildCreativeModeTabContentsEvent event) {
         if (event.getTabKey() == CreativeModeTabs.f_256731_) {
            for(RegistryObject<ForgeSpawnEggItem> item : ModRegistry.getModSpawnEggs()) {
               event.accept(item);
            }
         }

      }

      @SubscribeEvent
      public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
         ModBlockEntities.Client.registerLayers(event);
         ModEntities.Client.registerLayers(event);
         ModProjectiles.Client.registerLayers(event);
         ModMobs.Client.registerLayers(event);
         ModArmors.Client.registerLayers(event);
         ModMorphs.Client.registerLayers(event);
      }

      @SubscribeEvent
      public static void addLayers(EntityRenderersEvent.AddLayers event) {
         Minecraft mc = Minecraft.m_91087_();
         EntityRendererProvider.Context ctx = new EntityRendererProvider.Context(mc.m_91290_(), mc.m_91291_(), mc.m_91289_(), mc.f_91063_.f_109055_, mc.m_91098_(), mc.m_167973_(), mc.f_91062_);
         ModLayers.addEntityLayers(event);
         ModArmors.Client.addLayers(event, ctx);
         ModMorphs.Client.addLayers(event, ctx);
      }

      @SubscribeEvent
      public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
         Minecraft mc = Minecraft.m_91087_();
         EntityRendererProvider.Context ctx = new EntityRendererProvider.Context(mc.m_91290_(), mc.m_91291_(), mc.m_91289_(), mc.f_91063_.f_109055_, mc.m_91098_(), mc.m_167973_(), mc.f_91062_);
         ModBlockEntities.Client.registerRenderers(event, ctx);
         ModEntities.Client.registerRenderers(event, ctx);
         ModProjectiles.Client.registerRenderers(event, ctx);
         ModMobs.Client.registerRenderers(event, ctx);
      }
   }
}
