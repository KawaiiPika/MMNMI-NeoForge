package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YukiRabiAbility extends Ability {
   private static final float COOLDOWN = 120.0F;
   public static final RegistryObject<AbilityCore<YukiRabiAbility>> INSTANCE = ModRegistry.registerAbility("yuki_rabi", "Yuki Rabi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches numerous hardened snowballs, shaped like a rabbit's head, that can inflict %s on their enemies.", new Object[]{ModEffects.FROSTBITE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, YukiRabiAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.ICE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public YukiRabiAbility(AbilityCore<YukiRabiAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.repeaterComponent, this.projectileComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.repeaterComponent.start(entity, 6, 3);
      }
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 2.0F);
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      super.cooldownComponent.startCooldown(entity, 120.0F);
   }

   private YukiRabiProjectile createProjectile(LivingEntity entity) {
      return new YukiRabiProjectile(entity.m_9236_(), entity, this);
   }
}
