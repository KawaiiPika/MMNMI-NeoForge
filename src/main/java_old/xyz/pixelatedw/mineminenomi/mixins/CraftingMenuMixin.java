package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.handlers.world.CraftingHandler;

@Mixin({CraftingMenu.class})
public class CraftingMenuMixin {
   @Inject(
      method = {"slotChangedCraftingGrid"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void mineminenomi$slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer container, ResultContainer resultContainer, CallbackInfo callback) {
      boolean flag = CraftingHandler.onGridChanged(menu, level, player, container, resultContainer);
      if (flag) {
         callback.cancel();
      }
   }
}
