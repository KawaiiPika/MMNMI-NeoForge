package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.WateringCanItem;

public class TearsAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<TearsAbility>> INSTANCE = ModRegistry.registerAbility("tears", "Tears", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Each time the user is hurt their Watering Can has a 10% chance of being filled with a tear, which can be drank to heal themselves.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, TearsAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(this::onDamageTaken);

   public TearsAbility(AbilityCore<TearsAbility> ability) {
      super(ability);
      super.addComponents(this.damageTakenComponent);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (super.canUse(entity).isFail()) {
         return damage;
      } else if (!(entity instanceof Player)) {
         return damage;
      } else {
         Player player = (Player)entity;
         ItemStack wateringCan = null;
         List<ItemStack> inventory = new ArrayList();
         inventory.addAll(player.m_150109_().f_35974_);
         inventory.addAll(player.m_150109_().f_35976_);

         for(ItemStack element : inventory) {
            if (element != null && element.m_41720_() == ModItems.WATERING_CAN.get()) {
               wateringCan = element;
               break;
            }
         }

         if (wateringCan == null) {
            return damage;
         } else {
            if (super.random.nextInt(10) == 0) {
               WateringCanItem.alterTearAmount(wateringCan, 1);
            }

            return damage;
         }
      }
   }
}
