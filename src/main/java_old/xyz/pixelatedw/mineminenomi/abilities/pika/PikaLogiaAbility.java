package xyz.pixelatedw.mineminenomi.abilities.pika;

import java.lang.invoke.SerializedLambda;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PikaLogiaAbility extends LogiaInvulnerabilityAbility {
   public static final RegistryObject<AbilityCore<PikaLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_pika", "Logia Invulnerability Pika", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PikaLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).build("mineminenomi"));

   public PikaLogiaAbility(AbilityCore<? extends PikaLogiaAbility> ability) {
      super(ability, (SourceImmunityAbility.SourceImmunityInfo)null, ModParticleEffects.PIKA_LOGIA);
      this.onLogiaEffect = this::sideEffect;
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      attacker.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 60, 2));
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/pika/PikaLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((PikaLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
