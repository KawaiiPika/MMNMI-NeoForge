package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCreateCrewScreenPacket;

public class SakeBottleItem extends Item {
   public SakeBottleItem() {
      super((new Item.Properties()).m_41503_(5).m_41489_(ModFoods.ALCOHOL));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null && !world.f_46443_) {
         if (player.m_6047_() && props.isPirate()) {
            FactionsWorldData worldProps = FactionsWorldData.get();
            boolean isInCrew = worldProps.getCrewWithMember(player.m_20148_()) != null;
            if (isInCrew) {
               player.m_213846_(ModI18n.CREW_MESSAGE_ALREADY_IN_CREW);
               return InteractionResultHolder.m_19100_(itemstack);
            }

            if (props.getBounty() < (long)ServerConfig.getBountyRequirementForCrews()) {
               player.m_213846_(ModI18n.CREW_MESSAGE_BOUNTY_REQUIREMENT);
               return InteractionResultHolder.m_19100_(itemstack);
            }

            ModNetwork.sendTo(new SOpenCreateCrewScreenPacket(), player);
         } else {
            player.m_6672_(hand);
         }

         return InteractionResultHolder.m_19090_(itemstack);
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return false;
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player player) {
         player.m_36324_().eat(itemStack.m_41720_(), itemStack, entity);
         player.m_36246_(Stats.f_12982_.m_12902_(itemStack.m_41720_()));
         world.m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12321_, SoundSource.PLAYERS, 0.5F, world.f_46441_.m_188501_() * 0.1F + 0.9F);
         if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.f_10592_.m_23682_(serverPlayer, itemStack);
         }

         if (entity.m_21023_((MobEffect)ModEffects.DRUNK.get())) {
            MobEffectInstance effect = entity.m_21124_((MobEffect)ModEffects.DRUNK.get());
            int amp = effect.m_19564_();
            int duration = effect.m_19557_();
            if (amp < 4) {
               ++amp;
            }

            entity.m_21195_((MobEffect)ModEffects.DRUNK.get());
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DRUNK.get(), duration + 200, amp));
         } else {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DRUNK.get(), 400, 0));
         }

         itemStack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      }

      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }
}
