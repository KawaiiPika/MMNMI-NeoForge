package xyz.pixelatedw.mineminenomi.abilities.yuki;

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

public class YukiLogiaAbility extends LogiaInvulnerabilityAbility {
   public static final RegistryObject<AbilityCore<YukiLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_yuki", "Logia Invulnerability Yuki", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, YukiLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).build("mineminenomi"));

   public YukiLogiaAbility(AbilityCore<? extends YukiLogiaAbility> ability) {
      super(ability, (SourceImmunityAbility.SourceImmunityInfo)null, ModParticleEffects.YUKI_LOGIA);
      this.onLogiaEffect = this::sideEffect;
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      attacker.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 40, 1));
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/yuki/YukiLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((YukiLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
