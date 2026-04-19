package xyz.pixelatedw.mineminenomi.abilities;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.mera.HikenAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TestAbility extends Ability {
   public static final RegistryObject<AbilityCore<TestAbility>> INSTANCE = ModRegistry.registerAbility("test", "Test", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Test Description \nAbility: %s\nItem: %s\nEffect: %s.", new Object[]{HikenAbility.INSTANCE, ModItems.COLA, ModEffects.BUBBLY_CORAL}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TestAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   protected final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public TestAbility(AbilityCore<TestAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::tickContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.TEST);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   public void appendDescription(Player player, List<Component> desc) {
      super.appendDescription(player, desc);
   }
}
