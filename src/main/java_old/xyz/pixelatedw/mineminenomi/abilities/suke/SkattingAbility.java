package xyz.pixelatedw.mineminenomi.abilities.suke;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SkattingAbility extends Ability {
   public static final RegistryObject<AbilityCore<SkattingAbility>> INSTANCE = ModRegistry.registerAbility("skatting", "Skatting", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns the user's entire body invisible.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SkattingAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);

   public SkattingAbility(AbilityCore<SkattingAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_21023_((MobEffect)ModEffects.SUKE_INVISIBILITY.get())) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SUKE_INVISIBILITY.get(), Integer.MAX_VALUE, 0, false, false, true));
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_21195_((MobEffect)ModEffects.SUKE_INVISIBILITY.get());
   }
}
