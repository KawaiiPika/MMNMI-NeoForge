package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class CandyStuckEffect extends DamageOverTimeEffect implements IBlockOverlayEffect, IBreakableEffect, IBindBodyEffect, IBindHandsEffect, IWeakenedByHaoshokuEffect {
   public CandyStuckEffect() {
      super((entity) -> entity.m_269291_().m_269063_(), 1.0F, 40);
      this.m_19472_(Attributes.f_22279_, "f695cbb3-1223-4c64-97e2-c7979775fd4d", (double)-100.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "346e1665-7959-4a14-86ed-c8e2d1e5cd9a", (double)-256.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22283_, "23b16162-7090-4f27-87cc-613445852721", (double)-4.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22278_, "644893f5-3ae2-4b7c-bc61-32a7a7711286", (double)100.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.REGEN_RATE.get(), "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)-4.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.TOUGHNESS.get(), "77fb7784-2d75-4e0a-8f39-f5c2dcd4fca8", (double)2.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "fa4d711c-faa4-41cd-83c9-8f97edc5800e", (double)-256.0F, Operation.ADDITION);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public Block getBlockOverlay(int duration, int amplifier) {
      return (Block)ModBlocks.CANDY.get();
   }

   public boolean isBlockingSwings() {
      return true;
   }

   public boolean isWeakenedByHaoshoku() {
      return true;
   }
}
