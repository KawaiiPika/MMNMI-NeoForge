package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.ItemAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class TabiraYukiAbility extends ItemAbility {
   public static final RegistryObject<AbilityCore<TabiraYukiAbility>> INSTANCE = ModRegistry.registerAbility("tabira_yuki", "Tabira Yuki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a sword made of solid hardened snow. Will inflict %s on hit.", new Object[]{ModEffects.FROSTBITE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TabiraYukiAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.ICE).build("mineminenomi");
   });

   public TabiraYukiAbility(AbilityCore<TabiraYukiAbility> core) {
      super(core);
   }

   public ItemStack createItemStack(LivingEntity entity) {
      return new ItemStack((ItemLike)ModWeapons.TABIRA_YUKI.get());
   }
}
