package xyz.pixelatedw.mineminenomi.abilities;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public class GunArrayAbility extends Ability {
   private static final int COOLDOWN = 40;
   public static final RegistryObject<AbilityCore<GunArrayAbility>> INSTANCE = ModRegistry.registerAbility("gun_array", "Gun Array", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While active it automatically fires bullets from the inventory.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, GunArrayAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setUnlockCheck(GunArrayAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::stopRepeaterEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private ItemStack bulletStack;

   public GunArrayAbility(AbilityCore<GunArrayAbility> core) {
      super(core);
      this.bulletStack = ItemStack.f_41583_;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent});
      this.addCanUseCheck(this::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 20, 20);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.bulletStack.m_41619_()) {
         this.findNewStack(entity);
         if (this.bulletStack.m_41619_()) {
            this.continuousComponent.stopContinuity(entity);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 40.0F);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      float innacuracy = 3.0F;
      if (entity.m_20142_()) {
         innacuracy *= 2.0F;
      }

      if (entity.m_6047_()) {
         innacuracy = 0.0F;
      }

      this.projectileComponent.shoot(entity, 3.0F, innacuracy);
      this.bulletStack.m_41774_(1);
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private NuProjectileEntity createProjectile(LivingEntity entity) {
      NuProjectileEntity proj = null;
      if (this.bulletStack.m_41720_() == ModItems.KAIROSEKI_BULLET.get()) {
         proj = new KairosekiBulletProjectile(entity.m_9236_(), entity);
      } else {
         proj = new NormalBulletProjectile(entity.m_9236_(), entity);
      }

      return proj;
   }

   private void findNewStack(LivingEntity entity) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);

      for(int i = 0; i < inventory.size(); ++i) {
         ItemStack stack = (ItemStack)inventory.get(i);
         if (stack != null && !stack.m_41619_() && ModGunItem.GUN_AMMO.test(stack) && stack.m_41613_() >= 4) {
            this.bulletStack = stack;
            break;
         }
      }

   }

   private Result canUse(LivingEntity entity, IAbility ability) {
      Result hasArmor = AbilityUseConditions.requiresWootzArmor(entity, ability);
      if (hasArmor.isFail()) {
         return hasArmor;
      } else {
         this.findNewStack(entity);
         return this.bulletStack.m_41619_() ? Result.fail((Component)null) : Result.success();
      }
   }

   private static boolean canUnlock(LivingEntity entity) {
      return WootzArmorItem.hasArmorEquipped(entity);
   }
}
