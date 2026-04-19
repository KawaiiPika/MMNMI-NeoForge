package xyz.pixelatedw.mineminenomi.init;

import com.mojang.datafixers.types.Type;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.models.blocks.FlagModel;
import xyz.pixelatedw.mineminenomi.models.blocks.WantedPosterModel;
import xyz.pixelatedw.mineminenomi.renderers.blocks.FlagBlockEntityRenderer;
import xyz.pixelatedw.mineminenomi.renderers.blocks.WantedPosterBlockEntityRenderer;

public class ModBlockEntities {
   public static final RegistryObject<BlockEntityType<CustomSpawnerBlockEntity>> CUSTOM_SPAWNER = ModRegistry.registerTileEntity("custom_spawner", () -> Builder.m_155273_(CustomSpawnerBlockEntity::new, new Block[]{(Block)ModBlocks.CUSTOM_SPAWNER.get()}).m_58966_((Type)null));
   public static final RegistryObject<BlockEntityType<WantedPosterBlockEntity>> WANTED_POSTER = ModRegistry.registerTileEntity("wanted_poster", () -> Builder.m_155273_(WantedPosterBlockEntity::new, new Block[]{(Block)ModBlocks.WANTED_POSTER.get()}).m_58966_((Type)null));
   public static final RegistryObject<BlockEntityType<PoneglyphBlockEntity>> PONEGLYPH = ModRegistry.registerTileEntity("poneglyph", () -> Builder.m_155273_(PoneglyphBlockEntity::new, new Block[]{(Block)ModBlocks.PONEGLYPH.get()}).m_58966_((Type)null));
   public static final RegistryObject<BlockEntityType<FlagBlockEntity>> FLAG = ModRegistry.registerTileEntity("flag", () -> Builder.m_155273_(FlagBlockEntity::new, new Block[]{(Block)ModBlocks.FLAG.get()}).m_58966_((Type)null));

   public static void init() {
   }

   public static class Client {
      public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
         event.registerLayerDefinition(WantedPosterModel.LAYER_LOCATION, WantedPosterModel::createLayer);
         event.registerLayerDefinition(WantedPosterModel.FACE_LAYER_LOCATION, WantedPosterModel::createFaceLayer);
         event.registerLayerDefinition(WantedPosterModel.FACE_OVERLAY_LAYER_LOCATION, WantedPosterModel::createFaceOverlayLayer);
         event.registerLayerDefinition(FlagModel.LAYER_LOCATION, FlagModel::createBodyLayer);
      }

      public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event, EntityRendererProvider.Context ctx) {
         event.registerBlockEntityRenderer((BlockEntityType)ModBlockEntities.WANTED_POSTER.get(), WantedPosterBlockEntityRenderer::new);
         event.registerBlockEntityRenderer((BlockEntityType)ModBlockEntities.FLAG.get(), FlagBlockEntityRenderer::new);
      }
   }
}
