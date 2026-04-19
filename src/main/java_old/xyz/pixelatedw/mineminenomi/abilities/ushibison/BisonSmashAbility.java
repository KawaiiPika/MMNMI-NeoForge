package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.RunningSmashAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BisonSmashAbility extends RunningSmashAbility {
   public static final RegistryObject<AbilityCore<BisonSmashAbility>> INSTANCE = ModRegistry.registerAbility("bison_smash", "Bison Smash", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Running into enemies deals damage and knocks them back.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, BisonSmashAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.BISON_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, DealDamageComponent.getTooltip(2.0F), RangeComponent.getTooltip(1.5F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public BisonSmashAbility(AbilityCore<BisonSmashAbility> core) {
      super(core);
      this.addCanUseCheck(UshiBisonHelper::requiresWalkPoint);
   }

   public boolean canSmash(LivingEntity entity) {
      return true;
   }
}
