package xyz.pixelatedw.mineminenomi.api.enums;

public enum NPCCommand {
   IDLE,
   ATTACK,
   FOLLOW,
   STAY,
   GUARD;

   // $FF: synthetic method
   private static NPCCommand[] $values() {
      return new NPCCommand[]{IDLE, ATTACK, FOLLOW, STAY, GUARD};
   }
}
