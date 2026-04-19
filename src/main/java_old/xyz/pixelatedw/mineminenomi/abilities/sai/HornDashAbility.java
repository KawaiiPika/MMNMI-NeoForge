package xyz.pixelatedw.mineminenomi.abilities.sai;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HornDashAbility extends Ability {
   private static final int COOLDOWN = 180;
   private static final int HOLD_TIME = 20;
   private static final float AREA_SIZE = 1.7F;
   private static final float DAMAGE = 10.0F;
   public static final RegistryObject<AbilityCore<HornDashAbility>> INSTANCE = ModRegistry.registerAbility("horn_dash", "Horn Dash", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Running into enemies deals damage and knocks them back.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HornDashAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.SAI_HEAVY, ModMorphs.SAI_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F), ContinuousComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(10.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(100, this::stopContinuityEvent).addTickEvent(100, this::tickContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);

   public HornDashAbility(AbilityCore<HornDashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.hitTrackerComponent, this.rangeComponent, this.dashComponent});
      this.addCanUseCheck(SaiHelper::requiresEitherPoint);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.dashComponent.dashXYZ(entity, 3.0F, 0.2F);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.continuousComponent.startContinuity(entity, 20.0F);
   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 180.0F);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.7F)) {
         if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
            Vec3 speed = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)1.2F, (double)2.0F);
            AbilityHelper.setDeltaMovement(entity, speed.f_82479_, (double)0.5F, speed.f_82481_);
         }
      }

   }
}
