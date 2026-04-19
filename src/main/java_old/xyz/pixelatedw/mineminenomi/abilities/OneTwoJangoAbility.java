package xyz.pixelatedw.mineminenomi.abilities;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class OneTwoJangoAbility extends Ability {
   private static final int CHARGE_TIME = 60;
   private static final int COOLDOWN = 200;
   public static final int RANGE = 15;
   public static final RegistryObject<AbilityCore<OneTwoJangoAbility>> INSTANCE = ModRegistry.registerAbility("one_two_jango", "One Two Jango", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id);
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, OneTwoJangoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(15.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::startChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private boolean canAffectSelf = false;

   public OneTwoJangoAbility(AbilityCore<OneTwoJangoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.rangeComponent});
      this.addCanUseCheck(OneTwoJangoAbility::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.ONE_TWO_JANGO, 60);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, entity.m_20183_(), 15.0F, TargetPredicate.EVERYTHING);
      if (this.canAffectSelf) {
         targets.add(entity);
      }

      for(LivingEntity target : targets) {
         if (entity != target && (!(target instanceof Player) || !TargetHelper.isEntityInView(target, entity, 60.0F))) {
            if (!(target instanceof Mob)) {
               continue;
            }

            Mob mobTarget = (Mob)target;
            if (!GoalHelper.canSee(mobTarget, entity)) {
               continue;
            }
         }

         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 60, 0));
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   public void setAffectSelf(boolean canAffectSelf) {
      this.canAffectSelf = canAffectSelf;
   }

   public static Result canUse(LivingEntity entity, IAbility ability) {
      return !entity.m_21205_().m_41619_() && entity.m_21205_().m_41720_().equals(ModWeapons.CHAKRAM.get()) ? Result.success() : Result.fail((Component)null);
   }
}
