package xyz.pixelatedw.mineminenomi.entities;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.NetItem;

public class NetEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {
   private static final int NET_TIME = 1200;
   private Optional<LivingEntity> thrower = Optional.empty();
   private NetItem netItem;
   private NetType type;

   public NetEntity(EntityType<? extends NetEntity> type, Level world) {
      super(type, world);
      this.type = NetType.NORMAL;
   }

   private NetEntity(EntityType<? extends NetEntity> type, Level world, @Nullable LivingEntity thrower, NetItem netItem) {
      super(type, world);
      this.type = NetType.NORMAL;
      this.thrower = Optional.ofNullable(thrower);
      this.netItem = netItem;
      this.type = netItem.getNetType();
   }

   public static NetEntity createNormalNet(Level world, @Nullable LivingEntity thrower) {
      return new NetEntity((EntityType)ModEntities.ROPE_NET.get(), world, thrower, (NetItem)ModItems.ROPE_NET.get());
   }

   public static NetEntity createKairosekiNet(Level world, @Nullable LivingEntity thrower) {
      return new NetEntity((EntityType)ModEntities.KAIROSEKI_NET.get(), world, thrower, (NetItem)ModItems.KAIROSEKI_NET.get());
   }

   public static NetEntity createFromItem(Level world, @Nullable LivingEntity thrower, NetItem netItem) {
      NetEntity netEntity = null;
      switch (netItem.getNetType()) {
         case KAIROSEKI:
            netEntity = createKairosekiNet(world, thrower);
            break;
         case NORMAL:
         default:
            netEntity = createNormalNet(world, thrower);
      }

      return netEntity;
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.m_6060_()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         Vec3 radius = new Vec3(this.m_20191_().m_82362_() / (double)2.0F, this.m_20191_().m_82376_() / (double)2.0F, this.m_20191_().m_82385_() / (double)2.0F);
         List<Entity> entityList = WyHelper.<Entity>getNearbyEntities(this.m_20182_(), this.m_9236_(), radius.f_82479_, radius.f_82480_, radius.f_82481_, this.canHitEntity(), Entity.class);
         Entity entityTarget = (Entity)entityList.stream().findAny().orElse((Object)null);
         if (entityTarget != null) {
            this.m_5790_(new EntityHitResult(entityTarget));
         }
      }

   }

   protected void m_5790_(EntityHitResult result) {
      if (this.m_6084_()) {
         if (!this.m_9236_().f_46443_ && this.netItem != null) {
            if (result.m_82443_() instanceof LivingEntity) {
               LivingEntity living = (LivingEntity)result.m_82443_();
               boolean hpCheck = false;
               switch (this.getNetType()) {
                  case KAIROSEKI -> hpCheck = living.m_21223_() < 100.0F || (double)living.m_21223_() < WyHelper.percentage((double)40.0F, (double)living.m_21233_());
                  case NORMAL -> hpCheck = living.m_21223_() < 50.0F || (double)living.m_21223_() < WyHelper.percentage((double)20.0F, (double)living.m_21233_());
               }

               if (!hpCheck) {
                  this.destroyAndDropItem(this.m_20183_());
                  return;
               }

               if (CombatHelper.isLogiaBlocking(living) || CombatHelper.isShieldBlocking(living)) {
                  this.destroyAndDropItem(this.m_20183_());
                  return;
               }

               for(MobEffectInstance effectInst : living.m_21220_()) {
                  if (effectInst.m_19544_() instanceof CaughtInNetEffect) {
                     this.destroyAndDropItem(this.m_20183_());
                     return;
                  }
               }

               living.m_7292_(new MobEffectInstance(this.netItem.getEffect(), 1200, 0));
               this.m_142687_(RemovalReason.DISCARDED);
            } else {
               Entity isProjectileOfDF = result.m_82443_();
               if (isProjectileOfDF instanceof NuProjectileEntity) {
                  NuProjectileEntity projectile = (NuProjectileEntity)isProjectileOfDF;
                  if (this.getNetType() == NetType.KAIROSEKI) {
                     boolean isProjectileOfDF = (Boolean)projectile.getParent().map((abl) -> abl.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS).orElse(false);
                     if (isProjectileOfDF) {
                        projectile.m_142687_(RemovalReason.DISCARDED);
                        return;
                     }
                  }

                  if (projectile.getDamage() > 5.0F) {
                     this.m_142687_(RemovalReason.DISCARDED);
                  }
               }
            }

         }
      }
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      if (!this.m_9236_().f_46443_ && this.netItem != null) {
         this.destroyAndDropItem(result.m_82425_());
      }
   }

   protected boolean m_5603_(Entity target) {
      return target instanceof NuProjectileEntity ? true : super.m_5603_(target);
   }

   private Predicate<Entity> canHitEntity() {
      return (target) -> target != this;
   }

   private void destroyAndDropItem(BlockPos dropPos) {
      boolean canDrop = true;
      if (this.thrower.isPresent()) {
         Object var4 = this.thrower.get();
         if (var4 instanceof Player) {
            Player player = (Player)var4;
            canDrop = !player.m_150110_().f_35937_;
         }
      }

      if (canDrop) {
         ItemEntity itemEntity = new ItemEntity(this.m_9236_(), (double)dropPos.m_123341_(), (double)(dropPos.m_123342_() + 1), (double)dropPos.m_123343_(), this.netItem.m_7968_());
         this.m_9236_().m_7967_(itemEntity);
      }

      this.m_142687_(RemovalReason.DISCARDED);
   }

   public boolean m_5829_() {
      return true;
   }

   protected float m_7139_() {
      return 0.03F;
   }

   public NetType getNetType() {
      return this.type;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.type.ordinal());
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      int typeId = buffer.readInt();
      this.type = NetType.values()[typeId];
   }
}
