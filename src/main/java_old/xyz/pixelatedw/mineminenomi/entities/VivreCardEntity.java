package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.VivreCardItem;

public class VivreCardEntity extends Entity {
   private @Nullable LivingEntity owner;
   private UUID ownerUUID;
   private final double speedLimit;

   public VivreCardEntity(EntityType<? extends VivreCardEntity> type, Level world) {
      super(type, world);
      this.speedLimit = 0.001;
   }

   public VivreCardEntity(Level worldIn) {
      this((EntityType)ModEntities.VIVRE_CARD.get(), worldIn);
   }

   public void setOwner(UUID uuid) {
      this.ownerUUID = uuid;
      this.owner = (LivingEntity)((ServerLevel)this.m_9236_()).m_8791_(this.ownerUUID);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (!this.m_20096_()) {
            this.m_6478_(MoverType.SELF, new Vec3((double)0.0F, -0.1, (double)0.0F));
         }

         if (this.ownerUUID != null && !this.m_6060_()) {
            if (this.owner != null) {
               if (this.owner.m_21223_() <= 0.0F) {
                  WyHelper.spawnParticles(ParticleTypes.f_123744_, (ServerLevel)this.m_9236_(), this.m_20185_(), this.m_20186_() + 0.3, this.m_20189_());
                  this.m_142687_(RemovalReason.DISCARDED);
               }

               double posX;
               double posZ;
               label48: {
                  posX = this.m_20185_() - this.owner.m_20185_();
                  posZ = this.m_20189_() - this.owner.m_20189_();
                  if (posX > (double)0.0F) {
                     Objects.requireNonNull(this);
                     if (posX > 0.001) {
                        Objects.requireNonNull(this);
                        posX = 0.001;
                        break label48;
                     }
                  }

                  if (posX < (double)0.0F) {
                     Objects.requireNonNull(this);
                     if (posX < -0.001) {
                        Objects.requireNonNull(this);
                        posX = -0.001;
                     }
                  }
               }

               label43: {
                  if (posZ > (double)0.0F) {
                     Objects.requireNonNull(this);
                     if (posZ > 0.001) {
                        Objects.requireNonNull(this);
                        posZ = 0.001;
                        break label43;
                     }
                  }

                  if (posZ < (double)0.0F) {
                     Objects.requireNonNull(this);
                     if (posZ < -0.001) {
                        Objects.requireNonNull(this);
                        posZ = -0.001;
                     }
                  }
               }

               this.m_6478_(MoverType.SELF, new Vec3(-posX, (double)0.0F, -posZ));
               this.m_7618_(Anchor.EYES, this.owner.m_20182_());
               if (this.f_19797_ > 40) {
                  double radius = (double)0.5F;
                  AABB aabb = (new AABB(this.m_20183_())).m_82377_(radius, radius, radius);
                  List<Player> list = new ArrayList();
                  list.addAll(this.m_9236_().m_6443_(Player.class, aabb, EntitySelector.f_20408_.and(EntitySelector.f_20402_)));
                  Iterator var9 = list.iterator();
                  if (var9.hasNext()) {
                     Player player = (Player)var9.next();
                     ItemStack stack = new ItemStack((ItemLike)ModItems.VIVRE_CARD.get());
                     ((VivreCardItem)stack.m_41720_()).setOwner(stack, this.owner);
                     player.m_36356_(stack);
                     this.m_142687_(RemovalReason.DISCARDED);
                  }
               }

            }
         } else {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   protected void m_8097_() {
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128359_("OwnerUUID", this.ownerUUID.toString());
   }

   protected void m_7378_(CompoundTag compound) {
      if (compound.m_128425_("OwnerUUID", 8)) {
         this.setOwner(UUID.fromString(compound.m_128461_("OwnerUUID")));
      }

   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
