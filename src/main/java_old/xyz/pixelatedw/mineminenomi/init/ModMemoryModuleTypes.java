package xyz.pixelatedw.mineminenomi.init;

import java.util.List;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class ModMemoryModuleTypes {
   public static final RegistryObject<MemoryModuleType<List<AbstractDugongEntity>>> NEARBY_ADULT_DUGONGS = ModRegistry.registerMemoryModule("nearby_adult_dugongs");
   public static final RegistryObject<MemoryModuleType<GlobalPos>> LAST_EXPLOSION_HEARD;

   public static void init() {
   }

   static {
      LAST_EXPLOSION_HEARD = ModRegistry.registerMemoryModule("last_explosion_heard", GlobalPos.f_122633_);
   }
}
