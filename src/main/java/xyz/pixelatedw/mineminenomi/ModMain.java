package xyz.pixelatedw.mineminenomi;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(ModMain.PROJECT_ID)
public class ModMain {
    public static final String PROJECT_ID = "mineminenomi";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModMain(IEventBus modEventBus) {
        // Register the setup methods
        modEventBus.addListener(this::commonSetup);
        
        xyz.pixelatedw.mineminenomi.init.ModRegistries.init(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModRegistry.init(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModDataComponents.init(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModSounds.init();
        xyz.pixelatedw.mineminenomi.init.ModDataAttachments.init();
        xyz.pixelatedw.mineminenomi.init.ModFactions.init();
        xyz.pixelatedw.mineminenomi.init.ModRaces.init();
        xyz.pixelatedw.mineminenomi.init.ModFightingStyles.init();
        xyz.pixelatedw.mineminenomi.init.ModMaterials.init();
        xyz.pixelatedw.mineminenomi.init.ModCreativeTabs.init();
        xyz.pixelatedw.mineminenomi.init.ModItems.init();
        xyz.pixelatedw.mineminenomi.init.ModFruits.init();
        xyz.pixelatedw.mineminenomi.init.ModWeapons.init();
        xyz.pixelatedw.mineminenomi.init.ModArmors.init();
        xyz.pixelatedw.mineminenomi.init.ModBlocks.init();
        xyz.pixelatedw.mineminenomi.init.ModMobs.init();
        xyz.pixelatedw.mineminenomi.init.ModEntities.init();
        xyz.pixelatedw.mineminenomi.init.ModAbilities.register(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModAttributes.register(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModEffects.register(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModFeatures.register(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModStructures.register(modEventBus);
        xyz.pixelatedw.mineminenomi.init.ModDimensions.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Mine Mine no Mi: Common Setup");
    }

    @EventBusSubscriber(modid = PROJECT_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Mine Mine no Mi: Client Setup");
        }
    }
}
