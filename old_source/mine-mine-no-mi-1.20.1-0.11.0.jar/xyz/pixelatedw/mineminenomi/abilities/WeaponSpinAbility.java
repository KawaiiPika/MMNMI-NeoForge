package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WeaponSpinAbility extends Ability {
   private static final int HOLD_TIME = 80;
   private static final float COOLDOWN = 240.0F;
   private static final float DAMAGE = 15.0F;
   private static final float RANGE = 4.5F;
   public static final RegistryObject<AbilityCore<WeaponSpinAbility>> INSTANCE = ModRegistry.registerAbility("weapon_spin", "Weapon Spin", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Spins around hitting all nearby enemies with the held weapon.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, WeaponSpinAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(80.0F), DealDamageComponent.getTooltip(15.0F), RangeComponent.getTooltip(4.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::onStartContinuousEvent).addTickEvent(100, this::onTickContinuousEvent).addEndEvent(100, this::onEndContinuousEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public WeaponSpinAbility(AbilityCore<WeaponSpinAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresBluntWeapon);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 80.0F);
   }

   private void onStartContinuousEvent(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      stack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      this.animationComponent.start(entity, ModAnimations.SPIN_TO_WIN);
   }

   private void onEndContinuousEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private void onTickContinuousEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 4.5F)) {
         this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
      }

      entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 5, 2, false, false));
   }
}
