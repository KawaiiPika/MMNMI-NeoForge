package xyz.pixelatedw.mineminenomi.api;

import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;

public class NoConflictContext implements IKeyConflictContext {
   public static final NoConflictContext INSTANCE = new NoConflictContext();

   public boolean isActive() {
      return KeyConflictContext.IN_GAME.isActive();
   }

   public boolean conflicts(IKeyConflictContext other) {
      return false;
   }
}
