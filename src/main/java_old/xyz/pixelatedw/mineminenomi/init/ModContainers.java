package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;

public class ModContainers {
   public static final RegistryObject<MenuType<WhiteWalkieStorageContainer>> WHITE_WALKIE_STORAGE = ModRegistry.registerContainer("White Walkie Storage", (menuId, inv, data) -> new WhiteWalkieStorageContainer(menuId, inv, data));

   public static void init() {
   }
}
