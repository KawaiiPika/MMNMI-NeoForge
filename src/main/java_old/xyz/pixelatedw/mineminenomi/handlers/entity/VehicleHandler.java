package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class VehicleHandler {
   public static double getStrikerCameraZoom(Player player) {
      return player.m_20202_() != null && player.m_20202_() instanceof StrikerEntity ? (double)7.0F : (double)0.0F;
   }
}
