package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class ChallengeTeleporter implements ITeleporter {
   private InProgressChallenge challenge;
   private int spawnId = 0;
   private boolean toChallenge = true;

   public ChallengeTeleporter(InProgressChallenge challenge) {
      this.challenge = challenge;
   }

   public void teleportToChallengeWorld(Entity entity) {
      this.toChallenge = true;
      entity.changeDimension(this.challenge.getShard(), this);
   }

   public void teleportToHomeWorld(Entity entity) {
      this.toChallenge = false;
      entity.changeDimension(this.getTargetWorld(entity), this);
   }

   public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
      if (this.toChallenge) {
         ChallengeArena.SpawnPosition pos = this.challenge.getChallengerPosition().getChallengerSpawnPos(this.spawnId, this.challenge);
         ++this.spawnId;
         Vec3 vecPos = new Vec3((double)pos.getPos().m_123341_(), (double)pos.getPos().m_123342_(), (double)pos.getPos().m_123343_());
         PortalInfo info = new PortalInfo(vecPos, Vec3.f_82478_, pos.getYaw(), pos.getPitch());
         return info;
      } else {
         ServerLevel spawnWorld = this.getTargetWorld(entity);
         BlockPos spawnPos = null;
         if (ServerConfig.isReturnToSafetyEnabled()) {
            if (entity instanceof ServerPlayer) {
               spawnPos = ((ServerPlayer)entity).m_8961_();
            }

            if (spawnPos == null) {
               spawnPos = spawnWorld.m_220360_();
            }
         } else {
            spawnPos = this.challenge.getReturnPosition();
         }

         Vec3 vecPos = new Vec3((double)spawnPos.m_123341_(), (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_());
         PortalInfo info = new PortalInfo(vecPos, Vec3.f_82478_, entity.m_146908_(), entity.m_146909_());
         return info;
      }
   }

   private ServerLevel getTargetWorld(Entity entity) {
      if (this.toChallenge) {
         return this.challenge.getShard();
      } else {
         ServerLevel spawnWorld;
         if (ServerConfig.isReturnToSafetyEnabled()) {
            spawnWorld = entity.m_20194_().m_129783_();
         } else {
            spawnWorld = entity.m_20194_().m_129880_(this.challenge.getReturnDimension());
         }

         return spawnWorld;
      }
   }
}
