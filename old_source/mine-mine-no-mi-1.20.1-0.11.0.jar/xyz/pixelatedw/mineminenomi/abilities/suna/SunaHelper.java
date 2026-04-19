package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluids;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SunaHelper {
   public static final UUID DESERT_COOLDOWN_BONUS = UUID.fromString("6d311e6b-84b8-4c9d-b67b-f93116bbc397");
   public static final UUID DESERT_DAMAGE_BONUS = UUID.fromString("9f5c3cf8-d241-4895-a33d-a2f1be34e163");
   public static final UUID DESERT_CHARGE_BONUS = UUID.fromString("415a4bf3-1c81-4b93-a705-5e5661dd0bae");
   public static final UUID DESERT_RANGE_BONUS = UUID.fromString("e5e79903-2ee4-49e5-b462-dfa51a68af0a");

   public static Result requiresInactiveDesertGirasole(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         DesertGirasoleAbility desertGirasole = (DesertGirasoleAbility)props.getEquippedAbility((AbilityCore)DesertGirasoleAbility.INSTANCE.get());
         return desertGirasole != null && desertGirasole.isCharging() ? Result.fail(Component.m_237110_(ModI18nAbilities.MESSAGE_CANNOT_USE_TOGETHER, new Object[]{ability.getDisplayName().getString(), desertGirasole.getDisplayName().getString()})) : Result.success();
      }
   }

   public static Result requiresInactiveGroundDeath(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         GroundDeathAbility groundDeath = (GroundDeathAbility)props.getEquippedAbility((AbilityCore)GroundDeathAbility.INSTANCE.get());
         return groundDeath != null && groundDeath.isCharging() ? Result.fail(Component.m_237110_(ModI18nAbilities.MESSAGE_CANNOT_USE_TOGETHER, new Object[]{ability.getDisplayName().getString(), groundDeath.getDisplayName().getString()})) : Result.success();
      }
   }

   public static boolean isWet(LivingEntity entity) {
      BlockPos blockpos = entity.m_20183_();
      boolean isRaining = entity.m_9236_().m_46758_(blockpos) || entity.m_9236_().m_46758_(new BlockPos(blockpos.m_123341_(), (int)entity.m_20191_().f_82292_, blockpos.m_123343_()));
      boolean checkMainHand = !entity.m_21205_().m_41619_() && entity.m_21205_().m_41720_() == ModWeapons.UMBRELLA.get();
      boolean checkOffHand = !entity.m_21206_().m_41619_() && entity.m_21206_().m_41720_() == ModWeapons.UMBRELLA.get();
      boolean holdsUmbrella = checkMainHand || checkOffHand;
      if (isRaining && holdsUmbrella) {
         return false;
      } else {
         return entity.m_20070_();
      }
   }

   public static boolean isFruitBoosted(LivingEntity entity) {
      Holder<Biome> biome = entity.m_20193_().m_204166_(entity.m_20183_());
      return biome.m_203656_(BiomeTags.f_207614_) || biome.m_203656_(BiomeTags.f_207604_);
   }

   public static void drainLiquids(LivingEntity entity, int effects, int potions, int buckets) {
      if (entity != null && entity.m_6084_()) {
         int cleanBottles = 0;
         int cleanBuckets = 0;
         int cleanEffects = 0;

         for(MobEffectInstance eff : entity.m_21220_()) {
            if (eff != null && effects > cleanEffects && eff.m_19544_().m_19486_() && 1 >= eff.m_19564_() && !(eff.m_19544_() instanceof IColorOverlayEffect)) {
               ++cleanEffects;
               MobEffect potion = eff.m_19544_();
               entity.m_21195_(potion);
               if (entity instanceof ServerPlayer) {
                  ServerPlayer serverPlayer = (ServerPlayer)entity;
                  serverPlayer.f_8906_.m_9829_(new ClientboundRemoveMobEffectPacket(entity.m_19879_(), potion));
               }
            }
         }

         if (entity instanceof Player) {
            Player player = (Player)entity;
            ArrayList<Item> items = new ArrayList(Arrays.asList(Items.f_42589_, Items.f_42739_, Items.f_42736_));

            for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
               if (items.contains(player.m_150109_().m_8020_(i).m_41720_()) && potions > cleanBottles) {
                  player.m_150109_().m_6836_(i, new ItemStack(Items.f_41852_));
                  ++cleanBottles;
               } else if (player.m_150109_().m_8020_(i).m_41720_() instanceof BucketItem && buckets > cleanBuckets) {
                  BucketItem item = (BucketItem)player.m_150109_().m_8020_(i).m_41720_();
                  if (item.getFluid() != Fluids.f_76191_ && item.getFluid() != Fluids.f_76195_) {
                     player.m_150109_().m_6836_(i, new ItemStack(Items.f_41852_));
                     ++cleanBuckets;
                  }
               }
            }

            if (cleanBottles > 0) {
               player.m_36356_(new ItemStack(Items.f_42590_, cleanBottles));
            }

            if (cleanBuckets > 0) {
               player.m_36356_(new ItemStack(Items.f_42446_, cleanBuckets));
            }
         }

      }
   }
}
