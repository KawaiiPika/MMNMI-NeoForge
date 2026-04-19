package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.baku.BakuFactoryAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

@Mixin({AbstractContainerMenu.class})
public class AbstractContainerMenuMixin {
   @Inject(
      method = {"stillValid"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void mineminenomi$isWithinUsableDistance(ContainerLevelAccess worldPos, Player player, Block targetBlock, CallbackInfoReturnable<Boolean> callback) {
      if (targetBlock == Blocks.f_50091_) {
         IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (abilityData != null) {
            BakuFactoryAbility ability = (BakuFactoryAbility)abilityData.getEquippedAbility((AbilityCore)BakuFactoryAbility.INSTANCE.get());
            boolean hasAbility = ability != null && ability.isContinuous();
            if (hasAbility) {
               callback.setReturnValue(true);
            }
         }
      }

   }

   @Inject(
      method = {"doClick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void mineminenomi$doClick(int slotId, int dragType, ClickType clickType, Player player, CallbackInfo ci) {
      if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
         AbstractContainerMenu cont = (AbstractContainerMenu)this;
         if (clickType == ClickType.QUICK_MOVE && (dragType == 0 || dragType == 1)) {
            try {
               Slot slot = (Slot)cont.f_38839_.get(slotId);
               if (slot != null) {
                  ItemStack slotStack = slot.m_7993_();
                  if (!slotStack.m_41619_() && slotStack.m_41720_() instanceof AkumaNoMiItem && slot.m_8010_(player)) {
                     ci.cancel();
                  }
               }
            } catch (Exception e) {
               e.printStackTrace();
               ci.cancel();
            }
         }
      }

   }
}
