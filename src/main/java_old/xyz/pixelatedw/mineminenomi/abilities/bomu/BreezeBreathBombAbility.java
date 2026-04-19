package xyz.pixelatedw.mineminenomi.abilities.bomu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.BreezeBreathBombProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BreezeBreathBombAbility extends Ability {
   private static final float COOLDOWN = 300.0F;
   public static final RegistryObject<AbilityCore<BreezeBreathBombAbility>> INSTANCE = ModRegistry.registerAbility("breeze_breath_bomb", "Breeze Breath Bomb", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Load a gun with explosive breath and shoot a chain explosion", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BreezeBreathBombAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BULLET).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(100, this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = (new ProjectileComponent(this, this::createProjectile)).addAfterShootEvent(100, this::afterShootEvent);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public BreezeBreathBombAbility(AbilityCore<BreezeBreathBombAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private void afterShootEvent(LivingEntity entity) {
      this.continuousComponent.stopContinuity(entity);
   }

   public BreezeBreathBombProjectile createProjectile(LivingEntity entity) {
      BreezeBreathBombProjectile proj = new BreezeBreathBombProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
