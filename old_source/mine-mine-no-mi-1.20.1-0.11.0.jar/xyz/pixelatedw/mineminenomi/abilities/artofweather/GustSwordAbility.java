package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.GustSwordProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GustSwordAbility extends Ability {
   private static final float COOLDOWN = 10.0F;
   public static final RegistryObject<AbilityCore<GustSwordAbility>> INSTANCE = ModRegistry.registerAbility("gust_sword", "Gust Sword", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a concentrated wind blast forward, knocking back all enemies that it comes in contact with", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, GustSwordAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.AIR).setSourceHakiNature(SourceHakiNature.IMBUING).setUnlockCheck(GustSwordAbility::canUnlock).build("mineminenomi");
   });
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::endRepeaterEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuousEvent).addEndEvent(100, this::endContinuousEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public GustSwordAbility(AbilityCore<GustSwordAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.repeaterComponent, this.projectileComponent, this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuousEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 6, 3);
   }

   private void endContinuousEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 10.0F);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 3.0F);
   }

   private void endRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private GustSwordProjectile createProjectile(LivingEntity entity) {
      GustSwordProjectile proj = new GustSwordProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return props != null && questProps != null ? props.isWeatherWizard() : false;
      } else {
         return false;
      }
   }
}
