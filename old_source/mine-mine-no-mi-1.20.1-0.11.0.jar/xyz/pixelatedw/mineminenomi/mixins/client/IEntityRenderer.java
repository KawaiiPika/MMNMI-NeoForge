package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({EntityRenderer.class})
public interface IEntityRenderer {
   @Accessor("shadowRadius")
   void setShadowRadius(float var1);
}
