package xyz.pixelatedw.mineminenomi.abilities.kira;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BrilliantPunkAbility extends Ability {
   private static final int HOLD_TIME = 10;
   private static final int COOLDOWN = 200;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 25.0F;
   public static final RegistryObject<AbilityCore<BrilliantPunkAbility>> INSTANCE = ModRegistry.registerAbility("brilliant_punk", "Brilliant Punk", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user rams into the target with their diamond body.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BrilliantPunkAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(25.0F)).setSourceType(SourceType.PHYSICAL).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);

   public BrilliantPunkAbility(AbilityCore<BrilliantPunkAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent, this.hitTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(this::requiresDiamondBody);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 10.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.animationComponent.start(entity, ModAnimations.TACKLE);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         Vec3 look = entity.m_20154_();
         Vec3 speed = look.m_82542_(2.3, (double)0.0F, 2.3);
         entity.m_6478_(MoverType.SELF, speed);

         for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.0F, 1.6F)) {
            if (this.hitTrackerComponent.canHit(target)) {
               this.dealDamageComponent.hurtTarget(entity, target, 25.0F);
            }
         }

         for(Entity target : this.hitTrackerComponent.getHits()) {
            target.m_6021_(entity.m_20185_() + look.f_82479_, entity.m_20186_(), entity.m_20189_() + look.f_82481_);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private Result requiresDiamondBody(LivingEntity entity, IAbility ability) {
      boolean isAbilityActive = (Boolean)AbilityCapability.get(entity).map((props) -> (DiamondBodyAbility)props.getEquippedAbility((AbilityCore)DiamondBodyAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (!isAbilityActive) {
         Component text = Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{((AbilityCore)DiamondBodyAbility.INSTANCE.get()).getUnlocalizedName()});
         return Result.fail(text);
      } else {
         return Result.success();
      }
   }
}
