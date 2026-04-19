package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class LovestruckEffect extends BaseEffect implements ITextureOverlayEffect, IBindBodyEffect, IBindHandsEffect, IWeakenedByHaoshokuEffect {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/stone.png");
   private static final Color OVERLAY_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.8F);

   public LovestruckEffect() {
      super(MobEffectCategory.HARMFUL, 0);
      this.m_19472_(Attributes.f_22279_, "eb1809e8-a81d-4076-94aa-6f06e3a64920", (double)-1.0F, Operation.MULTIPLY_TOTAL).m_19472_(Attributes.f_22278_, "50b418ff-a637-47be-a13e-1a848dea1638", (double)1.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "51958b7e-3eb7-439e-a49a-de7387037c2d", (double)-1.0F, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public boolean isBlockingSwings() {
      return true;
   }

   public ResourceLocation getBodyTexture(int duration, int amplifier) {
      return TEXTURE;
   }

   public Color getTextureOverlayColor() {
      return OVERLAY_COLOR;
   }

   public double m_7048_(int amp, AttributeModifier mod) {
      return mod.m_22218_();
   }

   public boolean isWeakenedByHaoshoku() {
      return true;
   }
}
