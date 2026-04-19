package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ServerLevel.class})
public interface IServerLevelMixin {
   @Accessor("serverLevelData")
   ServerLevelData getServerLevelData();
}
