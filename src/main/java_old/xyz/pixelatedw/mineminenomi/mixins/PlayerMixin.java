package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin(
   value = {Player.class},
   priority = 990
)
public class PlayerMixin {
   @Inject(
      method = {"getDimensions"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$updateDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> callback) {
      Player living = (Player)this;
      EntityDimensions sizes = MorphsHelper.getMorphDimensions(living, pose);
      if (sizes != null) {
         callback.setReturnValue(sizes);
      }

   }

   @Redirect(
      method = {"tick"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/item/ItemCooldowns;tick()V"
)
   )
   public void mineminenomi$noroTick(ItemCooldowns cooldownTracker) {
      Player player = (Player)this;
      if (player.m_21023_((MobEffect)ModEffects.NORO_SLOWNESS.get())) {
         int amplifier = Math.max(5, 5 * player.m_21124_((MobEffect)ModEffects.NORO_SLOWNESS.get()).m_19564_());
         if (player.f_19797_ % amplifier == 0) {
            cooldownTracker.m_41518_();
         }
      } else {
         cooldownTracker.m_41518_();
      }

   }
}
