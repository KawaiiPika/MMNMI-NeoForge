package xyz.pixelatedw.mineminenomi.abilities.magu;

import java.lang.invoke.SerializedLambda;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MaguLogiaAbility extends LogiaInvulnerabilityAbility {
   public static final RegistryObject<AbilityCore<MaguLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_magu", "Logia Invulnerability Magu", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, MaguLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).setSourceElement(SourceElement.MAGMA).build("mineminenomi"));
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public MaguLogiaAbility(AbilityCore<? extends MaguLogiaAbility> ability) {
      super(ability, SourceImmunityAbility.MAGMA_IMMUNITY, ModParticleEffects.MAGU_LOGIA);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent});
      this.onLogiaEffect = this::sideEffect;
   }

   public boolean isDamageTaken(LivingEntity entity, DamageSource source, float amount) {
      if (source.m_269150_().m_203565_(DamageTypes.f_268546_)) {
         entity.m_20095_();
         return false;
      } else {
         return super.isDamageTaken(entity, source, amount);
      }
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      attacker.m_20254_(5);
      this.dealDamageComponent.hurtTarget(attacker, target, 10.0F);
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/magu/MaguLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((MaguLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
