package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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

public class FiddleBanffAbility extends Ability {
   private static final int COOLDOWN = 140;
   private static final int CONTINUITY = 20;
   private static final float AREA_SIZE = 1.6F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<FiddleBanffAbility>> INSTANCE = ModRegistry.registerAbility("fiddle_banff", "Fiddle Banff", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Running into enemies deals damage and knocks them back.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FiddleBanffAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.BISON_HEAVY, ModMorphs.BISON_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ContinuousComponent.getTooltip(20.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(15.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(100, this::stopContinuityEvent).addTickEvent(100, this::tickContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);

   public FiddleBanffAbility(AbilityCore<FiddleBanffAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.hitTrackerComponent, this.rangeComponent, this.dashComponent});
      this.dashComponent.setFixedCamera(true);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(UshiBisonHelper::requiresEitherPoints);
      this.addContinueUseCheck(UshiBisonHelper::requiresEitherPoints);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.dashComponent.timedDashXZ(entity, 3.0F, 10);
      this.continuousComponent.startContinuity(entity, 20.0F);
   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 140.0F);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.6F)) {
         if (this.hitTrackerComponent.canHit(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
         }
      }

   }
}
