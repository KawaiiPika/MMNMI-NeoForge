package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public interface IWithHome {
   Optional<Vec3> getHomePosition();
}
