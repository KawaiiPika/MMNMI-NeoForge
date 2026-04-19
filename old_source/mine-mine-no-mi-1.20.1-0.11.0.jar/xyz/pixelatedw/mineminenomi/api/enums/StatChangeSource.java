package xyz.pixelatedw.mineminenomi.api.enums;

public enum StatChangeSource {
   NATURAL,
   KILL_NPC,
   KILL_PLAYER,
   QUEST,
   CHALLENGE,
   COMMAND,
   DEATH,
   STORE;

   // $FF: synthetic method
   private static StatChangeSource[] $values() {
      return new StatChangeSource[]{NATURAL, KILL_NPC, KILL_PLAYER, QUEST, CHALLENGE, COMMAND, DEATH, STORE};
   }
}
