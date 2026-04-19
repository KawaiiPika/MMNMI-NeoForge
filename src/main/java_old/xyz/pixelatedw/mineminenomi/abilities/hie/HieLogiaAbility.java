package xyz.pixelatedw.mineminenomi.abilities.hie;

import java.lang.invoke.SerializedLambda;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HieLogiaAbility extends LogiaInvulnerabilityAbility {
   public static final RegistryObject<AbilityCore<HieLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_hie", "Logia Invulnerability Hie", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HieLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).build("mineminenomi"));

   public HieLogiaAbility(AbilityCore<? extends HieLogiaAbility> ability) {
      super(ability, (SourceImmunityAbility.SourceImmunityInfo)null, ModParticleEffects.HIE_LOGIA);
      this.onLogiaEffect = this::sideEffect;
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      attacker.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), 40, 0));
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/hie/HieLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((HieLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
