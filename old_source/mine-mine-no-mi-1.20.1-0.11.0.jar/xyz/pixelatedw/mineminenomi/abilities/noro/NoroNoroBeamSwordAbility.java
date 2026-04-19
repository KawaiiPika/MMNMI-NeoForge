package xyz.pixelatedw.mineminenomi.abilities.noro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.ItemAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class NoroNoroBeamSwordAbility extends ItemAbility {
   public static final RegistryObject<AbilityCore<NoroNoroBeamSwordAbility>> INSTANCE = ModRegistry.registerAbility("noro_noro_beam_sword", "Noro Noro Beam Sword", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Focuses photons inside a hilt to create a sword, which slows enemies upon hit", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NoroNoroBeamSwordAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public NoroNoroBeamSwordAbility(AbilityCore<NoroNoroBeamSwordAbility> core) {
      super(core);
   }

   public ItemStack createItemStack(LivingEntity entity) {
      return new ItemStack((ItemLike)ModWeapons.NORO_NORO_BEAM_SWORD.get());
   }
}
