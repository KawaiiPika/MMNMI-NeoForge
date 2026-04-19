package xyz.pixelatedw.mineminenomi.abilities.netsu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.netsu.NekkaiGyoraiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NekkaiGyoraiAbility extends RepeaterAbility {
   private static final int COOLDOWN = 100;
   private static final int TRIGGERS = 5;
   private static final int INTERVAL = 5;
   public static final RegistryObject<AbilityCore<NekkaiGyoraiAbility>> INSTANCE = ModRegistry.registerAbility("nekkai_gyorai", "Nekkai Gyorai", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots heat-torpedoes, exploding and setting the enemy on fire.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NekkaiGyoraiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public NekkaiGyoraiAbility(AbilityCore<NekkaiGyoraiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   public int getMaxTriggers() {
      return 5;
   }

   public int getTriggerInterval() {
      return 5;
   }

   public float getRepeaterCooldown() {
      return 100.0F;
   }

   public NekkaiGyoraiProjectile getProjectileFactory(LivingEntity entity) {
      NekkaiGyoraiProjectile proj = new NekkaiGyoraiProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
