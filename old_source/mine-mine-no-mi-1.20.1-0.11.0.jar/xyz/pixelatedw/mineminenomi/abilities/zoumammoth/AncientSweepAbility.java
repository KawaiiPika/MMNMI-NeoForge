package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AncientSweepAbility extends Ability {
   private static final int COOLDOWN = 160;
   private static final int CHARGE_TIME = 40;
   private static final int HEAVY_RANGE = 3;
   private static final int GUARD_RANGE = 6;
   private static final int HEAVY_DAMAGE = 15;
   private static final int GUARD_DAMAGE = 20;
   private static final AbilityDescriptionLine.IDescriptionLine RANGE_TOOLTIP = (entity, ability) -> {
      int range = 3;
      if (((MorphInfo)ModMorphs.MAMMOTH_GUARD.get()).isActive(entity)) {
         range = 6;
      }

      return RangeComponent.getTooltip((float)range, RangeComponent.RangeType.LINE).expand(entity, ability);
   };
   private static final AbilityDescriptionLine.IDescriptionLine DAMAGE_TOOLTIP = (entity, ability) -> {
      int damage = 15;
      if (((MorphInfo)ModMorphs.MAMMOTH_GUARD.get()).isActive(entity)) {
         damage = 20;
      }

      return DealDamageComponent.getTooltip((float)damage).expand(entity, ability);
   };
   public static final RegistryObject<AbilityCore<AncientSweepAbility>> INSTANCE = ModRegistry.registerAbility("ancient_sweep", "Ancient Sweep", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hits all enemies in a frontal cone with your trunk.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AncientSweepAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.MAMMOTH_GUARD, ModMorphs.MAMMOTH_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ChargeComponent.getTooltip(40.0F), RANGE_TOOLTIP, DAMAGE_TOOLTIP).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addEndEvent(100, this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public AncientSweepAbility(AbilityCore<AncientSweepAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(ZouMammothHelper::requiresEitherPoint);
      this.addContinueUseCheck(ZouMammothHelper::requiresEitherPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 40.0F);
   }

   public void endChargeEvent(LivingEntity entity, IAbility ability) {
      float damage = 15.0F;
      float radius = 3.0F;
      if (((MorphInfo)ModMorphs.MAMMOTH_GUARD.get()).isActive(entity)) {
         radius *= 2.0F;
         damage += 5.0F;
      }

      for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, radius, 3.0F)) {
         if (this.dealDamageComponent.hurtTarget(entity, target, damage)) {
            Vec3 speed = entity.m_20154_().m_82541_().m_82542_((double)3.0F, (double)1.0F, (double)3.0F);
            AbilityHelper.setDeltaMovement(target, speed.f_82479_, entity.m_20184_().m_7098_() + (double)0.5F, speed.f_82481_);
         }
      }

      if (!entity.m_9236_().f_46443_) {
         entity.m_21011_(InteractionHand.MAIN_HAND, true);
      }

      this.cooldownComponent.startCooldown(entity, 160.0F);
   }
}
