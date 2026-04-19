package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GearThirdAbility extends Ability {
   private static final int HOLD_TIME = 400;
   private static final int MIN_COOLDOWN = 60;
   private static final float MAX_COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<GearThirdAbility>> INSTANCE = ModRegistry.registerAbility("gear_third", "Gear Third", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By blowing air and inflating their body, the user's attacks get bigger and gain incredible strength.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GearThirdAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 400.0F), ContinuousComponent.getTooltip(400.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);

   public GearThirdAbility(AbilityCore<GearThirdAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(GomuHelper.canUseGearCheck((AbilityCore)INSTANCE.get()));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 400.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchThirdGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchThirdGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchThirdGear(entity);
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchNoGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchNoGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchNoGear(entity);
         }

         float cooldown = Math.max(60.0F, this.continuousComponent.getContinueTime());
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }
}
