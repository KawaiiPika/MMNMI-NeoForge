package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.lang.invoke.SerializedLambda;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GoroLogiaAbility extends LogiaInvulnerabilityAbility {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/logia_invulnerability_goro.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/logia_invulnerability_goro.png");
   public static final RegistryObject<AbilityCore<GoroLogiaAbility>> INSTANCE = ModRegistry.registerAbility("logia_invulnerability_goro", "Logia Invulnerability Goro", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GoroLogiaAbility::new)).addDescriptionLine(LogiaInvulnerabilityAbility.DESCRIPTION).setIcon(DEFAULT_ICON).setSourceElement(SourceElement.LIGHTNING).build("mineminenomi"));
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public GoroLogiaAbility(AbilityCore<? extends GoroLogiaAbility> ability) {
      super(ability, (SourceImmunityAbility.SourceImmunityInfo)null, ModParticleEffects.GORO_LOGIA);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent});
      this.onLogiaEffect = this::sideEffect;
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, PassiveAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
      if (ClientConfig.isGoroBlue()) {
         this.setDisplayIcon(ALT_ICON);
      }

   }

   public boolean isDamageTaken(LivingEntity entity, DamageSource source, float amount) {
      return source instanceof AbilityDamageSource && ((AbilityDamageSource)source).getElement() == SourceElement.RUBBER ? true : super.isDamageTaken(entity, source, amount);
   }

   public boolean sideEffect(LivingEntity target, LivingEntity attacker) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(attacker).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         boolean attackerHasGomu = props.hasDevilFruit(ModFruits.GOMU_GOMU_NO_MI) && attacker.m_21205_().m_41619_();
         if (!attackerHasGomu) {
            this.dealDamageComponent.hurtTarget(attacker, target, 5.0F);
            return false;
         } else {
            return true;
         }
      }
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      switch (lambda.getImplMethodName()) {
         case "sideEffect":
            if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("xyz/pixelatedw/mineminenomi/abilities/LogiaInvulnerabilityAbility$ILogiaReturnEffect") && lambda.getFunctionalInterfaceMethodName().equals("returnEffect") && lambda.getFunctionalInterfaceMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z") && lambda.getImplClass().equals("xyz/pixelatedw/mineminenomi/abilities/goro/GoroLogiaAbility") && lambda.getImplMethodSignature().equals("(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z")) {
               return ((GoroLogiaAbility)lambda.getCapturedArg(0))::sideEffect;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
      }
   }
}
