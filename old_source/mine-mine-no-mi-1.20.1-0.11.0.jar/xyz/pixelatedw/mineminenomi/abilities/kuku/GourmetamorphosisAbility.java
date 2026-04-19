package xyz.pixelatedw.mineminenomi.abilities.kuku;

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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GourmetamorphosisAbility extends Ability {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 20;
   private static final int MAX_COOLDOWN = 620;
   public static final RegistryObject<AbilityCore<GourmetamorphosisAbility>> INSTANCE = ModRegistry.registerAbility("gourmetamorphosis", "Gourmetamorphosis", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes all items in the user's inventory edible.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GourmetamorphosisAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 620.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::endContinuityEvent);

   public GourmetamorphosisAbility(AbilityCore<GourmetamorphosisAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = 20.0F + this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }
}
