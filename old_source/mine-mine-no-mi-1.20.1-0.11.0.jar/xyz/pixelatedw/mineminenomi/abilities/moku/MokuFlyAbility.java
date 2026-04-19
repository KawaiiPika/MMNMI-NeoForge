package xyz.pixelatedw.mineminenomi.abilities.moku;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MokuFlyAbility extends FlyAbility {
   public static final RegistryObject<AbilityCore<MokuFlyAbility>> INSTANCE = ModRegistry.registerAbility("moku_special_fly", "Moku Special Fly", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to fly", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, MokuFlyAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public MokuFlyAbility(AbilityCore<MokuFlyAbility> core) {
      super(core);
   }

   public Supplier<ParticleEffect<?>> getParticleEffects() {
      return ModParticleEffects.MOKU_FLY;
   }

   public int getHeightDifference(Player player) {
      return 30;
   }

   public float getSpeed(Player player) {
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (abilityDataProps == null) {
         return 0.0F;
      } else {
         WhiteLauncherAbility whiteLauncher = (WhiteLauncherAbility)abilityDataProps.getEquippedAbility((AbilityCore)WhiteLauncherAbility.INSTANCE.get());
         boolean whiteLauncherInUse = whiteLauncher != null && (Boolean)whiteLauncher.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous() && comp.getContinueTime() <= 15.0F).orElse(false);
         if (whiteLauncherInUse) {
            return 0.8F;
         } else {
            float speed = 0.3F;
            if (!WyHelper.isInCombat(player)) {
               speed += speed * 0.3F;
            }

            return speed;
         }
      }
   }
}
