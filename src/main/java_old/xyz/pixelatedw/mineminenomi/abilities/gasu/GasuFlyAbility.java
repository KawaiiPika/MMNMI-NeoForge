package xyz.pixelatedw.mineminenomi.abilities.gasu;

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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GasuFlyAbility extends FlyAbility {
   public static final RegistryObject<AbilityCore<GasuFlyAbility>> INSTANCE = ModRegistry.registerAbility("gasu_special_fly", "Gasu Special Fly", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to fly", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GasuFlyAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public GasuFlyAbility(AbilityCore<GasuFlyAbility> core) {
      super(core);
   }

   public Supplier<ParticleEffect<?>> getParticleEffects() {
      return ModParticleEffects.GASU_FLY;
   }

   public int getHeightDifference(Player player) {
      return 40;
   }

   public float getSpeed(Player player) {
      float speed = 0.55F;
      if (!WyHelper.isInCombat(player)) {
         speed += speed * 0.3F;
      }

      return speed;
   }
}
