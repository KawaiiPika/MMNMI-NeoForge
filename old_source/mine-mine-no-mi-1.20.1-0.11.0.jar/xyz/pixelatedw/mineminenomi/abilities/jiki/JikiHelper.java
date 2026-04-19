package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JikiHelper {
   public static Result requiresPunkGibson(LivingEntity entity, IAbility ability) {
      return AbilityUseConditions.requiresMorph(entity, ability, (MorphInfo)ModMorphs.PUNK_GIBSON.get());
   }

   public static Ability.ICanUseEvent getMetalicItemsCheck(int amount) {
      return (entity, ability) -> {
         List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
         return !hasEnoughIron(inventory, (float)amount) ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS) : Result.success();
      };
   }

   public static void spawnAttractEffect(Entity source) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ATTRACT.get(), source, source.m_20185_(), source.m_20186_(), source.m_20189_());
   }

   public static List<ItemStack> getMagneticItemsOnPlayer(Player player) {
      List<ItemStack> inventory = new ArrayList();
      inventory.addAll(player.m_150109_().f_35974_);
      inventory.addAll(player.m_150109_().f_35976_);
      inventory.addAll(player.m_150109_().f_35975_);
      return inventory;
   }

   public static List<ItemStack> getMagneticItemsNeeded(List<ItemStack> inventory, float threshold) {
      List<ItemStack> stacksUsed = new ArrayList();

      for(ItemStack stack : inventory) {
         if (threshold <= 0.0F) {
            break;
         }

         if (stack != null && !stack.m_41619_() && stack.m_204117_(ModTags.Items.MAGNETIC) && !EnchantmentHelper.m_44831_(stack).containsKey(ModEnchantments.KAIROSEKI.get())) {
            float amount = ModTags.Items.IRON.getValue(stack.m_41720_());
            ItemStack clone = ItemStack.m_41712_(stack.m_41739_(new CompoundTag()));
            if (stack.m_41763_()) {
               stacksUsed.add(clone);
               stack.m_41774_(1);
               threshold -= amount;
            } else {
               int qty = stack.m_41613_();
               int neededQty = (int)Math.ceil((double)(threshold / amount));
               if (qty < neededQty) {
                  stacksUsed.add(clone);
                  stack.m_41774_(qty);
                  threshold -= (float)qty * amount;
               } else {
                  ItemStack split = stack.m_41620_(neededQty);
                  stacksUsed.add(split);
                  threshold -= (float)split.m_41613_() * amount;
               }
            }
         }
      }

      return stacksUsed;
   }

   public static boolean hasEnoughIron(List<ItemStack> list, float threshold) {
      return getIronAmount(list) >= threshold;
   }

   public static float getIronAmount(List<ItemStack> list) {
      float totalAmount = 0.0F;

      for(ItemStack stack : list) {
         if (stack != null && !stack.m_41619_() && !EnchantmentHelper.m_44831_(stack).containsKey(ModEnchantments.KAIROSEKI.get()) && stack.m_204117_(ModTags.Items.MAGNETIC)) {
            float value = ModTags.Items.IRON.getValue(stack.m_41720_());
            totalAmount += value * (float)stack.m_41613_();
         }
      }

      return totalAmount;
   }

   public static void dropComponentItems(Level level, Vec3 pos, List<ItemStack> list) {
      damageMagneticItems(level, list);

      for(ItemStack stack : list) {
         int splits = level.m_213780_().m_188503_(4) + 2;

         for(int i = 0; i < splits; ++i) {
            ItemStack split = stack.m_41620_(stack.m_41613_() / splits);
            ItemEntity item = new ItemEntity(level, pos.m_7096_(), pos.m_7098_() + (double)0.4F, pos.m_7094_(), split);
            item.m_32010_(20);
            level.m_7967_(item);
         }

         ItemEntity item = new ItemEntity(level, pos.m_7096_(), pos.m_7098_() + (double)0.4F, pos.m_7094_(), stack);
         item.m_32010_(20);
         level.m_7967_(item);
      }

   }

   public static void damageMagneticItems(Level level, List<ItemStack> list) {
      for(ItemStack stack : list) {
         if (stack != null && !stack.m_41619_()) {
            float value = ModTags.Items.IRON.getValue(stack.m_41720_());
            float chance = Mth.m_14036_(10.0F / value, 0.0F, 50.0F);
            boolean canBreak = (float)level.m_213780_().m_188503_(100) + level.m_213780_().m_188501_() < chance;
            if (canBreak) {
               if (stack.m_41763_()) {
                  int maxDamage = stack.m_41776_();
                  int damage = (int)WyHelper.clamp((long)level.m_213780_().m_188503_(maxDamage), 50L, (long)maxDamage);
                  stack.m_220157_(damage, level.f_46441_, (ServerPlayer)null);
               } else if (value > 0.0F) {
                  int maxCount = 1 + (int)((float)stack.m_41613_() / value);
                  int destroy = (int)WyHelper.clamp((long)level.m_213780_().m_188503_(maxCount), 1L, (long)maxCount);
                  stack.m_41774_(destroy);
               }
            }
         }
      }

   }
}
