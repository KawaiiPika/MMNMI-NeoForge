package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class UnlockAbilityAction implements NodeUnlockAction {
   private final RegistryObject<? extends AbilityCore<?>> core;

   public static UnlockAbilityAction unlock(RegistryObject<? extends AbilityCore<?>> core) {
      return new UnlockAbilityAction(core);
   }

   public UnlockAbilityAction(RegistryObject<? extends AbilityCore<?>> core) {
      this.core = core;

      assert this.core != null : "Ability Core is null";

   }

   public void onUnlock(LivingEntity entity) {
      AbilityHelper.checkAndUnlockAbility(entity, (AbilityCore)this.core.get());
   }

   public void onLock(LivingEntity entity) {
      AbilityCapability.get(entity).ifPresent((props) -> props.removeUnlockedAbility((AbilityCore)this.core.get()));
   }
}
