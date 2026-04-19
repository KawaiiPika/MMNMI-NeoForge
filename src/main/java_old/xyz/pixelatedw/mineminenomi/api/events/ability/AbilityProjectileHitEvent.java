package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

@Cancelable
public class AbilityProjectileHitEvent extends Event {
   private NuProjectileEntity projectile;
   private HitResult hit;

   public AbilityProjectileHitEvent(NuProjectileEntity NuProjectileEntity, HitResult hit) {
      this.projectile = NuProjectileEntity;
      this.hit = hit;
   }

   public NuProjectileEntity getProjectile() {
      return this.projectile;
   }

   public HitResult getHit() {
      return this.hit;
   }
}
