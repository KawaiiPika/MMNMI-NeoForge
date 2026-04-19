package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class NPCPhaseManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Mob entity;
   private NPCPhase<?> currentPhase;
   private NPCPhase<?> previousPhase;

   public NPCPhaseManager(Mob entity) {
      this.entity = entity;
   }

   public void setPhase(NPCPhase<?> phase) {
      if (this.currentPhase == null || !phase.equals(this.currentPhase)) {
         if (this.currentPhase != null) {
            this.currentPhase.stopPhase();
         }

         this.previousPhase = this.currentPhase;
         this.currentPhase = phase;
         this.currentPhase.startPhase();
         if (FGCommand.SHOW_NPC_ABILITY_LOG) {
            WyDebug.debug(String.format("Switched %s's phase to: %s", this.entity.m_5446_().getString(), this.currentPhase.toString()));
         }
      }

   }

   public void tick() {
      if (!this.entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
         if (this.currentPhase != null) {
            this.currentPhase.tick();
         }

      }
   }

   public @Nullable NPCPhase<?> getPreviousPhase() {
      return this.previousPhase;
   }

   public @Nullable NPCPhase<?> getCurrentPhase() {
      return this.currentPhase;
   }

   public @Nullable boolean isCurrentPhase(NPCPhase<?> phase) {
      return this.currentPhase.equals(phase);
   }
}
