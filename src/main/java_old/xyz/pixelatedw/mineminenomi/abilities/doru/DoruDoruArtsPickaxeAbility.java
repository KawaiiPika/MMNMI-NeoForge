package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ItemAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DoruDoruArtsPickaxeAbility extends ItemAbility {
   private static final float COOLDOWN = 10.0F;
   public static final RegistryObject<AbilityCore<DoruDoruArtsPickaxeAbility>> INSTANCE = ModRegistry.registerAbility("doru_doru_arts_pickaxe", "Doru Doru Arts: Pickaxe", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user uses hardened wax to create a pickaxe", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoruDoruArtsPickaxeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WAX).build("mineminenomi");
   });

   public DoruDoruArtsPickaxeAbility(AbilityCore<DoruDoruArtsPickaxeAbility> core) {
      super(core);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 10.0F);
   }

   public ItemStack createItemStack(LivingEntity entity) {
      ItemStack itemStack = new ItemStack((ItemLike)ModWeapons.DORU_PICKAXE.get());
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      if (ItemsHelper.countItemInInventory(entity, (Item)ModItems.COLOR_PALETTE.get()) > 0) {
         red = this.random.nextFloat();
         green = this.random.nextFloat();
         blue = this.random.nextFloat();
      }

      IMultiChannelColorItem.dyeArmor(itemStack, 0, (new Color(red, green, blue)).getRGB());
      return itemStack;
   }
}
