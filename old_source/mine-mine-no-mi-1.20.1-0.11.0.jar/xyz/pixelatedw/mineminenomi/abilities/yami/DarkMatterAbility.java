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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.DarkMatterProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DarkMatterAbility extends Ability {
   private static final int COOLDOWN = 280;
   private static final int CHARGE_TIME = 80;
   public static final RegistryObject<AbilityCore<DarkMatterAbility>> INSTANCE = ModRegistry.registerAbility("dark_matter", "Dark Matter", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a ball of darkness that engulfs the opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DarkMatterAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(280.0F), ChargeComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.GRAVITY).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private DarkMatterProjectile proj = null;
   private Interval particleInterval = new Interval(2);

   public DarkMatterAbility(AbilityCore<DarkMatterAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 80.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.proj = null;
      this.particleInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.proj == null) {
         this.proj = (DarkMatterProjectile)this.projectileComponent.getNewProjectile(entity);
         entity.m_9236_().m_7967_(this.proj);
      } else {
         this.proj.setLife(this.proj.getMaxLife());
         this.proj.m_6034_(entity.m_20185_(), entity.m_20188_() + (double)3.0F, entity.m_20189_());
      }

      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DARK_MATTER_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.proj.m_37251_(entity, entity.m_146909_() + 10.0F, entity.m_146908_(), 0.0F, 3.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 280.0F);
   }

   private DarkMatterProjectile createProjectile(LivingEntity entity) {
      DarkMatterProjectile proj = new DarkMatterProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
