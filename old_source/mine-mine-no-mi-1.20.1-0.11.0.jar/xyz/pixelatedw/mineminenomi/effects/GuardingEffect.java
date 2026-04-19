package xyz.pixelatedw.mineminenomi.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

/** @deprecated */
@Deprecated
public class GuardingEffect extends BaseEffect implements IBindHandsEffect, IBindBodyEffect {
   /** @deprecated */
   @Deprecated
   public boolean useOnlySources = false;
   /** @deprecated */
   @Deprecated
   public boolean reduceSpeedAfterHit = false;
   /** @deprecated */
   @Deprecated
   public boolean blockSwings = false;
   /** @deprecated */
   @Deprecated
   public ArrayList<String> acceptableSources = new ArrayList(Collections.emptyList());

   /** @deprecated */
   @Deprecated
   public GuardingEffect(boolean canMove, boolean blockSwings) {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#000000").getRGB());
      if (canMove) {
         this.m_19472_(Attributes.f_22278_, "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)0.5F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "80abd0fe-3fec-42ac-8563-e39f82ab9c59", (double)-1.0F, Operation.ADDITION);
      } else {
         this.m_19472_(Attributes.f_22279_, "94822875-5c1c-4b25-ba22-44ee9d50717c", (double)-1.0F, Operation.MULTIPLY_TOTAL).m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "b2144abf-f6cb-4994-9acd-721f949140cb", (double)-1.0F, Operation.MULTIPLY_TOTAL).m_19472_(Attributes.f_22278_, "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)1.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "80abd0fe-3fec-42ac-8563-e39f82ab9c59", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      }

      this.blockSwings = blockSwings;
   }

   /** @deprecated */
   @Deprecated
   public GuardingEffect(boolean useOnlySources, boolean makeUserSlow, boolean blockSwings, String... s) {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#000000").getRGB());
      this.useOnlySources = useOnlySources;
      this.reduceSpeedAfterHit = makeUserSlow;
      this.blockSwings = blockSwings;
      this.acceptableSources.addAll(Arrays.asList(s));
   }

   /** @deprecated */
   @Deprecated
   public boolean isBlockingRotation() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public boolean isBlockingSwings() {
      return this.blockSwings;
   }
}
