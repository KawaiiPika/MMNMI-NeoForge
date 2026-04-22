package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import xyz.pixelatedw.mineminenomi.abilities.EmptyHandsAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.blocks.TrapAbilityBlock;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ItemsHandler {
   private static final Component KAIROSEKI_ITEM_STRING;

   public static void removeImbuingTag(@Nullable ItemStack stack) {
      if (stack != null && !stack.m_41619_() && stack.m_41782_() && stack.m_41783_().m_128471_("imbuingHakiActive")) {
         stack.m_41784_().m_128473_("imbuingHakiActive");
      }

   }

   public static void handleImbuingTagging(LivingEntity living) {
      ItemStack heldItem = living.m_21205_();
      if (ItemsHelper.isImbuable(heldItem)) {
         if (!heldItem.m_41619_()) {
            if (HakiHelper.hasImbuingActive(living) && !heldItem.m_41784_().m_128471_("imbuingHakiActive")) {
               heldItem.m_41784_().m_128379_("imbuingHakiActive", true);
            } else if (heldItem.m_41784_().m_128441_("imbuingHakiActive") && !HakiHelper.hasImbuingActive(living) && heldItem.m_41784_().m_128471_("imbuingHakiActive")) {
               heldItem.m_41784_().m_128473_("imbuingHakiActive");
            }
         }

      }
   }

   public static void handleEnmaCurse(Player player) {
      ItemStack stack = player.m_6844_(EquipmentSlot.MAINHAND);
      if (stack.m_41720_() == ModWeapons.ENMA.get() && stack.m_41782_()) {
         if (!stack.m_41783_().m_128471_("isClone")) {
            IHakiData hakiProps = (IHakiData)HakiCapability.get(player).orElse((Object)null);
            IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (hakiProps != null && abilityProps != null) {
               float imbuingExp = hakiProps.getBusoshokuHakiExp();
               if (imbuingExp < 20.0F) {
                  player.m_6469_(player.m_269291_().m_269251_(), player.m_21233_());
               } else if (imbuingExp >= 20.0F && imbuingExp < 30.0F) {
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 200, 1, false, false));
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 200, 1, false, false));
                  if (!player.m_21023_(MobEffects.f_19615_)) {
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 1, false, false));
                  }
               } else if (imbuingExp >= 30.0F && !HakiHelper.hasImbuingActive(player)) {
                  hakiProps.alterHakiOveruse(2);
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 100, 1, false, false));
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 1, false, false));
                  if (!player.m_21023_(MobEffects.f_19615_)) {
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 2, false, false));
                  }
               }

            }
         }
      }
   }

   public static boolean handleEmptyHandsPickup(Player player, ItemEntity itemEntity) {
      ItemStack heldItem = player.m_21205_();
      if (!heldItem.m_41619_()) {
         return false;
      } else {
         boolean isEmptyHandsActive = (Boolean)AbilityCapability.get(player).map((props) -> (EmptyHandsAbility)props.getPassiveAbility((AbilityCore)EmptyHandsAbility.INSTANCE.get())).map((abl) -> !abl.isPaused()).orElse(false);
         if (!isEmptyHandsActive) {
            return false;
         } else {
            ItemStack itemStack = itemEntity.m_32055_();
            Item item = itemStack.m_41720_();
            int count = itemStack.m_41613_();
            int emptySlot = ItemsHelper.getFreeOrSameSlot(player, itemStack);
            if (emptySlot < 0) {
               return true;
            } else {
               ItemStack copy = itemStack.m_41777_();
               if (!itemEntity.m_32063_() && (itemEntity.m_19749_() == null || itemEntity.lifespan - itemEntity.m_32059_() <= 200 || itemEntity.m_19749_().m_20148_().equals(player.m_20148_())) && (count <= 0 || player.m_150109_().m_36040_(emptySlot, itemStack))) {
                  copy.m_41764_(copy.m_41613_() - itemEntity.m_32055_().m_41613_());
                  ForgeEventFactory.firePlayerItemPickupEvent(player, itemEntity, copy);
                  player.m_7938_(itemEntity, count);
                  if (itemStack.m_41619_()) {
                     itemEntity.m_146870_();
                     itemStack.m_41764_(count);
                  }

                  player.m_6278_(Stats.f_12984_.m_12902_(item), count);
                  player.m_21053_(itemEntity);
                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public static void handleRandomizedFruitsTooltip(ItemStack itemStack, List<Component> list) {
      if (itemStack.m_41720_() instanceof AkumaNoMiItem) {
         list.add(0, ModI18n.ITEM_GENERIC_FRUIT);

         for(int i = 0; i < list.size(); ++i) {
            Component comp = (Component)list.get(i);
            if (comp.toString().contains("item.color")) {
               list.remove(i);
               break;
            }
         }
      }

   }

   public static void insideTrapBlock(LivingEntity entity) {
      BlockPos pos = entity.m_20183_().m_6630_((int)entity.m_20192_());
      BlockState blockState = entity.m_9236_().m_8055_(pos);
      if (!blockState.m_60795_()) {
         Block var4 = blockState.m_60734_();
         if (var4 instanceof TrapAbilityBlock) {
            TrapAbilityBlock trapBlock = (TrapAbilityBlock)var4;
            if (trapBlock.canWalkOn(entity)) {
               entity.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)1.0F, entity.m_20189_());
               entity.f_19789_ = 0.0F;
               entity.m_6853_(true);
            } else {
               if (entity instanceof Player) {
                  Player player = (Player)entity;
                  if (!player.m_7500_()) {
                     player.m_150110_().f_35935_ = false;
                  }
               }

               if (trapBlock.getPotionEffect() != null) {
                  entity.m_7292_(trapBlock.getPotionEffect());
               }

               DamageSource source = trapBlock.getDamageSource(entity.m_9236_());
               if (source == null) {
                  source = entity.m_269291_().m_269318_();
               }

               entity.m_6469_(source, (float)trapBlock.getDamageDealt());
            }
         }

      }
   }

   public static void cuckShield(LivingEntity entity, LivingEntity attacker) {
      if (HakiHelper.hasAdvancedBusoActive(attacker)) {
         Item mainShield = entity.m_21205_().m_41720_();
         Item secondaryShield = entity.m_21206_().m_41720_();
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (xyz.pixelatedw.mineminenomi.api.WyHelper.random() > (double)0.5F && (mainShield.equals(Items.f_42740_) || secondaryShield.equals(Items.f_42740_))) {
               player.m_36335_().m_41524_(Items.f_42740_, 100);
               entity.m_5810_();
               entity.m_9236_().m_7605_(attacker, (byte)30);
            }
         }
      }

   }

   public static void dyeableTooltip(ItemStack itemStack, List<Component> tooltip) {
      Item var3 = itemStack.m_41720_();
      if (var3 instanceof IMultiChannelColorItem colorItem) {
         if (colorItem.getMaxLayers() > 0) {
            Component title = (Component)tooltip.get(0);
            Component sub = Component.m_237113_(" \ud83c\udfa8" + colorItem.getMaxLayers());
            if (!title.m_240452_(sub)) {
               Component var6 = title.m_6881_().m_7220_(sub);
               tooltip.set(0, var6);
            }
         }
      }

   }

   public static void kairosekiTooltip(ItemStack itemStack, List<Component> tooltip) {
      if (itemStack.m_204117_(ModTags.Items.KAIROSEKI)) {
         if (!tooltip.contains(KAIROSEKI_ITEM_STRING)) {
            tooltip.add(1, KAIROSEKI_ITEM_STRING);
         }

      }
   }

   public static void weatherBallChargesTooltip(ItemStack itemStack, List<Component> tooltip) {
      if (ItemsHelper.isClimaTact(itemStack)) {
         tooltip.add(1, Component.m_237113_(ArtOfWeatherHelper.getChargesString(itemStack)));
      }
   }

   static {
      KAIROSEKI_ITEM_STRING = ModI18n.ITEM_KAIROSEKI_ITEM.m_6881_().m_6270_(Style.f_131099_.m_131157_(ChatFormatting.YELLOW));
   }
}
