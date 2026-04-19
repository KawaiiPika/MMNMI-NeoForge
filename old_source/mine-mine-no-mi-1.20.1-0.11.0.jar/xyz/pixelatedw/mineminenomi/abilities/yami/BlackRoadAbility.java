package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.BlackRoadProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackRoadAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 60;
   private static final int RANGE = 24;
   public static final RegistryObject<AbilityCore<BlackRoadAbility>> INSTANCE = ModRegistry.registerAbility("black_road", "Black Road", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user spreads darkness in a forward path.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BlackRoadAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(24.0F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.GRAVITY).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() > 0.2)).addStartEvent(100, this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private Interval particleInterval = new Interval(2);

   public BlackRoadAbility(AbilityCore<BlackRoadAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DARK_MATTER_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 1.1F, 1.0F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DARK_MATTER_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.cooldownComponent.startCooldown(entity, 400.0F);
   }

   private BlackRoadProjectile createProjectile(LivingEntity entity) {
      int range = (int)(24.0F * this.chargeComponent.getChargePercentage());
      BlackRoadProjectile proj = new BlackRoadProjectile(entity.m_9236_(), entity, this);
      proj.setLife(range);
      return proj;
   }
}
