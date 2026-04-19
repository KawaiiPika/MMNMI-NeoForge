package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika.YasakaniNoMagatamaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class YasakaniNoMagatamaAbility extends Ability {
   private static final int COOLDOWN = 280;
   public static final RegistryObject<AbilityCore<YasakaniNoMagatamaAbility>> INSTANCE = ModRegistry.registerAbility("yasakani_no_magatama", "Yasakani no Magatama", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a torrent of deadly light particles, causing huge destruction", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, YasakaniNoMagatamaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(280.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHT).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::repeaterTriggerEvent).addStopEvent(this::repeaterStopEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public YasakaniNoMagatamaAbility(AbilityCore<YasakaniNoMagatamaAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.repeaterComponent, this.animationComponent, this.projectileComponent, this.explosionComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
         this.repeaterComponent.start(entity, 25, 3);
      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
      AbilityHelper.slowEntityFall(entity);
   }

   private void repeaterTriggerEvent(LivingEntity entity, IAbility ability) {
      if (super.canUse(entity).isFail()) {
         this.repeaterComponent.stop(entity);
      }

      this.projectileComponent.shoot(entity, 10.5F, 20.0F);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PIKA_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void repeaterStopEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 280.0F);
   }

   private YasakaniNoMagatamaProjectile createProjectile(LivingEntity entity) {
      YasakaniNoMagatamaProjectile proj = new YasakaniNoMagatamaProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
