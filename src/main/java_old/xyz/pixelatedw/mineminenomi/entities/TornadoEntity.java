package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.suna.SablesNewParticleEffect;

public class TornadoEntity extends Entity implements IEntityAdditionalSpawnData {
   private LivingEntity owner;
   private SablesNewParticleEffect.Details details = new SablesNewParticleEffect.Details();
   private int maxLife = 60;
   private float size = 1.0F;
   private float speed = 1.0F;
   private Vec3 vector = null;
   private List<BlockState> states = new ArrayList();

   public TornadoEntity(Level level, LivingEntity entity) {
      super((EntityType)ModEntities.TORNADO.get(), level);
      this.owner = entity;
   }

   public TornadoEntity(EntityType<?> type, Level pLevel) {
      super(type, pLevel);
   }

   public void setMaxLife(int maxLife) {
      this.maxLife = maxLife;
   }

   public void setSize(float size) {
      this.size = size;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public int getMaxLife() {
      return this.maxLife;
   }

   public float getSize() {
      return this.size;
   }

   public float getSpeed() {
      return this.speed;
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.maxLife > 0 && this.f_19797_ >= this.maxLife) {
            this.m_146870_();
            return;
         }

         if (this.owner == null) {
            this.m_146870_();
            return;
         }

         if (this.f_19797_ % 40 == 0) {
            this.details.setSize(this.size);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SABLES_NEW.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.details);
         }

         AABB box = (new AABB(this.m_20183_())).m_82377_((double)(this.size / 2.0F), (double)this.size, (double)(this.size / 2.0F));

         for(Entity entity : this.m_9236_().m_6443_(Entity.class, box, (e) -> e != this.owner)) {
            double px = entity.m_20184_().f_82479_ + (this.m_20185_() - entity.m_20185_()) / (double)25.0F;
            double py = entity.m_20184_().f_82480_ + (this.m_20186_() + (double)(this.size / 2.0F) - entity.m_20186_()) / (double)25.0F;
            double pz = entity.m_20184_().f_82481_ + (this.m_20189_() - entity.m_20189_()) / (double)25.0F;
            AbilityHelper.setDeltaMovement(entity, px, py, pz);
            if (this.m_20270_(entity) < 2.0F) {
               entity.m_6469_(this.m_269291_().m_269515_(), this.getSize() / 3.0F);
            }

            if (entity instanceof FallingBlockEntity) {
               FallingBlockEntity falling = (FallingBlockEntity)entity;
               this.states.add(falling.m_31980_());
            }
         }

         Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)this.m_9236_()).getProtectedArea((int)this.m_20185_(), (int)this.m_20186_(), (int)this.m_20189_());
         if (area.isPresent() && ServerConfig.isAbilityGriefingEnabled()) {
            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

            for(double x = box.f_82288_; x < box.f_82291_; ++x) {
               for(double y = box.f_82289_; y < box.f_82292_; ++y) {
                  for(double z = box.f_82290_; z < box.f_82293_; ++z) {
                     mutpos.m_122169_(x, y, z);
                     BlockState state = this.m_9236_().m_8055_(mutpos);
                     boolean hasAirAbove = this.m_9236_().m_8055_(mutpos.m_7494_()).m_60734_() == Blocks.f_50016_;
                     if (!state.m_60795_() && (double)this.f_19796_.m_188501_() > 0.9995 && !DefaultProtectionRules.CORE_FOLIAGE_ORE.isBanned(state) && hasAirAbove) {
                        FallingBlockEntity fallingBlock = FallingBlockEntity.m_201971_(this.m_9236_(), mutpos, state);
                        AbilityHelper.setDeltaMovement(fallingBlock, (double)0.0F, WyHelper.randomDouble() * (double)2.0F, (double)0.0F);
                        fallingBlock.f_31943_ = false;
                        fallingBlock.f_31942_ = 1;
                        this.m_9236_().m_7967_(fallingBlock);
                        NuWorld.setBlockState((Level)this.m_9236_(), mutpos, Blocks.f_50016_.m_49966_(), 2, (BlockProtectionRule)null);
                     }
                  }
               }
            }
         }

         if (this.vector != null) {
            this.f_19794_ = true;
            Vec3 dist = this.m_20182_().m_82546_(this.vector).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F);
            double speedReduction = (double)20.0F;
            double speed = 0.4;
            double xSpeed = Math.min(speed, -dist.f_82479_ / speedReduction);
            double zSpeed = Math.min(speed, -dist.f_82481_ / speedReduction);
            this.m_6478_(MoverType.SELF, new Vec3(xSpeed, (double)0.0F, zSpeed));
         }

         if (this.m_20070_() && this.f_19797_ % 20 == 0) {
            this.m_146870_();
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_20039_(BlockPos pPos, BlockState pState) {
      return false;
   }

   public EntityDimensions m_6972_(Pose pose) {
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(this.size / 2.0F);
      return newSize;
   }

   protected void m_7378_(CompoundTag nbt) {
      this.maxLife = nbt.m_128451_("maxLife");
      this.size = nbt.m_128457_("size");
      this.speed = nbt.m_128457_("speed");
   }

   protected void m_7380_(CompoundTag nbt) {
      nbt.m_128405_("maxLife", this.maxLife);
      nbt.m_128350_("size", this.size);
      nbt.m_128350_("speed", this.speed);
   }

   public void writeSpawnData(FriendlyByteBuf buf) {
      buf.writeFloat(this.size);
      buf.writeFloat(this.speed);
   }

   public void readSpawnData(FriendlyByteBuf buf) {
      this.size = buf.readFloat();
      this.speed = buf.readFloat();
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public List<BlockState> getBlocksUsed() {
      return this.states;
   }

   public void setVector(Vec3 vector) {
      this.vector = vector;
   }
}
