package xyz.pixelatedw.mineminenomi.abilities.wara;

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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class WarabideSwordAbility extends ItemAbility {
   public static final RegistryObject<AbilityCore<WarabideSwordAbility>> INSTANCE = ModRegistry.registerAbility("warabide_sword", "Warabide Sword", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a sword that can shoot long thin straw-like projectiles.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WarabideSwordAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });

   public WarabideSwordAbility(AbilityCore<WarabideSwordAbility> core) {
      super(core);
   }

   public ItemStack createItemStack(LivingEntity entity) {
      return new ItemStack((ItemLike)ModWeapons.WARABIDE_SWORD.get());
   }
}
