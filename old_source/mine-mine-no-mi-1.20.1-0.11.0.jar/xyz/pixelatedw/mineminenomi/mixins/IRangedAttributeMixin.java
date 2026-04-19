package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
   value = {RangedAttribute.class},
   priority = 2000
)
public interface IRangedAttributeMixin {
   @Accessor("minValue")
   @Mutable
   void setMinValue(double var1);

   @Accessor("maxValue")
   @Mutable
   void setMaxValue(double var1);
}
