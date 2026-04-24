with open("src/main/java/xyz/pixelatedw/mineminenomi/events/ModEvents.java", "r") as f:
    content = f.read()

content = """package xyz.pixelatedw.mineminenomi.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModItems;

@EventBusSubscriber(modid = ModMain.PROJECT_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerCapabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event) {
        event.registerItem(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM, (stack, ctx) -> {
            return new net.neoforged.neoforge.energy.ComponentEnergyStorage(stack, xyz.pixelatedw.mineminenomi.init.ModDataComponents.ENERGY.get(), 10000, 10000, 10000);
        },
        ModItems.IMPACT_DIAL.get(),
        ModItems.FLAME_DIAL.get(),
        ModItems.BREATH_DIAL.get(),
        ModItems.REJECT_DIAL.get(),
        ModItems.EISEN_DIAL.get()
        );
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        ModMobs.registerAttributes(event);
    }
}"""

with open("src/main/java/xyz/pixelatedw/mineminenomi/events/ModEvents.java", "w") as f:
    f.write(content)
