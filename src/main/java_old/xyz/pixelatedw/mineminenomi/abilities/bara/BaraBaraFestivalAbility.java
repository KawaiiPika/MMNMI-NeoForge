package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BaraBaraFestivalAbility extends Ability {
   private static final Component ATTACK_NAME;
   private static final Component SHIELD_NAME;
   private static final ResourceLocation BARA_BARA_FESTIVAL_ATTACK_ICON;
   private static final ResourceLocation BARA_BARA_FESTIVAL_SHIELD_ICON;
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 160;
   private static final int RANGE = 3;
   private static final int DAMAGE_ATTACK = 6;
   private static final int DAMAGE_SHIELD = 4;
   public static final RegistryObject<AbilityCore<BaraBaraFestivalAbility>> INSTANCE;
   private static final AbilityAttributeModifier STEP_HEIGHT;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::continuityStart).addTickEvent(this::continuityTick).addEndEvent(this::continuityEnd);
   private final AltModeComponent<Mode> altModeComponent;
   private final ChangeStatsComponent changeStatsComponent;
   private final GrabEntityComponent grabEntityComponent;
   private final DealDamageComponent dealDamageComponent;
   private final RangeComponent rangeComponent;
   private final MorphComponent morphComponent;
   private final PoolComponent poolComponent;
   private Interval hurtInterval;
   private BaraFestivalEntity baraFestivalEntity;

   public BaraBaraFestivalAbility(AbilityCore<BaraBaraFestivalAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, BaraBaraFestivalAbility.Mode.ATTACK)).addChangeModeEvent(this::altModeChange);
      this.changeStatsComponent = new ChangeStatsComponent(this);
      this.grabEntityComponent = new GrabEntityComponent(this, false, false, 0.0F);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.rangeComponent = new RangeComponent(this);
      this.morphComponent = new MorphComponent(this);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.hurtInterval = new Interval(20);
      this.changeStatsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.altModeComponent, this.changeStatsComponent, this.grabEntityComponent, this.dealDamageComponent, this.rangeComponent, this.morphComponent, this.poolComponent});
      this.addCanUseCheck(BaraHelper::hasLimbs);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         if (this.altModeComponent.getCurrentMode() == BaraBaraFestivalAbility.Mode.ATTACK) {
            HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)12.0F);
            if (!(mop instanceof EntityHitResult)) {
               WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NO_TARGET);
               return;
            }

            EntityHitResult entityResult = (EntityHitResult)mop;
            if (entityResult.m_82443_() instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)entityResult.m_82443_();
               boolean isLogia = (Boolean)DevilFruitCapability.get(target).map((props) -> props.isLogia()).orElse(false);
               if (!target.m_6084_() || isLogia && !HakiHelper.hasHardeningActive(entity) || CombatHelper.isTargetBlocking(entity, target)) {
                  return;
               }

               this.grabEntityComponent.grabManually(entity, target);
            }
         } else if (this.altModeComponent.getCurrentMode() == BaraBaraFestivalAbility.Mode.SHIELD) {
            this.grabEntityComponent.grabManually(entity, entity);
         }

         if (!this.grabEntityComponent.hasGrabbedEntity()) {
            WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NO_TARGET);
         } else {
            this.continuousComponent.startContinuity(entity, 160.0F);
         }
      }
   }

   private void continuityStart(LivingEntity entity, IAbility ability) {
      this.hurtInterval.restartIntervalToZero();
      if (this.grabEntityComponent.hasGrabbedEntity()) {
         this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.BARA_FESTIVAL.get());
         this.changeStatsComponent.applyModifiers(entity);
         LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
         this.baraFestivalEntity = new BaraFestivalEntity(entity.m_9236_(), entity);
         this.baraFestivalEntity.m_7678_(target.m_20185_(), target.m_20186_() + (double)1.0F, target.m_20189_(), 0.0F, 0.0F);
         this.baraFestivalEntity.setTarget(target);
         entity.m_9236_().m_7967_(this.baraFestivalEntity);
      }

   }

   private void continuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == BaraBaraFestivalAbility.Mode.ATTACK) {
            LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
            if (target == null || !target.m_6084_() || entity.m_20280_(target) > (double)1500.0F) {
               this.continuousComponent.stopContinuity(entity);
               return;
            }

            if (this.hurtInterval.canTick() && this.dealDamageComponent.hurtTarget(entity, target, 6.0F)) {
               target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 10, 1, true, true));
            }
         } else if (this.altModeComponent.getCurrentMode() == BaraBaraFestivalAbility.Mode.SHIELD) {
            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 3.0F)) {
               if (this.dealDamageComponent.hurtTarget(entity, target, 4.0F)) {
                  Vec3 dist = target.m_20182_().m_82546_(entity.m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F).m_82541_();
                  double power = (double)2.0F;
                  double xSpeed = -dist.f_82479_ * power;
                  double zSpeed = -dist.f_82481_ * power;
                  AbilityHelper.setDeltaMovement(target, -xSpeed, (double)0.2F, -zSpeed);
               }
            }
         }

         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NO_HANDS.get(), 5, 0));
      }
   }

   private void continuityEnd(LivingEntity entity, IAbility ability) {
      if (this.baraFestivalEntity != null) {
         this.baraFestivalEntity.m_142687_(RemovalReason.DISCARDED);
      }

      this.changeStatsComponent.removeModifiers(entity);
      this.grabEntityComponent.release(entity);
      this.morphComponent.stopMorph(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private boolean altModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == BaraBaraFestivalAbility.Mode.ATTACK) {
         this.setDisplayName(ATTACK_NAME);
         this.setDisplayIcon(BARA_BARA_FESTIVAL_ATTACK_ICON);
      } else if (mode == BaraBaraFestivalAbility.Mode.SHIELD) {
         this.setDisplayName(SHIELD_NAME);
         this.setDisplayIcon(BARA_BARA_FESTIVAL_SHIELD_ICON);
      }

      return true;
   }

   public void switchModeToAttack(LivingEntity entity) {
      if (!this.altModeComponent.isMode(BaraBaraFestivalAbility.Mode.ATTACK)) {
         this.altModeComponent.setMode(entity, BaraBaraFestivalAbility.Mode.ATTACK);
      }
   }

   public void switchModeToShield(LivingEntity entity) {
      if (!this.altModeComponent.isMode(BaraBaraFestivalAbility.Mode.SHIELD)) {
         this.altModeComponent.setMode(entity, BaraBaraFestivalAbility.Mode.SHIELD);
      }
   }

   public boolean isAttackMode() {
      return this.altModeComponent.isMode(BaraBaraFestivalAbility.Mode.ATTACK);
   }

   public boolean isShieldMode() {
      return this.altModeComponent.isMode(BaraBaraFestivalAbility.Mode.SHIELD);
   }

   static {
      ATTACK_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "bara_bara_festival", "Bara Bara Festival: Attack"));
      SHIELD_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "bara_bara_festival_shield", "Bara Bara Festival: Shield"));
      BARA_BARA_FESTIVAL_ATTACK_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/bara_bara_festival.png");
      BARA_BARA_FESTIVAL_SHIELD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/bara_bara_festival_shield.png");
      INSTANCE = ModRegistry.registerAbility("bara_bara_festival", "Bara Bara Festival", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Splits the user's body in several small parts that constantly hurt and slow down the target.", (Object)null), ImmutablePair.of("Splits the user's body in several small parts that swarm around the user acting as a shield.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BaraBaraFestivalAbility::new)).addAdvancedDescriptionLine(CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(160.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(ATTACK_NAME)), AbilityDescriptionLine.IDescriptionLine.of(desc[0]), DealDamageComponent.getTooltip(6.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(SHIELD_NAME)), AbilityDescriptionLine.IDescriptionLine.of(desc[1]), RangeComponent.getTooltip(3.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(4.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
      });
      STEP_HEIGHT = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Bara Bara Festival Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }

   public static enum Mode {
      ATTACK,
      SHIELD;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{ATTACK, SHIELD};
      }
   }
}
