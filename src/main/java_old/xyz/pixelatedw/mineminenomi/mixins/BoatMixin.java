package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.IKairosekiCoating;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;

@Mixin({Boat.class})
public class BoatMixin {
   @ModifyVariable(
      method = {"setDamage"},
      at = @At("HEAD"),
      ordinal = 0
   )
   public float mineminenomi$setDamage(float damage) {
      Boat boat = (Boat)this;
      IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(boat).orElse((Object)null);
      if (coatingData != null) {
         float modifier = 1.0F - (float)coatingData.getCoatingLevel() / 5.0F;
         modifier = Mth.m_14036_(modifier, 0.5F, 1.0F);
         damage *= modifier;
      }

      return damage;
   }
}
