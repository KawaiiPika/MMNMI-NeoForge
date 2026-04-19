package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class ChallengesHelper {
   public static ChallengeArena.SpawnPosition[] get4DefaultSpawnPoints(BlockPos pos, float yaw) {
      return get4DefaultSpawnPoints(pos, yaw, 0.0F);
   }

   public static ChallengeArena.SpawnPosition[] get4DefaultSpawnPoints(BlockPos pos, float yaw, float pitch) {
      return get4DefaultSpawnPoints(pos, yaw, 0.0F, 1);
   }

   public static ChallengeArena.SpawnPosition[] get4DefaultSpawnPoints(BlockPos pos, float yaw, float pitch, int spacing) {
      ChallengeArena.SpawnPosition first = new ChallengeArena.SpawnPosition(pos, yaw, 0.0F);
      ChallengeArena.SpawnPosition second = new ChallengeArena.SpawnPosition(pos.m_7918_(-spacing, 0, 0), yaw, 0.0F);
      ChallengeArena.SpawnPosition third = new ChallengeArena.SpawnPosition(pos.m_7918_(0, 0, -spacing), yaw, 0.0F);
      ChallengeArena.SpawnPosition fourth = new ChallengeArena.SpawnPosition(pos.m_7918_(-spacing, 0, -spacing), yaw, 0.0F);
      return new ChallengeArena.SpawnPosition[]{first, second, third, fourth};
   }

   public static Map<String, List<ChallengeCore<?>>> getChallengesByGroup(String category) {
      List<ChallengeCore<?>> challenges = new ArrayList();
      ((IForgeRegistry)WyRegistry.CHALLENGES.get()).getEntries().stream().forEach((ro) -> challenges.add((ChallengeCore)ro.getValue()));
      Map<String, List<ChallengeCore<?>>> map = (Map)challenges.stream().collect(Collectors.groupingBy(ChallengeCore::getCategory));
      return map;
   }
}
