package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class StructuresHelper {
   public static Optional<StructureTemplate> getStructure(ServerLevel level, ResourceLocation name) {
      StructureTemplateManager structuretemplatemanager = level.m_215082_();
      return structuretemplatemanager.m_230407_(name);
   }

   public static boolean spawnStructure(ServerLevel level, ResourceLocation name, BlockPos pos, StructurePlaceSettings settings) {
      Optional<StructureTemplate> optional = getStructure(level, name);
      if (optional.isEmpty()) {
         return false;
      } else {
         BlockState blockstate = level.m_8055_(pos);
         level.m_7260_(pos, blockstate, blockstate, 3);
         ((StructureTemplate)optional.get()).m_230328_(level, pos, pos, settings, RandomSource.m_216335_(Util.m_137550_()), 2);
         return true;
      }
   }

   public static void spawnLoot(LevelAccessor world, BlockPos pos, ResourceLocation lootTable) {
      world.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
      BlockEntity tile = world.m_7702_(pos.m_7495_());
      if (tile instanceof ChestBlockEntity chest) {
         chest.m_59626_(lootTable, world.m_213780_().m_188505_());
      }

   }

   public static boolean isInsideShip(ServerLevel level, BlockPos pos) {
      return level.m_215010_().m_220491_(pos, ModTags.Structures.IS_SHIP).m_73603_();
   }

   public static boolean isInsideMarineStructure(ServerPlayer player) {
      return player.m_284548_().m_215010_().m_220491_(player.m_20183_(), ModTags.Structures.MARINE).m_73603_();
   }

   public static boolean isInsideRevoStructure(ServerPlayer player) {
      return player.m_284548_().m_215010_().m_220491_(player.m_20183_(), ModTags.Structures.REVOLUTIONARY).m_73603_();
   }

   @Nullable
   public static StructureStart getStructureAt(ServerLevel world, BlockPos pos) {
      for(Map.Entry<ResourceKey<Structure>, Structure> entry : world.m_215010_().m_220521_().m_175515_(Registries.f_256944_).m_6579_()) {
         StructureStart struct = world.m_215010_().m_220494_(pos, (Structure)entry.getValue());
         if (struct != StructureStart.f_73561_) {
            return struct;
         }
      }

      return null;
   }
}
