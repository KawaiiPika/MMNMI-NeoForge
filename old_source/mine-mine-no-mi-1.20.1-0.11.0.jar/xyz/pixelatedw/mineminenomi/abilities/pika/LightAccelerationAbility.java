package xyz.pixelatedw.mineminenomi.abilities.pika;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika.AmaterasuProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class LightAccelerationAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int HOLD_TIME = 400;
   public static final RegistryObject<AbilityCore<LightAccelerationAbility>> INSTANCE = ModRegistry.registerAbility("light_acceleration", "Light Acceleration", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user accelerates its attacks by converting into light before impact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, LightAccelerationAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(400.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.LIGHT).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addTickEvent(this::duringContinuity).addEndEvent(this::onContinuityEnd);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(0, this::onSwing);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private final SkinOverlayComponent skinOverlayComponent;
   private final HitTriggerComponent hitTriggerComponent;
   private final ProjectileComponent projectileComponent;
   private final ExplosionComponent explosionComponent;
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private boolean hasSwung;
   private boolean hasTarget;

   public LightAccelerationAbility(AbilityCore<LightAccelerationAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.explosionComponent = new ExplosionComponent(this);
      this.hasSwung = false;
      this.hasTarget = false;
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22281_, STRENGTH_MODIFIER, (entity) -> this.continuousComponent.isContinuous() && entity.m_21205_().m_41619_());
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, (entity) -> this.continuousComponent.isContinuous() && entity.m_21205_().m_41619_());
      super.addComponents(this.continuousComponent, this.swingTriggerComponent, this.changeStatsComponent, this.skinOverlayComponent, this.hitTriggerComponent, this.projectileComponent, this.explosionComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 400.0F);
   }

   private void onSwing(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.continuousComponent.isContinuous()) {
            this.hasTarget = false;
            this.hasSwung = true;
         }

      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void duringContinuity(LivingEntity entity, IAbility ability) {
      if (!this.hasTarget && this.hasSwung) {
         this.hasSwung = false;
         this.projectileComponent.shoot(entity, 5.0F, 1.0F);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PIKA_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.hasTarget = true;
      }

      return HitTriggerComponent.HitResult.PASS;
   }

   private AmaterasuProjectile createProjectile(LivingEntity entity) {
      AmaterasuProjectile proj = new AmaterasuProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(40.0F);
      proj.setArmorPiercing(0.5F);
      proj.setMaxLife(30);
      return proj;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setRenderType(AbilityOverlay.RenderType.ENERGY).setColor(new Color(255, 220, 0)).build();
      STRENGTH_MODIFIER = new AbilityAttributeModifier(UUID.fromString("ef9423e5-9be0-4223-a34a-538cb74d6e54"), INSTANCE, "Light Acceleration Strength Modifier", (double)10.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("1d78a133-8a0e-4b8f-8790-1360007d4741"), INSTANCE, "Light Acceleration Attack Speed Modifier", (double)4.0F, Operation.ADDITION);
   }
}
