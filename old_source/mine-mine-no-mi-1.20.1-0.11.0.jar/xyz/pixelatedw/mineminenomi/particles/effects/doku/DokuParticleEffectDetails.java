package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DokuParticleEffectDetails extends ParticleEffect.Details {
   private boolean hasVenomDemon;

   public void save(CompoundTag nbt) {
      nbt.m_128379_("hasVenomDemon", this.hasVenomDemon);
   }

   public void load(CompoundTag nbt) {
      this.hasVenomDemon = nbt.m_128471_("hasVenomDemon");
   }

   public DokuParticleEffectDetails setHasVenomDemon() {
      this.hasVenomDemon = true;
      return this;
   }

   public boolean hasVenomDemon() {
      return this.hasVenomDemon;
   }
}
