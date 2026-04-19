package xyz.pixelatedw.mineminenomi.abilities.kame;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.RideableAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KameRideableAbility extends RideableAbility {
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/kame_guard_point.png");
   public static final RegistryObject<AbilityCore<KameRideableAbility>> INSTANCE = ModRegistry.registerAbility("kame_rideable", "Kame Rideable", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows other players to ride on your back.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, KameRideableAbility::new)).setIcon(ICON).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.KAME_GUARD)).build("mineminenomi");
   });

   public KameRideableAbility(AbilityCore<KameRideableAbility> core) {
      super(core);
      this.addCanUseCheck((entity, ability) -> AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.KAME_GUARD.get()));
   }
}
