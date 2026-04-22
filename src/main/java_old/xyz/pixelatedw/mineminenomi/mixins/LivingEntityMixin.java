package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.events.MobEffectAfterAddedEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin implements IForgeEntity, ILivingEntityExtension {
   @Shadow
   public boolean f_20899_;

   public boolean isJumping() {
      return this.f_20899_;
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      LivingEntity entity = (LivingEntity)this;
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityProps == null) {
         return null;
      } else {
         for(IAbility ability : abilityProps.getEquippedAndPassiveAbilities((abl) -> abl.hasComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()))) {
            MorphComponent morph = (MorphComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()).get();
            if (!morph.getParts().isEmpty()) {
               return (PartEntity[])morph.getParts().values().toArray(new PartEntity[0]);
            }
         }

         return null;
      }
   }

   public boolean isMultipartEntity() {
      LivingEntity entity = (LivingEntity)this;
      boolean hasParts = entity.getParts() != null && entity.getParts().length > 0;
      return hasParts;
   }

   @Inject(
      method = {"isImmobile"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$isImmobile(CallbackInfoReturnable<Boolean> cir) {
      LivingEntity entity = (LivingEntity)this;
      Stream var10000 = entity.m_21220_().stream().map(MobEffectInstance::m_19544_);
      Objects.requireNonNull(IBindBodyEffect.class);
      boolean hasBindMovement = var10000.filter(IBindBodyEffect.class::isInstance).anyMatch((eff) -> ((IBindBodyEffect)eff).isBlockingRotation());
      if (hasBindMovement) {
         cir.setReturnValue(true);
      }
   }

   @Inject(
      method = {"aiStep"},
      at = {@At("TAIL")},
      cancellable = true
   )
   public void mineminenomi$aiStep(CallbackInfo ci) {
      LivingEntity entity = (LivingEntity)this;
      if (entity instanceof Mob mob) {
         Stream var10000 = mob.m_21220_().stream().map(MobEffectInstance::m_19544_);
         Objects.requireNonNull(IBindHandsEffect.class);
         boolean hasArmsBindEffect = var10000.filter(IBindHandsEffect.class::isInstance).filter((eff) -> eff != ModEffects.NO_HANDS.get()).anyMatch((eff) -> ((IBindHandsEffect)eff).isBlockingSwings());
         if (hasArmsBindEffect) {
            mob.m_21566_().m_6849_(mob.m_20185_(), mob.m_20186_(), mob.m_20189_(), (double)0.0F);
            mob.f_20902_ = 0.0F;
            mob.m_21573_().m_26573_();
            mob.m_6710_((LivingEntity)null);
         }
      }

   }

   @Inject(
      method = {"dropAllDeathLoot"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$dropAllDeathLoot(DamageSource pDamageSource, CallbackInfo ci) {
      LivingEntity entity = (LivingEntity)this;
      if (NuWorld.isChallengeDimension(entity.m_9236_())) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"getDimensions"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$updateDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> callback) {
      LivingEntity living = (LivingEntity)this;
      EntityDimensions sizes = MorphsHelper.getMorphDimensions(living, pose);
      if (sizes != null) {
         callback.setReturnValue(sizes);
      }

   }

   @Inject(
      method = {"getEyeHeight"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$getEyeHeight(Pose pose, EntityDimensions size, CallbackInfoReturnable<Float> cir) {
      LivingEntity living = (LivingEntity)this;
      float eyeHeight = MorphsHelper.getMorphEyeHeight(living);
      if (eyeHeight > 0.0F) {
         cir.setReturnValue(eyeHeight);
      }

   }

   @Inject(
      method = {"onEffectAdded"},
      at = {@At("HEAD")}
   )
   public void mineminenomi$fireAfterEffectAddedEvent(MobEffectInstance instance, @Nullable Entity entity, CallbackInfo callback) {
      LivingEntity living = (LivingEntity)this;
      MobEffectAfterAddedEvent event = new MobEffectAfterAddedEvent(living, instance);
      MinecraftForge.EVENT_BUS.post(event);
   }

   @Inject(
      method = {"canStandOnFluid"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$canStandOnFluid(FluidState fluid, CallbackInfoReturnable<Boolean> cir) {
      if (fluid.m_76152_().getFluidType().equals(ForgeMod.WATER_TYPE.get())) {
         LivingEntity entity = (LivingEntity)this;
         boolean hasYomi = (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYomiPower()).orElse(false);
         if (hasYomi && entity.m_20142_()) {
            cir.setReturnValue(true);
         }
      }

   }

   @ModifyVariable(
      method = {"travel(Lnet/minecraft/world/phys/Vec3;)V"},
      at = @At("STORE"),
      ordinal = 0
   )
   private float mineminenomi$getFriction(float original) {
      LivingEntity entity = (LivingEntity)this;
      AttributeInstance friction = entity.m_21051_((Attribute)ModAttributes.FRICTION.get());
      return friction != null && friction.m_22135_() > (double)0.0F ? (float)friction.m_22135_() : original;
   }
}
