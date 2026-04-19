package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MizuShuryudanAbility extends Ability {
   private static final int COOLDOWN = 240;
   public static final RegistryObject<AbilityCore<MizuShuryudanAbility>> INSTANCE = ModRegistry.registerAbility("mizu_shuryudan", "Mizu Shuryudan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches multiple bubbles in the direction the user is looking, these bubbles will linger around until an enemy comes close to them at which point they'll start targeting the enemy and move towards them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, MizuShuryudanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::triggerRepeaterEvent).addStopEvent(100, this::stopRepeaterEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private int projectilesSpawned = 5;

   public MizuShuryudanAbility(AbilityCore<MizuShuryudanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.repeaterComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, this.projectilesSpawned, 5);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      float speed = 1.0F + entity.m_217043_().m_188501_();
      this.projectileComponent.shoot(entity, speed, 10.0F);
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private MizuShuryudanProjectile createProjectile(LivingEntity entity) {
      MizuShuryudanProjectile projectile = new MizuShuryudanProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }

   public void setProjectilesNumber(int projectiles) {
      this.projectilesSpawned = projectiles;
   }
}
