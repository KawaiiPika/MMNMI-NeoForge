package xyz.pixelatedw.mineminenomi;

import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.integrations.clothconfig.ClothConfigIntegration;

public class ClientProxy {
   public static void init(FMLJavaModLoadingContext ctx) {
      ctx.registerConfig(Type.CLIENT, ClientConfig.SPEC);
      if (ModMain.hasClothConfigInstalled()) {
         ClothConfigIntegration.registerModConfig(ctx);
      }

   }
}
