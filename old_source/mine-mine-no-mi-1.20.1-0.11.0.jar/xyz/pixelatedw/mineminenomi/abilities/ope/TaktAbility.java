package xyz.pixelatedw.mineminenomi.abilities.ope;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class TaktAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   private static final float HOLD_TIME = 60.0F;
   public static final RegistryObject<AbilityCore<TaktAbility>> INSTANCE = ModRegistry.registerAbility("takt", "Takt", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user lifts entities its looking at, preventing them from moving freely.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TaktAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(60.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private List<Entity> grabbedEntities = new ArrayList();

   public TaktAbility(AbilityCore<TaktAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      RoomAbility abl = (RoomAbility)AbilityCapability.get(entity).map((props) -> (RoomAbility)props.getEquippedAbility((AbilityCore)RoomAbility.INSTANCE.get())).orElse((Object)null);
      if (abl != null) {
         HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)abl.getROOMSize());
         BlockPos blockPos = BlockPos.m_274446_(mop.m_82450_());
         if (mop.m_6662_() == Type.BLOCK) {
            blockPos = new BlockPos(((BlockHitResult)mop).m_82425_());
         } else if (mop.m_6662_() == Type.ENTITY) {
            blockPos = new BlockPos(((EntityHitResult)mop).m_82443_().m_20183_());
         }

         Function<BlockPos, FallingBlockEntity> mapper = (pos) -> {
            BlockState state = entity.m_9236_().m_8055_(pos);
            FallingBlockEntity fallingBlock = FallingBlockEntity.m_201971_(entity.m_9236_(), pos, state);
            AbilityHelper.setDeltaMovement(fallingBlock, (double)0.0F, (double)0.0F, (double)0.0F);
            fallingBlock.f_31942_ = 5;
            fallingBlock.m_20242_(true);
            fallingBlock.f_31943_ = false;
            entity.m_9236_().m_7471_(pos, true);
            return fallingBlock;
         };
         Stream var10000 = WyHelper.getNearbyBlocks(blockPos, entity.m_9236_(), 2, isPositionGriefable(entity), ImmutableList.of(Blocks.f_50016_)).stream().map(mapper);
         List var10001 = this.grabbedEntities;
         Objects.requireNonNull(var10001);
         var10000.forEach(var10001::add);
         var10000 = WyHelper.getNearbyLiving(mop.m_82450_(), entity.m_9236_(), (double)2.0F, ModEntityPredicates.getEnemyFactions(entity)).stream().filter(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL).filter((living) -> abl.isPositionInRoom(living.m_20183_()));
         var10001 = this.grabbedEntities;
         Objects.requireNonNull(var10001);
         var10000.forEach(var10001::add);
         if (!this.grabbedEntities.isEmpty()) {
            this.continuousComponent.triggerContinuity(entity, 60.0F);
         }

      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabbedEntities.isEmpty()) {
            this.continuousComponent.stopContinuity(entity);
         } else {
            RoomAbility abl = (RoomAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)RoomAbility.INSTANCE.get());
            if (abl != null) {
               this.grabbedEntities.stream().forEach((target) -> {
                  target.m_146926_(target.f_19860_);
                  target.m_146922_(target.f_19859_);
                  Random rand = new Random((long)target.hashCode());
                  double offsetX = WyHelper.randomWithRange((Random)rand, -2, 2);
                  double offsetY = WyHelper.randomWithRange((Random)rand, -2, 2);
                  double offsetZ = WyHelper.randomWithRange((Random)rand, -2, 2);
                  double distance = (double)8.0F;
                  Vec3 lookVec = entity.m_20154_();
                  Vec3 pos = new Vec3(lookVec.f_82479_ * distance + offsetX, (double)entity.m_20192_() / (double)2.0F + lookVec.f_82480_ * distance + offsetY, lookVec.f_82481_ * distance + offsetZ);
                  if (target instanceof LivingEntity && abl.isPositionInRoom(target.m_20183_()) || isPositionGriefable(entity).test(target.m_20183_())) {
                     AbilityHelper.setDeltaMovement(target, entity.m_20182_().m_82549_(pos).m_82546_(target.m_20182_()));
                     if (target instanceof ServerPlayer) {
                        ServerPlayer var14 = (ServerPlayer)target;
                     }
                  }

                  target.f_19789_ = 0.0F;
               });
            }
         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.grabbedEntities.stream().filter(Entity::m_20068_).forEach((e) -> e.m_20242_(false));
      this.grabbedEntities.clear();
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private static Predicate<BlockPos> isPositionGriefable(LivingEntity entity) {
      RoomAbility abl = (RoomAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)RoomAbility.INSTANCE.get());
      if (abl == null) {
         return Predicates.alwaysFalse();
      } else {
         ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)entity.m_9236_());
         return (pos) -> {
            boolean isGriefDisabled = !ServerConfig.isAbilityGriefingEnabled();
            if (isGriefDisabled) {
               return false;
            } else {
               Optional<ProtectedArea> area = worldData.getProtectedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
               if (area.isPresent() && !((ProtectedArea)area.get()).canDestroyBlocks()) {
                  return false;
               } else if (!abl.isPositionInRoom(pos)) {
                  return false;
               } else {
                  BlockState state = entity.m_9236_().m_8055_(pos);
                  boolean isBlockBanned = state.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
                  return !isBlockBanned;
               }
            }
         };
      }
   }
}
