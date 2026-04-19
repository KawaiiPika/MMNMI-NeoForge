package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.ability.component.CSwingTriggerPacket;

public class ComponentsHandler {
   public static boolean triggerBowShootComponents(LivingEntity entity) {
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData != null) {
         for(IAbility abl : abilityData.getEquippedAbilities()) {
            Optional<BowTriggerComponent> opt = abl.<BowTriggerComponent>getComponent((AbilityComponentKey)ModAbilityComponents.BOW_TRIGGER.get());
            if (opt.isPresent() && ((BowTriggerComponent)opt.get()).tryShoot(entity)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean tryTriggerHitComponents(LivingEntity attacker, LivingEntity target, DamageSource damageSource) {
      Set<HitTriggerComponent> components = new HashSet();
      IAbilityData props = (IAbilityData)AbilityCapability.get(attacker).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         for(IAbility ability : props.getEquippedAndPassiveAbilities((core) -> core.hasComponent((AbilityComponentKey)ModAbilityComponents.HIT_TRIGGER.get()))) {
            HitTriggerComponent hitComponent = (HitTriggerComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.HIT_TRIGGER.get()).get();
            if (hitComponent.tryHit(attacker, target, damageSource) == HitTriggerComponent.HitResult.HIT) {
               components.add(hitComponent);
            }
         }

         CombatCapability.get(attacker).ifPresent((combatProps) -> combatProps.setHitTriggers(new HashSet(components)));
         components.removeIf((comp) -> !comp.getAbility().getCore().hasType(SourceType.FRIENDLY));
         if (components.size() > 0) {
            triggerHitComponents(attacker, target, damageSource, components);
            IDamageSourceHandler.getHandler(damageSource).reset();
            return true;
         } else {
            return false;
         }
      }
   }

   public static void triggerHitComponents(LivingEntity attacker, LivingEntity target, DamageSource damageSource, @Nullable Set<HitTriggerComponent> components) {
      if (components == null) {
         components = (Set)CombatCapability.get(attacker).map((props) -> props.getHitTriggers()).orElse(new HashSet());
         components.removeIf((compx) -> compx.getAbility().getCore().hasType(SourceType.FRIENDLY));
      }

      if (!components.isEmpty()) {
         for(HitTriggerComponent comp : components) {
            comp.onHit(attacker, target, damageSource);
         }
      }

   }

   public static float handleDamageTakenComponents(LivingEntity entity, DamageSource source, float amount, DamageTakenComponent.DamageState state) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return amount;
      } else {
         for(IAbility ability : props.getEquippedAndPassiveAbilities((core) -> core.hasComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE_TAKEN.get()))) {
            boolean isOutOfBodyDamage = ability instanceof OutOfBodyAbility && source.equals(ModDamageSources.getInstance().outOfBody());
            if (isOutOfBodyDamage) {
               return amount;
            }

            DamageTakenComponent damageTakenComponent = (DamageTakenComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE_TAKEN.get()).get();
            amount = damageTakenComponent.checkDamageTaken(entity, source, amount, state);
         }

         return amount;
      }
   }

   public static class Client {
      public static void triggerSwingComponents(LocalPlayer player) {
         IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (abilityData != null) {
            for(IAbility abl : abilityData.getEquippedAndPassiveAbilities()) {
               abl.getComponent((AbilityComponentKey)ModAbilityComponents.SWING_TRIGGER.get()).ifPresent((component) -> ModNetwork.sendToServer(new CSwingTriggerPacket(player, abl)));
            }
         }

      }
   }
}
