package xyz.pixelatedw.mineminenomi.abilities;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.NetItem;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;

public class WootzNetLauncherAbility extends Ability {
   private static final int COOLDOWN = 600;
   public static final RegistryObject<AbilityCore<WootzNetLauncherAbility>> INSTANCE = ModRegistry.registerAbility("wootz_net_launcher", "Wootz Net Launcher", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots a net from one of the many guns hidden within the armor.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, WootzNetLauncherAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F)).setUnlockCheck(WootzNetLauncherAbility::canUnlock).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private ItemStack netStack;

   public WootzNetLauncherAbility(AbilityCore<WootzNetLauncherAbility> core) {
      super(core);
      this.netStack = ItemStack.f_41583_;
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.addCanUseCheck(this::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.netStack.m_41720_() instanceof NetItem) {
         NetItem netItem = (NetItem)this.netStack.m_41720_();
         this.animationComponent.start(entity, ModAnimations.MH5_CHARGING, 7);
         Vec3 look = entity.m_20154_();
         NetEntity netEntity = NetEntity.createFromItem(entity.m_9236_(), entity, netItem);
         netEntity.m_6034_(entity.m_20185_() + look.f_82479_, entity.m_20188_() + look.f_82480_, entity.m_20189_() + look.f_82481_);
         netEntity.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 1.25F, 0.0F);
         entity.m_9236_().m_7967_(netEntity);
         this.netStack.m_41774_(1);
         this.cooldownComponent.startCooldown(entity, 600.0F);
      }
   }

   private void findNewStack(LivingEntity entity) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);

      for(int i = 0; i < inventory.size(); ++i) {
         ItemStack stack = (ItemStack)inventory.get(i);
         if (stack != null && !stack.m_41619_() && stack.m_41720_() instanceof NetItem) {
            this.netStack = stack;
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
         return this.netStack.m_41619_() ? Result.fail((Component)null) : Result.success();
      }
   }

   private static boolean canUnlock(LivingEntity entity) {
      return entity instanceof Player ? false : WootzArmorItem.hasArmorEquipped(entity);
   }
}
