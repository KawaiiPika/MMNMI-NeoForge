package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;

public class ModBlockEntities {
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CustomSpawnerBlockEntity>> CUSTOM_SPAWNER = ModRegistry.BLOCK_ENTITIES.register("custom_spawner", () -> BlockEntityType.Builder.of(CustomSpawnerBlockEntity::new, ModBlocks.CUSTOM_SPAWNER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WantedPosterBlockEntity>> WANTED_POSTER = ModRegistry.BLOCK_ENTITIES.register("wanted_poster", () -> BlockEntityType.Builder.of(WantedPosterBlockEntity::new, ModBlocks.WANTED_POSTER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PoneglyphBlockEntity>> PONEGLYPH = ModRegistry.BLOCK_ENTITIES.register("poneglyph", () -> BlockEntityType.Builder.of(PoneglyphBlockEntity::new, ModBlocks.PONEGLYPH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FlagBlockEntity>> FLAG = ModRegistry.BLOCK_ENTITIES.register("flag", () -> BlockEntityType.Builder.of(FlagBlockEntity::new, ModBlocks.FLAG.get()).build(null));

    public static void init() {
    }

    @EventBusSubscriber(modid = "mineminenomi", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        }
    }
}
