package xyz.pixelatedw.mineminenomi.abilities.zushi;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.zushi.GraviZoneParticleEffect;

public class GraviZoneAbility extends Ability {
   private static final Component REJECT_NAME;
   private static final ResourceLocation GRAVI_ZONE_GUARD_ICON;
   private static final ResourceLocation GRAVI_ZONE_REJECT_ICON;
   private static final int COOLDOWN = 200;
   private static final int GUARD_HOLD_TIME = 100;
   private static final int REJECT_HOLD_TIME = 160;
   private static final int GUARD_RANGE = 8;
   private static final int REJECT_RANGE = 3;
   private static final int REJECT_DAMAGE = 10;
   public static final RegistryObject<AbilityCore<GraviZoneAbility>> INSTANCE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final AltModeComponent<Mode> altModeComponent;
   private final RangeComponent rangeComponent;
   private final DealDamageComponent dealDamageComponent;
   private Interval gravityRingInterval;

   public GraviZoneAbility(AbilityCore<GraviZoneAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, GraviZoneAbility.Mode.GUARD)).addChangeModeEvent(this::onAltModeChange);
      this.rangeComponent = new RangeComponent(this);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.gravityRingInterval = new Interval(10);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.altModeComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == GraviZoneAbility.Mode.GUARD) {
         this.continuousComponent.triggerContinuity(entity, 100.0F);
      } else if (this.altModeComponent.getCurrentMode() == GraviZoneAbility.Mode.REJECT) {
         this.continuousComponent.triggerContinuity(entity, 160.0F);
      }

   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.gravityRingInterval.restartIntervalToZero();
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         boolean spawnGravityRings = this.gravityRingInterval.canTick();
         if (this.altModeComponent.getCurrentMode() == GraviZoneAbility.Mode.GUARD) {
            int range = 8;
            List<Entity> list = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)8.0F, ModEntityPredicates.getEnemyFactions(entity), Entity.class);
            list.forEach((target) -> {
               target.m_6034_(target.f_19854_, target.f_19855_, target.f_19856_);
               if (target instanceof LivingEntity) {
                  ((LivingEntity)target).m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
               }

            });
            if (spawnGravityRings) {
               gravityRing(entity, range, 0, true);
               gravityRing(entity, range - 2, 4, true);
               gravityRing(entity, range - 4, 8, true);
            }
         } else if (this.altModeComponent.getCurrentMode() == GraviZoneAbility.Mode.REJECT) {
            int range = 3;
            List<Entity> list = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)range, ModEntityPredicates.getEnemyFactions(entity), Entity.class);
            list.forEach((target) -> {
               boolean causedDamage = true;
               if (target instanceof LivingEntity) {
                  causedDamage = this.dealDamageComponent.hurtTarget(entity, (LivingEntity)target, 10.0F);
               }

               if (causedDamage) {
                  Vec3 dist = target.m_20182_().m_82546_(entity.m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F).m_82541_();
                  double power = (double)4.5F;
                  double xSpeed = -dist.f_82479_ * power;
                  double zSpeed = -dist.f_82481_ * power;
                  AbilityHelper.setDeltaMovement(target, -xSpeed, (double)0.2F, -zSpeed);
               }

            });
            if (spawnGravityRings) {
               gravityRing(entity, range + 3, 4, true);
               gravityRing(entity, range + 2, 2, true);
               gravityRing(entity, range, 0, true);
            }
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == GraviZoneAbility.Mode.GUARD) {
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         this.setDisplayIcon(GRAVI_ZONE_GUARD_ICON);
      } else if (mode == GraviZoneAbility.Mode.REJECT) {
         this.setDisplayName(REJECT_NAME);
         this.setDisplayIcon(GRAVI_ZONE_REJECT_ICON);
      }

      return true;
   }

   public static void gravityRing(LivingEntity entity, int range, int yOffset, boolean visibleOnlyFromOwner) {
      GraviZoneParticleEffect.Details details = new GraviZoneParticleEffect.Details();
      details.setRange(range);
      details.setYOffset(yOffset);
      if (visibleOnlyFromOwner && entity instanceof Player player) {
         WyHelper.spawnParticleEffectForOwner((ParticleEffect)ModParticleEffects.GRAVI_ZONE.get(), player, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), details);
      } else {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GRAVI_ZONE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), details);
      }

   }

   static {
      REJECT_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gravi_zone_reject", "Gravi Zone: Reject"));
      GRAVI_ZONE_GUARD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gravi_zone.png");
      GRAVI_ZONE_REJECT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gravi_zone_reject.png");
      INSTANCE = ModRegistry.registerAbility("gravi_zone", "Gravi Zone: Guard", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates an area in which entities cannot move.", (Object)null), ImmutablePair.of("Creates an area which pushed away all enemies.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GraviZoneAbility::new)).addDescriptionLine(desc).addDescriptionLine((e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0], CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(100.0F), RangeComponent.getTooltip(8.0F, RangeComponent.RangeType.AOE)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> REJECT_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1], CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(160.0F), RangeComponent.getTooltip(3.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(10.0F)).build("mineminenomi");
      });
   }

   public static enum Mode {
      GUARD,
      REJECT;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{GUARD, REJECT};
      }
   }
}
