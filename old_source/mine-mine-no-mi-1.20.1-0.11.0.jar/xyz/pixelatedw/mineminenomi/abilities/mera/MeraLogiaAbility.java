package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.lang.invoke.SerializedLambda;
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

public class MeraLogiaAbility extends LogiaInvulnerabilityAbility {
   private static final int DAMAGE = 5;
   public static final RegistryObject<AbilityCore<MeraLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_mera", "Logia Invulnerability Mera", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, MeraLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).setSourceElement(SourceElement.FIRE).build("mineminenomi"));
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public MeraLogiaAbility(AbilityCore<? extends MeraLogiaAbility> ability) {
      super(ability, SourceImmunityAbility.FIRE_IMMUNITY, ModParticleEffects.MERA_LOGIA);
      this.onLogiaEffect = this::sideEffect;
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent});
   }

   public boolean sideEffect(LivingEntity entity, LivingEntity attacker) {
      attacker.m_20254_(3);
      this.dealDamageComponent.hurtTarget(entity, attacker, 5.0F);
      return false;
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/mera/MeraLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((MeraLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
