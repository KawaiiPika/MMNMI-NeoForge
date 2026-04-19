package xyz.pixelatedw.mineminenomi.mixins;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.events.SmithingTableEvent;

@Mixin({SmithingMenu.class})
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
   @Nullable
   @Shadow
   private SmithingRecipe f_40242_;

   public SmithingMenuMixin(MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
      super(pType, pContainerId, pPlayerInventory, pAccess);
   }

   @Inject(
      method = {"onTake"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onTake(Player player, ItemStack inputItem, CallbackInfo cir) {
      MinecraftForge.EVENT_BUS.post(new SmithingTableEvent(this.f_39769_, this.f_39771_, this.f_40242_, this.f_39769_.m_8020_(1), this.f_39769_.m_8020_(2)));
   }
}
