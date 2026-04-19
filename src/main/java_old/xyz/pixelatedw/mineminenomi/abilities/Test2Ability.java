package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Test2Ability extends Ability {
   public static final RegistryObject<AbilityCore<Test2Ability>> INSTANCE = ModRegistry.registerAbility("test_2", "Test 2", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Test 2 Description \nAbility: %s\nItem: %s\nEffect: %s.", new Object[]{TestAbility.INSTANCE, Items.f_42386_, MobEffects.f_19594_}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, Test2Ability::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   protected final ContinuousComponent continuousComponent = new ContinuousComponent(this);

   public Test2Ability(AbilityCore<Test2Ability> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::tickContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      ModMain.LOGGER.debug("start");
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      ModMain.LOGGER.debug("stop");
   }
}
