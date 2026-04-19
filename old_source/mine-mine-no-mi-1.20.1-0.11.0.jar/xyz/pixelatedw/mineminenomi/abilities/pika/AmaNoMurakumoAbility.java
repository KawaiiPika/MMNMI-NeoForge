package xyz.pixelatedw.mineminenomi.abilities.pika;

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
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class AmaNoMurakumoAbility extends ItemAbility {
   public static final RegistryObject<AbilityCore<AmaNoMurakumoAbility>> INSTANCE = ModRegistry.registerAbility("ama_no_murakumo", "Ama no Murakumo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Focuses light in the user's hand to create a sword", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AmaNoMurakumoAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.LIGHT).setSourceType(SourceType.SLASH).build("mineminenomi");
   });

   public AmaNoMurakumoAbility(AbilityCore<AmaNoMurakumoAbility> core) {
      super(core);
   }

   public ItemStack createItemStack(LivingEntity entity) {
      return new ItemStack((ItemLike)ModWeapons.AMA_NO_MURAKUMO.get());
   }
}
