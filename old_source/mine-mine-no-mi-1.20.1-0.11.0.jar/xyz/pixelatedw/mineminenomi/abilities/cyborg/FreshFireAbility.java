package xyz.pixelatedw.mineminenomi.abilities.cyborg;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class FreshFireAbility extends Ability {
   private static final float COOLDOWN = 100.0F;
   private static final int COLA_REQUIRED = 1;
   private static final int MAX_FIRE_BURSTS = 10;
   public static final RegistryObject<AbilityCore<FreshFireAbility>> INSTANCE = ModRegistry.registerAbility("fresh_fire", "Fresh Fire", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user heats up and breathes fire like a flamethrower at the opponenty.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, FreshFireAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ColaUsageComponent.getColaTooltip(10)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).setUnlockCheck(FreshFireAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::triggerRepeaterEvent).addStopEvent(100, this::stopRepeaterEvent);
   private final ColaUsageComponent colaUsageComponent = new ColaUsageComponent(this);

   public FreshFireAbility(AbilityCore<FreshFireAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.repeaterComponent, this.colaUsageComponent});
      this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(1));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.startContinuity(entity, -1.0F);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 10, 3);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         this.projectileComponent.shoot(entity, 2.0F, 5.0F);
         this.colaUsageComponent.consumeCola(entity, 1);
      }
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private FreshFireProjectile createProjectile(LivingEntity entity) {
      FreshFireProjectile proj = new FreshFireProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
