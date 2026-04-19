package xyz.pixelatedw.mineminenomi.mixins;

import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

@Mixin({ItemStack.class})
public class ItemStackMixin {
   @Inject(
      method = {"hurtAndBreak"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public <T extends LivingEntity> void mineminenomi$hurtAndBreak(int amount, T entity, Consumer<T> onBroken, CallbackInfo callback) {
      ItemStack stack = (ItemStack)this;
      Item item = stack.m_41720_();
      if (item.getFoodProperties(stack, entity) == null && !ServerConfig.isItemBannedFromImbuing(item)) {
         boolean hakiActiveFlag = HakiHelper.hasImbuingActive(entity);
         if (hakiActiveFlag) {
            callback.cancel();
         }

      }
   }
}
