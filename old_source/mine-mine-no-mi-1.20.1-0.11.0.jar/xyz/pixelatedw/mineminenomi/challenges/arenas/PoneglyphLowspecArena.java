package xyz.pixelatedw.mineminenomi.challenges.arenas;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.RandomBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengesBlockGenerators;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.ChallengesHelper;
import xyz.pixelatedw.mineminenomi.blocks.PoneglyphBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class PoneglyphLowspecArena extends ChallengeArena {
   public static final PoneglyphLowspecArena INSTANCE = new PoneglyphLowspecArena();
   public static final ChallengeCore.ArenaEntry ARENA_INFO;
   private static final int ARENA_SIZE_RADIUS = 50;
   private static final int ARENA_THICCNESS = 2;

   private PoneglyphLowspecArena() {
      super(ArenaStyle.LOWSPEC);
   }

   public void buildArena(InProgressChallenge challenge, BlockQueue queue) {
      boolean isUlti = challenge.isUltimateDifficulty();
      RandomBlockPlacer randomPlacer = (new RandomBlockPlacer()).addBlock((BlockState)((Block)ModBlocks.PONEGLYPH.get()).m_49966_().m_61124_(PoneglyphBlock.TEXTURE, 0), 100).addBlock((BlockState)((Block)ModBlocks.PONEGLYPH.get()).m_49966_().m_61124_(PoneglyphBlock.TEXTURE, 1), 100).addBlock((BlockState)((Block)ModBlocks.PONEGLYPH.get()).m_49966_().m_61124_(PoneglyphBlock.TEXTURE, 2), 100).setSize(50, 2).setBlockQueue(queue);
      randomPlacer.generate(challenge.getShard(), challenge.getArenaPos().m_6625_(6), ChallengesBlockGenerators.CUBE_XZ);
      SimpleBlockPlacer simplePlacer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50375_.m_49966_()).setSize(50, 25).setHollow().setThickness(3);
      simplePlacer.generate(challenge.getShard(), challenge.getArenaPos().m_6630_(17), BlockGenerators.CUBE);
   }

   public BoundingBox getArenaLimits() {
      return new BoundingBox(-50, -50, -50, 50, 50, 50);
   }

   public static ChallengeArena.SpawnPosition getChallengerSpawnPos(int posId, InProgressChallenge challenge) {
      BlockPos pos = new BlockPos(challenge.getArenaPos().m_123341_() + 15 - (posId + 1), challenge.getArenaPos().m_123342_() - 3, challenge.getArenaPos().m_123343_() + 15);
      return new ChallengeArena.SpawnPosition(pos, 135.0F, 0.0F);
   }

   public static ChallengeArena.SpawnPosition[] getEnemySpawnPos(InProgressChallenge challenge) {
      BlockPos pos = new BlockPos(challenge.getArenaPos().m_123341_() - 15, challenge.getArenaPos().m_123342_() - 3, challenge.getArenaPos().m_123343_() - 15);
      return ChallengesHelper.get4DefaultSpawnPoints(pos, 140.0F);
   }

   static {
      ARENA_INFO = new ChallengeCore.ArenaEntry(INSTANCE, PoneglyphLowspecArena::getChallengerSpawnPos, PoneglyphLowspecArena::getEnemySpawnPos);
   }
}
