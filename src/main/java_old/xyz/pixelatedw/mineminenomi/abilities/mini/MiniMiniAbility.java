package xyz.pixelatedw.mineminenomi.abilities.mini;

import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MiniMiniAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<MiniMiniAbility>> INSTANCE = ModRegistry.registerAbility("mini_mini", "Mini Mini", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to become smaller than their actual size.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MiniMiniAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public MiniMiniAbility(AbilityCore<MiniMiniAbility> core) {
      super(core);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.MINI.get();
   }
}
