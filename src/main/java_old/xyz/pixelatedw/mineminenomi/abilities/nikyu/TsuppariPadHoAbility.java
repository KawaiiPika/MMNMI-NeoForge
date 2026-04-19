package xyz.pixelatedw.mineminenomi.abilities.nikyu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.PadHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class TsuppariPadHoAbility extends Ability {
   private static final int COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<TsuppariPadHoAbility>> INSTANCE = ModRegistry.registerAbility("tsuppari_pad_ho", "Tsuppari Pad Ho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a barrage of paw-shaped shockwaves at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TsuppariPadHoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::stopRepeaterEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public TsuppariPadHoAbility(AbilityCore<TsuppariPadHoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.repeaterComponent, this.animationComponent, this.projectileComponent, this.explosionComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 5, 4);
      this.animationComponent.start(entity, ModAnimations.PUNCH_RUSH);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.MULTIPLE_PAD_HO_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      for(int i = 0; i < 3; ++i) {
         this.projectileComponent.shoot(entity, 3.0F, 15.0F);
      }

   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 300.0F);
      this.animationComponent.stop(entity);
   }

   private PadHoProjectile createProjectile(LivingEntity entity) {
      PadHoProjectile proj = new PadHoProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(5.0F);
      return proj;
   }
}
