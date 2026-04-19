package xyz.pixelatedw.mineminenomi.abilities.gasu;

import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class BlueSwordAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<BlueSwordAbility>> INSTANCE = ModRegistry.registerAbility("blue_sword", "Blue Sword", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fills a %s hilt with flamable gas, then sets it on fire to create a sword", new Object[]{ModWeapons.BLUE_SWORD}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, BlueSwordAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.FIRE).setSourceType(SourceType.SLASH).build("mineminenomi");
   });

   public BlueSwordAbility(AbilityCore<BlueSwordAbility> core) {
      super(core);
   }
}
