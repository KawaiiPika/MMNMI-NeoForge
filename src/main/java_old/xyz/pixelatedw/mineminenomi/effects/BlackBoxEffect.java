package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class BlackBoxEffect extends BaseEffect implements IBlockOverlayEffect, IBindBodyEffect {
   public BlackBoxEffect() {
      super(MobEffectCategory.HARMFUL, Color.WHITE.getRGB());
      this.m_19472_(Attributes.f_22279_, "b1ddc653-67cc-4eb4-b6ee-8be108e70e2e", (double)-1.0F, Operation.MULTIPLY_TOTAL).m_19472_(Attributes.f_22278_, "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)1.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "bacea9ea-a77a-4296-93db-90548eb2d30d", (double)-256.0F, Operation.ADDITION);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public Block getBlockOverlay(int duration, int amplifier) {
      return (Block)ModBlocks.DARKNESS.get();
   }

   public double m_7048_(int amp, AttributeModifier mod) {
      return mod.m_22218_();
   }
}
