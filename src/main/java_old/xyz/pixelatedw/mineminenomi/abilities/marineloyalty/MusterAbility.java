package xyz.pixelatedw.mineminenomi.abilities.marineloyalty;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MusterAbility extends Ability {
   private static final float COOLDOWN = 1200.0F;
   public static final RegistryObject<AbilityCore<MusterAbility>> INSTANCE = ModRegistry.registerAbility("muster", "Muster", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user musters some reinforcements.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.FACTION, MusterAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1200.0F)).setUnlockCheck(MusterAbility::canUnlock).build("mineminenomi");
   });

   public MusterAbility(AbilityCore<MusterAbility> core) {
      super(core);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      EntityType<GruntEntity> gruntEntity = (EntityType)ModMobs.MARINE_GRUNT.get();
      EntityType<CaptainEntity> captain = (EntityType)ModMobs.MARINE_CAPTAIN.get();
      Level var6 = entity.m_9236_();
      if (var6 instanceof ServerLevel serverLevel) {
         for(int i = 0; (double)i < WyHelper.randomWithRange(3, 10); ++i) {
            BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(entity.m_9236_(), gruntEntity, entity.m_20183_(), 10);
            if (spawnPos != null) {
               gruntEntity.m_262496_(serverLevel, spawnPos, MobSpawnType.EVENT);
            }
         }

         for(int i = 0; (double)i < WyHelper.randomWithRange(1, 3); ++i) {
            BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(entity.m_9236_(), captain, entity.m_20183_(), 10);
            captain.m_262496_(serverLevel, spawnPos, MobSpawnType.EVENT);
         }
      }

      this.cooldownComponent.startCooldown(entity, 1200.0F);
   }

   private static boolean canUnlock(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         return props.hasRank(MarineRank.CAPTAIN);
      }
   }
}
