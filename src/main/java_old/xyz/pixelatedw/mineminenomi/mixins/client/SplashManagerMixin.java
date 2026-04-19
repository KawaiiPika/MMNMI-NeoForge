package xyz.pixelatedw.mineminenomi.mixins.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;

@Mixin({SplashManager.class})
public class SplashManagerMixin {
   private static final ResourceLocation MMNM_SPLASHES = ResourceLocation.fromNamespaceAndPath("mineminenomi", "texts/splashes.txt");
   private static final RandomSource RANDOM = RandomSource.m_216327_();

   @Inject(
      method = {"getSplash"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void mineminenomi$getSplash(CallbackInfoReturnable<SplashRenderer> cir) {
      if (ClientConfig.isModSplashTextEnabled()) {
         List<String> splashes = new ArrayList();

         try {
            BufferedReader reader = Minecraft.m_91087_().m_91098_().m_215597_(MMNM_SPLASHES);

            try {
               Stream var10000 = reader.lines().map(String::trim).filter((splash) -> splash.hashCode() != 125780783);
               Objects.requireNonNull(splashes);
               var10000.forEach(splashes::add);
            } catch (Throwable var7) {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (reader != null) {
               reader.close();
            }
         } catch (IOException var8) {
         }

         if (RANDOM.m_188503_(3) == 0) {
            cir.setReturnValue(new SplashRenderer((String)splashes.get(RANDOM.m_188503_(splashes.size()))));
         }

      }
   }
}
