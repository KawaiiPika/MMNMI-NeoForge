package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class TankyudonAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final float RANGE = 1.2F;
   private static final double PUSH_FORCE = 2.2;
   private static final float DAMAGE = 10.0F;
   public static final RegistryObject<AbilityCore<TankyudonAbility>> INSTANCE = ModRegistry.registerAbility("tankyudon", "Tankyudon", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Flying into enemies at great speeds deals damage and knocks them back.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TankyudonAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PTERA_FLY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(), DealDamageComponent.getTooltip(10.0F), RangeComponent.getTooltip(1.2F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private Interval clearHitsInterval = new Interval(25);

   public TankyudonAbility(AbilityCore<TankyudonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.hitTrackerComponent, this.rangeComponent});
      this.addCanUseCheck(RyuPteraHelper::requiresFlyPoint);
      this.addContinueUseCheck(RyuPteraHelper::requiresFlyPoint);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, WyHelper.secondsToTicks(10.0F));
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.hitTrackerComponent.clearHits();
         this.clearHitsInterval.restartIntervalToZero();
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (super.canUse(entity).isFail()) {
            this.continuousComponent.stopContinuity(entity);
         } else {
            List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 1.2F, 1.5F);
            if (this.clearHitsInterval.canTick()) {
               this.hitTrackerComponent.clearHits();
            }

            for(LivingEntity target : targets) {
               if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
                  Vec3 pushVec = entity.m_20154_().m_82546_(target.m_20154_()).m_82541_().m_82490_(2.2);
                  AbilityHelper.setDeltaMovement(target, pushVec);
               }
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }
}
