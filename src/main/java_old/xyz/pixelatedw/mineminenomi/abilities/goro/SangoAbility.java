package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.SangoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SangoAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/sango.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/sango.png");
   private static final int COOLDOWN = 240;
   private static final int CHARGE_TIME = 40;
   public static final RegistryObject<AbilityCore<SangoAbility>> INSTANCE = ModRegistry.registerAbility("sango", "Sango", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches powerful charges of electricity from the hands.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SangoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(40.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setIcon(DEFAULT_ICON).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final Interval particleInterval = new Interval(10);

   public SangoAbility(AbilityCore<SangoAbility> core) {
      super(core);
      this.setDisplayIcon(DEFAULT_ICON);
      if (ClientConfig.isGoroBlue()) {
         this.setDisplayIcon(ALT_ICON);
      }

      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.explosionComponent, this.projectileComponent});
      this.explosionComponent.setBlocksAffectedLimit(1508);
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
      if (ClientConfig.isGoroBlue()) {
         this.setDisplayIcon(ALT_ICON);
      }

   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 40.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.particleInterval.restartIntervalToZero();
         this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.particleInterval.canTick()) {
            EntityHitResult trace = WyHelper.rayTraceEntities(entity, 0.8);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SANGO.get(), entity, trace.m_82450_().m_7096_(), entity.m_20186_() + (double)1.0F, trace.m_82450_().m_7094_());
         }

      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_21011_(InteractionHand.MAIN_HAND, true);
         this.projectileComponent.shoot(entity);
         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 240.0F);
      }
   }

   private SangoProjectile createProjectile(LivingEntity entity) {
      SangoProjectile projectile = new SangoProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }
}
