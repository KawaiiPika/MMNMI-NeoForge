package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class GoldDenDenMushiItem extends Item {
   private static final int CANNON_SHOTS_TIMER = 200;
   private static final int BUSTER_CALL_COUNTDOWN = 1200;

   public GoldDenDenMushiItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (!player.m_21120_(hand).m_41784_().m_128471_("inUse") && !world.f_46443_) {
         if (NuWorld.isChallengeDimension(world)) {
            return InteractionResultHolder.m_19100_(itemStack);
         }

         CompoundTag compoundNBT = player.m_21120_(hand).m_41784_();
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props == null) {
            return InteractionResultHolder.m_19100_(itemStack);
         }

         boolean hasPermission = ModPermissions.hasPermission(player, ModPermissions.BUSTER_CALL_ITEM);
         if (!props.hasRank(MarineRank.ADMIRAL) && !hasPermission) {
            WyHelper.sendMessage(player, ModI18n.ITEM_BUSTER_CALL_REQUIREMENT);
            return InteractionResultHolder.m_19100_(itemStack);
         }

         compoundNBT.m_128405_("countdown", 1200);
         compoundNBT.m_128379_("inUse", true);
         compoundNBT.m_128385_("coords", new int[]{player.m_20183_().m_123341_(), player.m_20183_().m_123342_(), player.m_20183_().m_123343_()});
         world.m_6907_().stream().filter((target) -> (Boolean)EntityStatsCapability.get(target).map((tprops) -> tprops.isMarine()).orElse(false)).forEach((target) -> {
            Component message = Component.m_237110_(ModI18n.ITEM_BUSTER_CALL_LAUNCHED, new Object[]{Math.round(player.m_20185_()), Math.round(player.m_20189_())});
            WyHelper.sendMessage(target, message);
         });
      }

      return InteractionResultHolder.m_19090_(itemStack);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      super.m_6883_(stack, world, entity, itemSlot, isSelected);
      if (stack.m_41782_() && stack.m_41783_().m_128471_("inUse") && !world.f_46443_) {
         CompoundTag nbt = stack.m_41783_();
         nbt.m_128405_("countdown", this.getCountdown(stack) - 1);
         int countdown = this.getCountdown(stack);
         int t = Math.max((countdown - 200) / 20, 0);
         if (entity instanceof Player) {
            Player player = (Player)entity;
            Component message = Component.m_237110_(ModI18n.ITEM_BUSTER_CALL_TIMER, new Object[]{t});
            WyHelper.sendMessage(player, message);
         }

         int[] pos = nbt.m_128465_("coords");
         if (countdown > 40 && countdown < 200 && countdown % 5 == 0) {
            for(int i = 0; i < 20; ++i) {
               CannonBallProjectile projectile = new CannonBallProjectile(world, (LivingEntity)entity);
               projectile.m_6034_((double)pos[0] + WyHelper.randomWithRange(-50, 50), (double)(pos[1] + 100), (double)pos[2] + WyHelper.randomWithRange(-50, 50));
               projectile.setDamage(50.0F);
               projectile.setMaxLife(60);
               world.m_7967_(projectile);
               projectile.m_37251_(entity, 90.0F, 0.0F, 0.0F, 3.0F, 0.0F);
            }
         }

         if (countdown == 0) {
            stack.m_41774_(1);
            EntityType<CaptainEntity> captainEntity = (EntityType)ModMobs.MARINE_CAPTAIN.get();
            EntityType<GruntEntity> gruntEntity = (EntityType)ModMobs.MARINE_GRUNT.get();
            EntityType<PacifistaEntity> pacifistaEntity = (EntityType)ModMobs.PACIFISTA.get();
            int nrPacifistas = (int)WyHelper.randomWithRange(1, 5);
            int nrCaptains = (int)WyHelper.randomWithRange(10, 20);
            int nrGrunts = (int)WyHelper.randomWithRange(100, 200);

            for(int i = 0; i < nrPacifistas; ++i) {
               BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, captainEntity, new BlockPos(pos[0], pos[1], pos[2]), 50);
               if (spawnPos != null) {
                  pacifistaEntity.m_262455_((ServerLevel)world, (CompoundTag)null, (Consumer)null, spawnPos, MobSpawnType.EVENT, false, false);
               }
            }

            for(int i = 0; i < nrCaptains; ++i) {
               BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, captainEntity, new BlockPos(pos[0], pos[1], pos[2]), 50);
               if (spawnPos != null) {
                  captainEntity.m_262455_((ServerLevel)world, (CompoundTag)null, (Consumer)null, spawnPos, MobSpawnType.EVENT, false, false);
               }
            }

            for(int i = 0; i < nrGrunts; ++i) {
               BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, gruntEntity, new BlockPos(pos[0], pos[1], pos[2]), 50);
               if (spawnPos != null) {
                  gruntEntity.m_262455_((ServerLevel)world, (CompoundTag)null, (Consumer)null, spawnPos, MobSpawnType.EVENT, false, false);
               }
            }
         }

      }
   }

   private int getCountdown(ItemStack stack) {
      return stack.m_41782_() ? stack.m_41783_().m_128451_("countdown") : -1;
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> lore, TooltipFlag tooltip) {
      super.m_7373_(itemStack, world, lore, tooltip);
      lore.add(ModI18n.ITEM_BUSTER_CALL_INFO);
      int countdown = this.getCountdown(itemStack);
      if (countdown > 0) {
         int t = Math.max((countdown - 200) / 20, 0);
         lore.add(Component.m_237110_(ModI18n.ITEM_BUSTER_CALL_TIMER, new Object[]{t}));
      }

   }
}
