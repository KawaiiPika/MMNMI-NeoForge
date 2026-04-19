package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SpikeEntity extends Entity {
   protected boolean inGround;
   protected List<LivingEntity> targets = new ArrayList();

   public SpikeEntity(EntityType<? extends SpikeEntity> type, Level world) {
      super(type, world);
   }

   public SpikeEntity(Level world) {
      super((EntityType)ModEntities.SPIKE.get(), world);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_20096_()) {
         this.m_6478_(MoverType.SELF, new Vec3((double)0.0F, -0.3, (double)0.0F));
      }

      if (!this.m_9236_().f_46443_) {
         for(LivingEntity entity : WyHelper.getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)0.0F, (Predicate)null)) {
            entity.m_6469_(this.m_269291_().m_269325_(), 3.0F);
            if (entity.m_217043_().m_188500_() < (double)0.5F) {
               entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 1, false, true));
            }
         }

         if ((double)this.f_19797_ > (double)1200.0F + WyHelper.randomWithRange(0, 100)) {
            this.m_142687_(RemovalReason.DISCARDED);
         }

      }
   }

   protected float getGravityVelocity() {
      return 0.03F;
   }

   public boolean m_6783_(double distance) {
      return distance < (double)4096.0F ? true : super.m_6783_(distance);
   }

   protected void m_8097_() {
   }

   public boolean m_5829_() {
      return false;
   }

   protected void m_7378_(CompoundTag compound) {
      compound.m_128379_("inGround", this.inGround);
   }

   protected void m_7380_(CompoundTag compound) {
      this.inGround = compound.m_128471_("inGround");
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
