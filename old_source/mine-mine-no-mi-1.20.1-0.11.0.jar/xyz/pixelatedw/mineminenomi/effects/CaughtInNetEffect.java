package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class CaughtInNetEffect extends BaseEffect implements ITextureOverlayEffect, IBindHandsEffect, IBindBodyEffect {
   public static final ResourceLocation NET_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/net.png");
   public static final ResourceLocation KAIROSEKI_NET_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/armor/kairoseki_net.png");
   private NetType type;

   public CaughtInNetEffect(NetType type) {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
      this.type = type;
      this.m_19472_(Attributes.f_22279_, "0e091520-be78-40aa-9e74-22aa34f506cf", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "71ada06a-e999-4408-9d43-6f205379b52a", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "7cadbf47-441b-4cd8-b93f-4e0c1147c7c8", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "21006ee0-bf00-4ef7-90b0-d6ba8c003a4f", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.MINING_SPEED.get(), "7adfb66b-5442-4b9e-8a42-5b1e25c39226", (double)-1.0F, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      MobEffectInstance instance = entity.m_21124_(this);
      if (instance.m_19557_() <= 1) {
         entity.m_21195_(this);
         AbilityHelper.enableAbilities(entity, (ability) -> ability != null && ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
      }

      if (this.type == NetType.NORMAL) {
         if (entity.m_6060_()) {
            entity.m_21195_(this);
         }

         if (CombatHelper.isLogiaBlocking(entity)) {
            entity.m_21195_(this);
         }
      } else if (this.type == NetType.KAIROSEKI) {
         AbilityHelper.disableAbilities(entity, instance.m_19557_(), (ability) -> ability != null && ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
      }

   }

   public boolean isBlockingSwings() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public NetType getType() {
      return this.type;
   }

   public ResourceLocation getBodyTexture(int duration, int amplifier) {
      switch (this.type) {
         case KAIROSEKI:
            return KAIROSEKI_NET_TEXTURE;
         case NORMAL:
         default:
            return NET_TEXTURE;
      }
   }
}
