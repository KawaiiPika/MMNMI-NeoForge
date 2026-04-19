package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

@Cancelable
public class ProjectileShootEvent extends Event {
   private NuProjectileEntity projectile;
   private float velocity;
   private float inaccuracy;

   public ProjectileShootEvent(NuProjectileEntity NuProjectileEntity, float velocity, float inaccuracy) {
      this.projectile = NuProjectileEntity;
      this.velocity = velocity;
      this.inaccuracy = inaccuracy;
   }

   public NuProjectileEntity getProjectile() {
      return this.projectile;
   }

   public float getVelocity() {
      return this.velocity;
   }

   public float getInaccuracy() {
      return this.inaccuracy;
   }
}
