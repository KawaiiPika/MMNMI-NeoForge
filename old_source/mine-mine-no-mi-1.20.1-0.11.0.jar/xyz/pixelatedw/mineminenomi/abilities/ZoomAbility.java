package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ZoomAbility extends Ability {
   public static final RegistryObject<AbilityCore<ZoomAbility>> INSTANCE = ModRegistry.registerAbility("zoom", "Zoom", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Zooms into the direction the user is looking, massively reduces inaccuracy when shooting projectiles.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ZoomAbility::new)).addDescriptionLine(desc).setUnlockCheck(ZoomAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this, true);

   public ZoomAbility(AbilityCore<ZoomAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(this::canStartAbility);
      this.addUseEvent(this::onUseEvent);
      this.addTickEvent(this::onTickEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void onTickEvent(LivingEntity entity, IAbility ability) {
      if (this.isContinuous()) {
         ItemStack headStack = entity.m_6844_(EquipmentSlot.HEAD);
         if (headStack.m_41619_() || headStack.m_41720_() != ModArmors.SNIPER_GOGGLES.get()) {
            this.continuousComponent.stopContinuity(entity);
         }
      }

   }

   private Result canStartAbility(LivingEntity entity, IAbility ability) {
      return !ItemsHelper.hasItemInSlot(entity, EquipmentSlot.HEAD, (Item)ModArmors.SNIPER_GOGGLES.get()) ? Result.fail(ModI18nAbilities.MESSAGE_NEED_SNIPER_GOGGLES) : Result.success();
   }

   private static boolean canUnlock(LivingEntity entity) {
      ItemStack headStack = entity.m_6844_(EquipmentSlot.HEAD);
      return !headStack.m_41619_() && headStack.m_41720_() == ModArmors.SNIPER_GOGGLES.get();
   }
}
