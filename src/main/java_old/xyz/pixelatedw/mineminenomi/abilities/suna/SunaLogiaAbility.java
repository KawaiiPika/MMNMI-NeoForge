package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.lang.invoke.SerializedLambda;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SunaLogiaAbility extends LogiaInvulnerabilityAbility {
   public static final RegistryObject<AbilityCore<SunaLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_suna", "Logia Invulnerability Suna", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, SunaLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).build("mineminenomi"));

   public SunaLogiaAbility(AbilityCore<? extends SunaLogiaAbility> ability) {
      super(ability, (SourceImmunityAbility.SourceImmunityInfo)null, ModParticleEffects.SUNA_LOGIA);
      this.onLogiaEffect = this::sideEffect;
   }

   public boolean isDamageTaken(LivingEntity entity, DamageSource source, float amount) {
      boolean isWaterElement = ((IDamageSourceHandler)source).hasElement(SourceElement.WATER);
      if (isWaterElement) {
         return true;
      } else {
         return SunaHelper.isWet(entity) ? true : super.isDamageTaken(entity, source, amount);
      }
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      attacker.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DEHYDRATION.get(), 40, 1));
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/suna/SunaLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((SunaLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
