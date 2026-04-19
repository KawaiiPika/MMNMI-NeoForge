package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class ShamblesAbility extends Ability {
   private static final ResourceLocation SHAMBLES_SINGLE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/shambles.png");
   private static final ResourceLocation SHAMBLES_GROUP_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/shambles_group.png");
   private static final float COOLDOWN = 40.0F;
   public static final RegistryObject<AbilityCore<ShamblesAbility>> INSTANCE = ModRegistry.registerAbility("shambles", "Shambles", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user swaps place with the closest entity or block within the ROOM. Alt mode allows it to switch multiple entities within the ROOM.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShamblesAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F)).build("mineminenomi");
   });
   private final AltModeComponent<Mode> altModeComponent;
   private final RangeComponent rangeComponent;
   private static final Predicate<Entity> SHAMBLES_LIST = (target) -> {
      if (target instanceof LightningBolt) {
         return false;
      } else if (target instanceof SphereEntity) {
         return false;
      } else {
         if (target instanceof NuProjectileEntity) {
            NuProjectileEntity proj = (NuProjectileEntity)target;
            if (!proj.isPhysical()) {
               return false;
            }
         }

         return true;
      }
   };

   public ShamblesAbility(AbilityCore<ShamblesAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, ShamblesAbility.Mode.SINGLE)).addChangeModeEvent(this::onAltModeChange);
      this.rangeComponent = new RangeComponent(this);
      this.addComponents(new AbilityComponent[]{this.altModeComponent, this.rangeComponent});
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      RoomAbility roomAbility = (RoomAbility)AbilityCapability.get(entity).map((props) -> (RoomAbility)props.getEquippedAbility((AbilityCore)RoomAbility.INSTANCE.get())).orElse((Object)null);
      if (roomAbility != null) {
         boolean hadTarget = false;
         if (this.altModeComponent.getCurrentMode() == ShamblesAbility.Mode.SINGLE) {
            HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F);
            if (mop instanceof EntityHitResult) {
               EntityHitResult entityRayTraceResult = (EntityHitResult)mop;
               Entity target = entityRayTraceResult.m_82443_();
               if (!roomAbility.isEntityInRoom(target)) {
                  return;
               }

               if (!SHAMBLES_LIST.test(target)) {
                  return;
               }

               float[] beforeCoords = new float[]{(float)entity.m_20185_(), (float)entity.m_20186_(), (float)entity.m_20189_(), entity.m_146908_(), entity.m_146909_()};
               entity.m_264318_((ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_(), target.m_20189_(), EnumSet.allOf(RelativeMovement.class), target.m_146908_(), target.m_146909_());
               target.m_264318_((ServerLevel)entity.m_9236_(), (double)beforeCoords[0], (double)beforeCoords[1], (double)beforeCoords[2], EnumSet.allOf(RelativeMovement.class), beforeCoords[3], beforeCoords[4]);
               entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
               entity.m_9236_().m_5594_((Player)null, target.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
               hadTarget = true;
            } else if (mop instanceof BlockHitResult) {
               BlockHitResult result = (BlockHitResult)mop;
               BlockPos pos = result.m_82425_();
               BlockState state = entity.m_9236_().m_8055_(pos);
               BlockPos entityPos = entity.m_20183_();
               BlockState entityPosState = entity.m_9236_().m_8055_(entityPos);
               boolean isInsideRoom = roomAbility.isPositionInRoom(pos);
               boolean isDestinationBanned = state.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
               boolean isOriginBanned = entityPosState.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
               if (isInsideRoom && !isDestinationBanned && !isOriginBanned) {
                  BlockPos beforePos = entity.m_20183_();
                  ProtectedAreasData protectedAreaData = ProtectedAreasData.get((ServerLevel)entity.m_9236_());
                  boolean a1 = protectedAreaData.isInsideRestrictedArea(beforePos.m_123341_(), beforePos.m_123342_(), beforePos.m_123343_());
                  boolean a2 = protectedAreaData.isInsideRestrictedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
                  if (a1 != a2) {
                     return;
                  }

                  entity.m_7678_((double)pos.m_123341_(), (double)(pos.m_123342_() + 1), (double)pos.m_123343_(), entity.m_146908_(), entity.m_146909_());
                  boolean b1 = NuWorld.setBlockState((Entity)entity, beforePos, state, 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID);
                  boolean b2 = NuWorld.setBlockState((Entity)entity, pos, Blocks.f_50016_.m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID);
                  if (b1 && b2) {
                     entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
                     hadTarget = true;
                  }
               }
            }
         } else if (this.altModeComponent.getCurrentMode() == ShamblesAbility.Mode.GROUP) {
            BlockPos centerPos = roomAbility.getCenterBlock();
            List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, centerPos, roomAbility.getROOMSize());
            Collections.shuffle(targets);

            for(int i = 0; i < targets.size() && i < targets.size() && i + 1 < targets.size(); i += 2) {
               Entity target1 = (Entity)targets.get(i);
               Entity target2 = (Entity)targets.get(i + 1);
               if (roomAbility.isPositionInRoom(target1.m_20183_()) && roomAbility.isPositionInRoom(target2.m_20183_())) {
                  float[] beforeCoords = new float[]{(float)target2.m_20185_(), (float)target2.m_20186_(), (float)target2.m_20189_(), target2.m_146908_(), target2.m_146909_()};
                  target2.m_264318_((ServerLevel)entity.m_9236_(), target1.m_20185_(), target1.m_20186_(), target1.m_20189_(), EnumSet.allOf(RelativeMovement.class), target1.m_146908_(), target1.m_146909_());
                  target1.m_264318_((ServerLevel)entity.m_9236_(), (double)beforeCoords[0], (double)beforeCoords[1], (double)beforeCoords[2], EnumSet.allOf(RelativeMovement.class), beforeCoords[3], beforeCoords[4]);
                  entity.m_9236_().m_5594_((Player)null, target2.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
                  entity.m_9236_().m_5594_((Player)null, target1.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
               }
            }

            if (targets.size() >= 2) {
               hadTarget = true;
            }
         }

         if (hadTarget) {
            this.cooldownComponent.startCooldown(entity, 40.0F);
         }

      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == ShamblesAbility.Mode.SINGLE) {
         this.setDisplayIcon(SHAMBLES_SINGLE_ICON);
      } else if (mode == ShamblesAbility.Mode.GROUP) {
         this.setDisplayIcon(SHAMBLES_GROUP_ICON);
      }

      return true;
   }

   public static enum Mode {
      SINGLE,
      GROUP;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{SINGLE, GROUP};
      }
   }
}
