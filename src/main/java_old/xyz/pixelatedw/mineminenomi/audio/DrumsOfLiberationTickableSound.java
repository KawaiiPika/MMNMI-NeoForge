package xyz.pixelatedw.mineminenomi.audio;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DrumsOfLiberationTickableSound extends AbstractTickableSoundInstance {
   private static final int INTRO_DURATION = 72;
   private static final int FADE_IN_DURATION = 60;
   private final int stage;
   private final Entity source;
   private float playTime = 0.0F;

   public DrumsOfLiberationTickableSound(SoundEvent sound, int stage, Entity source) {
      super(sound, SoundSource.PLAYERS, SoundInstance.m_235150_());
      this.stage = stage;
      this.source = source;
      this.f_119578_ = this.stage == 1;
      this.f_119579_ = 0;
      this.f_119573_ = 0.0F;
      this.f_119582_ = true;
   }

   public boolean m_7767_() {
      return this.source != null && this.source.m_6084_();
   }

   public boolean m_7784_() {
      return true;
   }

   public void m_7788_() {
      if (this.source.m_6084_()) {
         if (this.stage == 0) {
            if (this.playTime > 72.0F) {
               this.m_119609_();
            } else {
               this.f_119573_ = Mth.m_14036_(this.playTime / 120.0F, 0.0F, 0.5F);
            }
         } else if (this.stage == 1) {
            if (this.playTime > 1200.0F) {
               this.m_119609_();
            } else if (this.playTime > 60.0F) {
               this.f_119573_ = 0.5F;
            }
         }
      } else {
         this.m_119609_();
      }

   }
}
