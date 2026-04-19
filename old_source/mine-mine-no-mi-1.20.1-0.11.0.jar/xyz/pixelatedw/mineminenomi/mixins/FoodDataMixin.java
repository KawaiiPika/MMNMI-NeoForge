package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FoodData.class})
public class FoodDataMixin {
   @Shadow
   private float f_38698_;
   @Shadow
   private float f_38697_;

   @ModifyArg(
      method = {"tick"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"
),
      index = 0
   )
   public float changeExhaustion(float value) {
      return Math.max(value / 2.0F, 0.1F);
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   public void changeSaturation(Player player, CallbackInfo info) {
      if (this.f_38698_ > 4.0F && this.f_38697_ > 0.0F) {
         this.f_38697_ = 1.0F + Math.max(this.f_38697_ - 0.75F, 0.0F);
      }

   }
}
