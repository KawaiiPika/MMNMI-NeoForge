package xyz.pixelatedw.mineminenomi.handlers.ability;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.events.entity.YomiTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsRestoreHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class YomiPassiveHandler {
   public static void tick(LivingEntity entity) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (props != null && props.hasYomiPower()) {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.m_36324_().m_38702_() <= 18) {
               player.m_36324_().m_38705_(18);
            }
         }

         entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 10, entity.m_20142_() ? 4 : 0, false, false));
      }
   }

   public static void handleWaterRunning(LivingEntity entity) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (props != null && props.hasYomiPower()) {
         if (!(entity.f_20902_ <= 0.0F)) {
            BlockState state = entity.m_9236_().m_8055_(entity.m_20183_().m_7495_());
            if (state.m_60819_().m_76170_() && state.m_60819_().getFluidType().equals(ForgeMod.WATER_TYPE.get()) && entity.m_20142_()) {
               Vec3 moveVec = entity.m_20154_().m_82541_().m_82542_((double)0.5F, (double)0.0F, (double)0.5F);
               AbilityHelper.setDeltaMovement(entity, moveVec);
               entity.f_19789_ = 0.0F;
               if (!entity.m_9236_().f_46443_) {
                  for(int i = 0; i < 32; ++i) {
                     double newPosX = entity.m_20185_() + WyHelper.randomDouble() * (double)0.5F;
                     double newPosY = entity.m_20186_() + WyHelper.randomDouble() * (double)0.5F;
                     double newPosZ = entity.m_20189_() + WyHelper.randomDouble() * (double)0.5F;
                     ((ServerLevel)entity.m_9236_()).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, state), newPosX, newPosY, newPosZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
                  }
               }
            }

         }
      }
   }

   public static boolean triggerFirstDeath(Player original, Player player) {
      IDevilFruit oldProps = (IDevilFruit)DevilFruitCapability.get(original).orElse((Object)null);
      if (oldProps == null) {
         return false;
      } else {
         boolean canTriggerFirstDeath = oldProps.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && !oldProps.hasYomiPower();
         if (!canTriggerFirstDeath) {
            return false;
         } else {
            YomiTriggerEvent yomiEvent = new YomiTriggerEvent(original, player);
            MinecraftForge.EVENT_BUS.post(yomiEvent);
            if (original instanceof ServerPlayer) {
               ServerPlayer serverPlayer = (ServerPlayer)original;
               ModAdvancements.YOMI_REVIVE.trigger(serverPlayer);
            }

            StatsRestoreHandler.restoreFullData(original, player);
            IDevilFruit newProps = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
            newProps.setYomiPower(true);
            newProps.setDevilFruit((AkumaNoMiItem)ModFruits.YOMI_YOMI_NO_MI.get());
            newProps.addMorph((MorphInfo)ModMorphs.YOMI_SKELETON.get());
            return true;
         }
      }
   }

   public static void milkHeal(LivingEntity entity, ItemStack stack) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (props != null) {
         if (stack.m_41720_() == Items.f_42455_ && props.hasYomiPower()) {
            entity.m_5634_(8.0F);
         }

      }
   }
}
