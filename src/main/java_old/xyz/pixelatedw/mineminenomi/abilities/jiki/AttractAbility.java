package xyz.pixelatedw.mineminenomi.abilities.jiki;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class AttractAbility extends Ability {
   private static final int COOLDOWN = 60;
   private static final int HOLD_TIME = 100;
   private static final int RADIUS = 40;
   public static final RegistryObject<AbilityCore<AttractAbility>> INSTANCE = ModRegistry.registerAbility("attract", "Attract", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Attracts all nearby magnetic objects from the ground or enemy inventories.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AttractAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F), ContinuousComponent.getTooltip(100.0F), RangeComponent.getTooltip(40.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);

   public AttractAbility(AbilityCore<AttractAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      List<Entity> targets = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)40.0F, ModEntityPredicates.getEnemyFactions(entity), LivingEntity.class);
      List<Entity> items = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)40.0F, (Predicate)null, ItemEntity.class);
      targets.addAll(items);

      for(Entity target : targets) {
         if (target instanceof ItemEntity item) {
            if (!item.m_32055_().m_41619_() && item.m_32055_().m_204117_(ModTags.Items.MAGNETIC) && !EnchantmentHelper.m_44831_(item.m_32055_()).containsKey(ModEnchantments.KAIROSEKI.get()) && (!item.m_32055_().m_41782_() || !item.m_32055_().m_41783_().m_128441_("imbuingHakiActive"))) {
               Vec3 vec = item.m_20182_().m_82546_(entity.m_20182_().m_82520_((double)0.0F, (double)1.5F, (double)0.0F)).m_82542_(1.2, 1.2, 1.2);
               double speedReduction = (double)8.0F;
               double xSpeed = -vec.f_82479_ / speedReduction;
               double ySpeed = -vec.f_82480_ / speedReduction;
               double zSpeed = -vec.f_82481_ / speedReduction;
               AbilityHelper.setDeltaMovement(item, xSpeed, ySpeed, zSpeed);
               double dist = Math.sqrt(entity.m_20183_().m_123331_(item.m_20183_()));
               if (dist > (double)3.0F) {
                  int delay = (int)Math.max((double)5.0F, dist / (double)3.0F);
                  item.m_32010_(delay);
               }

               if (this.continuousComponent.getContinueTime() % 40.0F == 0.0F) {
                  JikiHelper.spawnAttractEffect(item);
               }
            }
         } else {
            Iterable<ItemStack> iter = target.m_20158_();
            if (target instanceof Player player) {
               Inventory playerInv = player.m_150109_();
               iter = Iterables.concat(playerInv.f_35974_, playerInv.f_35975_, playerInv.f_35976_);
            }

            for(ItemStack stack : iter) {
               if (!stack.m_41619_() && stack.m_204117_(ModTags.Items.MAGNETIC) && !EnchantmentHelper.m_44831_(stack).containsKey(ModEnchantments.KAIROSEKI.get()) && (!stack.m_41782_() || !stack.m_41783_().m_128441_("imbuingHakiActive"))) {
                  if (target instanceof Mob) {
                     Mob mob = (Mob)target;

                     for(EquipmentSlot slotType : EquipmentSlot.values()) {
                        if (mob.m_6844_(slotType).equals(stack)) {
                           mob.m_8061_(slotType, ItemStack.f_41583_);
                        }
                     }
                  }

                  if (!HakiHelper.hasImbuingActive((LivingEntity)target, false, true)) {
                     ItemEntity item = target.m_5552_(stack.m_41777_(), 1.0F);
                     if (item != null) {
                        item.m_32010_(30);
                     }

                     stack.m_41774_(stack.m_41613_());
                  }
               }
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 60.0F);
   }
}
