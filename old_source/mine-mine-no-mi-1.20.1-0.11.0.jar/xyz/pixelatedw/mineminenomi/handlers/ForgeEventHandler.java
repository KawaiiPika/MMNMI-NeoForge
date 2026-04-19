package xyz.pixelatedw.mineminenomi.handlers;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.api.events.ability.ModifyDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

@EventBusSubscriber(
   modid = "mineminenomi"
)
public class ForgeEventHandler {
   @SubscribeEvent
   public static void serverStarted(ServerStartedEvent event) {
      NPCWorldData.get().setup(event.getServer().m_129880_(Level.f_46428_));
   }

   @SubscribeEvent
   public static void serverStarting(ServerStartingEvent event) {
      for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS) {
         ModifyDevilFruitEvent fruitEvent = new ModifyDevilFruitEvent(fruit, fruit.getRawRegistryAbilities());
         MinecraftForge.EVENT_BUS.post(fruitEvent);
         fruit.setAbilities(fruitEvent.getAbilities());
      }

   }

   @SubscribeEvent
   public static void addReloadListeners(AddReloadListenerEvent event) {
      event.addListener(ModTags.Items.CONDUCTIVE);
      event.addListener(ModTags.Items.IRON);
      event.addListener(ModTags.Entities.CONDUCTIVE);
   }
}
