package xyz.pixelatedw.mineminenomi.challenges.arenas;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.ChallengesHelper;

public class CircusArena extends ChallengeArena {
   public static final CircusArena INSTANCE = new CircusArena();
   private static final int ARENA_SIZE_RADIUS = 50;
   private static final int ARENA_SIZE = 100;

   private CircusArena() {
      super(ArenaStyle.SIMPLE);
   }

   public void buildArena(InProgressChallenge challenge, BlockQueue queue) {
      PoneglyphLowspecArena.INSTANCE.buildArena(challenge, queue);
   }

   public BoundingBox getArenaLimits() {
      return new BoundingBox(-50, -50, -50, 50, 50, 50);
   }

   public static ChallengeArena.SpawnPosition getChallengerSpawnPos(int posId, InProgressChallenge challenge) {
      BlockPos pos = new BlockPos(challenge.getArenaPos().m_123341_() + 15 - (posId + 1), challenge.getArenaPos().m_123342_(), challenge.getArenaPos().m_123343_() + 15);
      return new ChallengeArena.SpawnPosition(pos, 135.0F, 0.0F);
   }

   public static ChallengeArena.SpawnPosition[] getEnemySpawnPos(InProgressChallenge challenge) {
      BlockPos pos = new BlockPos(challenge.getArenaPos().m_123341_() - 15, challenge.getArenaPos().m_123342_(), challenge.getArenaPos().m_123343_() - 15);
      return ChallengesHelper.get4DefaultSpawnPoints(pos, 140.0F);
   }
}
