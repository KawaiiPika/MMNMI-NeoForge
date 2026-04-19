package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HeartRegenAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<HeartRegenAbility>> INSTANCE = ModRegistry.registerAbility("heart_regen", "Heart Regen", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HeartRegenAbility::new)).setHidden().build("mineminenomi"));
   private int ticksWithoutHeart = 0;

   public HeartRegenAbility(AbilityCore<HeartRegenAbility> ability) {
      super(ability);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         if (!props.hasHeart()) {
            ++this.ticksWithoutHeart;
            if (this.ticksWithoutHeart == 100) {
               props.setHeart(true);
               this.ticksWithoutHeart = 0;
            }
         }

      }
   }
}
