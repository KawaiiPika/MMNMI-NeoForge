package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class FrozenEffect extends DamageOverTimeEffect implements ITextureOverlayEffect, IBreakableEffect, IBindHandsEffect, IBindBodyEffect, IWeakenedByHaoshokuEffect {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/blue_ice.png");
   private static final Color OVERLAY_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.8F);

   public FrozenEffect() {
      super((entity) -> ModDamageSources.getInstance().frozen(), 10.0F, 40);
      super.m_19472_(Attributes.f_22279_, "4c6e221d-2191-4960-a642-d38fcbea9c4f", (double)-1000.0F, Operation.ADDITION).m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "0aca6c19-061a-4b3f-9ba8-262f5e6f3815", (double)-256.0F, Operation.ADDITION).m_19472_(Attributes.f_22283_, "777f14bf-fe3f-4f19-9d2c-eb69c04d5209", (double)-4.0F, Operation.ADDITION).m_19472_(Attributes.f_22278_, "c24353cb-2efd-491f-a3f3-55a853d707ef", (double)100.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.TOUGHNESS.get(), "77fb7784-2d75-4e0a-8f39-f5c2dcd4fca8", (double)2.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.REGEN_RATE.get(), "cbbcac4a-a068-47e9-befa-b4324a0faeaf", (double)-4.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "fc7efdce-785b-4a61-bde2-10b95f99d7a4", (double)-256.0F, Operation.ADDITION);
      super.setDamageScaling((amp) -> this.getBaseDamage());
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public ResourceLocation getBodyTexture(int duration, int amplifier) {
      return TEXTURE;
   }

   public Color getTextureOverlayColor() {
      return OVERLAY_COLOR;
   }

   public boolean isBlockingSwings() {
      return true;
   }

   public boolean isWeakenedByHaoshoku() {
      return true;
   }
}
