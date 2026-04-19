package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SunaFlyAbility extends FlyAbility {
   public static final RegistryObject<AbilityCore<SunaFlyAbility>> INSTANCE = ModRegistry.registerAbility("suna_special_fly", "Suna Special Fly", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to fly, cannot be used while the user is wet", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, SunaFlyAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public SunaFlyAbility(AbilityCore<SunaFlyAbility> core) {
      super(core);
   }

   public Result canUse(LivingEntity entity) {
      return SunaHelper.isWet(entity) ? Result.fail((Component)null) : super.canUse(entity);
   }

   public Supplier<ParticleEffect<?>> getParticleEffects() {
      return ModParticleEffects.SUNA_FLY;
   }

   public int getHeightDifference(Player player) {
      return 30;
   }

   public float getSpeed(Player player) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (props == null) {
         return 0.0F;
      } else {
         GrandeSablesAbility grandeSables = (GrandeSablesAbility)props.getEquippedAbility((AbilityCore)GrandeSablesAbility.INSTANCE.get());
         BarjanAbility barjanAbility = (BarjanAbility)props.getEquippedAbility((AbilityCore)BarjanAbility.INSTANCE.get());
         if ((grandeSables == null || !grandeSables.isContinuous()) && (barjanAbility == null || !barjanAbility.isContinuous())) {
            float speed = 0.2F;
            if (SunaHelper.isFruitBoosted(player)) {
               speed = 0.55F;
            }

            if (!WyHelper.isInCombat(player)) {
               speed += speed * 0.3F;
            }

            return speed;
         } else {
            return 0.8F;
         }
      }
   }
}
