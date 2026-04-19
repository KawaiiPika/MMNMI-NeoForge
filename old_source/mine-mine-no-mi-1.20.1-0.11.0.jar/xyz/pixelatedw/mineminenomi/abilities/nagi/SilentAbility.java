package xyz.pixelatedw.mineminenomi.abilities.nagi;

import java.util.List;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SilentAbility extends Ability {
   private static final TargetPredicate TARGET_CHECK = (new TargetPredicate()).testFriendlyFaction();
   public static final int RANGE = 30;
   public static final RegistryObject<AbilityCore<SilentAbility>> INSTANCE = ModRegistry.registerAbility("silent", "Silent", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Cancels all noises caused by or around the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SilentAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, RangeComponent.getTooltip(30.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public SilentAbility(AbilityCore<SilentAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() % 15.0F == 0.0F) {
         List<LivingEntity> friendlyTargets = this.rangeComponent.getTargetsInArea(entity, 30.0F, TARGET_CHECK);
         friendlyTargets.add(entity);

         for(LivingEntity target : friendlyTargets) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SILENT.get(), 20, 0, false, false));
         }
      }

   }
}
