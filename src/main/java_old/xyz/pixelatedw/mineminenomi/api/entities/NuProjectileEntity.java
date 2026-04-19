package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.events.ProjectileShootEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjBlockCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjEntityCollisionBox;

public abstract class NuProjectileEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {
   private int life;
   private int maxLife;
   private float damage;
   private int knockback;
   private float gravity;
   private boolean canPassThroughEntities;
   private boolean canPassThroughBlocks;
   protected DamageSource damageSource;
   protected IDamageSourceHandler damageSourceHandler;
   private Optional<IAbility> parent;
   private SourceElement sourceElement;
   private SourceHakiNature sourceHakiNature;
   private Set<SourceType> sourceType;
   private LivingEntity self;
   private UUID ownerUUID;
   private AABB entityCollisionBox;
   private AABB blockCollisionBox;
   private final Predicate<Entity> canCollideWithEntityPredicate;
   public final TargetPredicate targetsCheck;
   private final PriorityEventPool<IOnHitEntityEvent> onHitEntityEvents;
   private final PriorityEventPool<IOnHitBlockEvent> onHitBlockEvents;
   private final PriorityEventPool<IOnTickEvent> onTickEvents;

   public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
      super(type, world);
      this.life = 0;
      this.maxLife = 64;
      this.damage = 1.0F;
      this.gravity = 0.0F;
      this.canPassThroughEntities = false;
      this.canPassThroughBlocks = false;
      this.parent = Optional.empty();
      this.sourceElement = SourceElement.NONE;
      this.sourceHakiNature = SourceHakiNature.UNKNOWN;
      this.sourceType = new HashSet();
      this.ownerUUID = ModValues.NIL_UUID;
      this.entityCollisionBox = this.getDefaultBoundingBox();
      this.blockCollisionBox = this.getDefaultBoundingBox();
      this.canCollideWithEntityPredicate = (target) -> {
         if (target != this && target != this.getOwner()) {
            if (!(target instanceof ExperienceOrb) && !(target instanceof ItemEntity)) {
               if (target instanceof LivingEntity) {
                  LivingEntity livingTarget = (LivingEntity)target;
                  return this.hasLineOfSight(livingTarget);
               } else {
                  if (target instanceof Projectile) {
                     Projectile projectile = (Projectile)target;
                     if (projectile.m_19749_() != null && projectile.m_19749_() == this.getOwner()) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      };
      this.targetsCheck = (new TargetPredicate()).selector(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(this.canCollideWithEntityPredicate));
      this.onHitEntityEvents = new PriorityEventPool<IOnHitEntityEvent>();
      this.onHitBlockEvents = new PriorityEventPool<IOnHitBlockEvent>();
      this.onTickEvents = new PriorityEventPool<IOnTickEvent>();
      this.setMaxLife(64);
   }

   private NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level world, double x, double y, double z) {
      this(type, world);
      this.m_6034_(x, y, z);
   }

   public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level world, LivingEntity thrower) {
      this(type, world, thrower.m_20185_(), thrower.m_20188_() - (double)0.1F, thrower.m_20189_());
      this.m_5602_(thrower);
      this.damageSource = ModDamageSources.getInstance().projectile(this, thrower);
      this.damageSourceHandler = (IDamageSourceHandler)this.damageSource;
   }

   public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level world, LivingEntity thrower, @Nullable IAbility parent, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      this(type, world, thrower);
      this.m_5602_(thrower);
      this.sourceElement = element;
      this.sourceHakiNature = hakiNature;
      this.sourceType = Sets.newHashSet(types);
      this.damageSource = (DamageSource)(parent != null ? ModDamageSources.getInstance().ability(this, thrower, parent.getCore()) : ModDamageSources.getInstance().projectile(this, thrower));
      this.damageSourceHandler = (IDamageSourceHandler)this.damageSource;
      this.parent = Optional.ofNullable(parent);
   }

   public NuProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level world, LivingEntity thrower, IAbility parent) {
      this(type, world, thrower, parent, parent.getCore().getSourceElement(), parent.getCore().getSourceHakiNature(), parent.getCore().getSourceTypesArray());
   }

   public NuProjectileEntity addEntityHitEvent(int priority, IOnHitEntityEvent event) {
      this.onHitEntityEvents.addEvent(priority, event);
      return this;
   }

   public NuProjectileEntity addBlockHitEvent(int priority, IOnHitBlockEvent event) {
      this.onHitBlockEvents.addEvent(priority, event);
      return this;
   }

   public void clearBlockHitEvents() {
      this.onHitBlockEvents.clearEvents();
   }

   public void clearBlockHitEvents(int priority) {
      this.onHitBlockEvents.clearEvents(priority);
   }

   public NuProjectileEntity addTickEvent(int priority, IOnTickEvent event) {
      this.onTickEvents.addEvent(priority, event);
      return this;
   }

   public boolean m_5829_() {
      return false;
   }

   private AABB getDefaultBoundingBox() {
      AABB def = super.m_142242_();
      return def != null ? def : this.m_6095_().m_20680_().m_20393_(this.m_20182_());
   }

   protected AABB m_142242_() {
      if (this.entityCollisionBox == null) {
         this.entityCollisionBox = this.getDefaultBoundingBox();
      }

      return new AABB(this.m_20185_() - this.entityCollisionBox.m_82362_() / (double)2.0F, this.m_20186_() - this.entityCollisionBox.m_82376_() / (double)2.0F, this.m_20189_() - this.entityCollisionBox.m_82385_() / (double)2.0F, this.m_20185_() + this.entityCollisionBox.m_82362_() / (double)2.0F, this.m_20186_() + this.entityCollisionBox.m_82376_() / (double)2.0F, this.m_20189_() + this.entityCollisionBox.m_82385_() / (double)2.0F);
   }

   public void m_37251_(Entity thrower, float pX, float pY, float pZ, float velocity, float inaccuracy) {
      inaccuracy = Math.max(inaccuracy, 0.0F);
      ProjectileShootEvent event = new ProjectileShootEvent(this, velocity, inaccuracy);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
         float f = -Mth.m_14031_(pY * ((float)Math.PI / 180F)) * Mth.m_14089_(pX * ((float)Math.PI / 180F));
         float f1 = -Mth.m_14031_((pX + pZ) * ((float)Math.PI / 180F));
         float f2 = Mth.m_14089_(pY * ((float)Math.PI / 180F)) * Mth.m_14089_(pX * ((float)Math.PI / 180F));
         this.m_6686_((double)f, (double)f1, (double)f2, velocity, inaccuracy);
      }
   }

   public boolean m_20073_() {
      if (this.m_20069_()) {
         this.m_5841_();
      }

      return false;
   }

   public boolean tickDespawn() {
      if (!this.m_9236_().f_46443_) {
         if (this.getMaxLife() < 0) {
            return false;
         }

         if (this.getLife() <= 0) {
            this.setLife(this.getMaxLife());
            this.m_142687_(RemovalReason.DISCARDED);
            return true;
         }

         this.setLife(this.getLife() - 1);
      }

      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.tickDespawn()) {
            return;
         }

         if (this.shouldRemoveInProtectedArea()) {
            return;
         }

         if (this.damageSource == null || this.damageSourceHandler == null) {
            this.m_146870_();
            return;
         }

         if (!this.m_9236_().isAreaLoaded(this.m_20183_(), 1)) {
            this.m_146870_();
            return;
         }

         if (!this.m_6084_()) {
            return;
         }

         this.checkEntityCollision();
      }

      this.onTickEvents.dispatch((event) -> event.onTick());
   }

   private void checkEntityCollision() {
      AABB bb = this.m_142242_();
      bb = bb.m_82369_(this.m_20184_());
      Set<Entity> parentEntities = (Set)this.m_9236_().getPartEntities().stream().map(PartEntity::getParent).collect(Collectors.toSet());

      for(Entity target : TargetHelper.getEntitiesInArea(this.m_9236_(), this.getOwner(), bb, this.targetsCheck, Entity.class)) {
         if (!parentEntities.contains(target)) {
            this.m_5790_(new EntityHitResult(target));
         }
      }

   }

   public void m_6532_(HitResult hitResult) {
      if (!this.m_9236_().f_46443_) {
         try {
            HitResult.Type type = hitResult.m_6662_();
            if (type == Type.ENTITY) {
               EntityHitResult entityHit = (EntityHitResult)hitResult;
               this.m_5790_(entityHit);
            } else if (type == Type.BLOCK) {
               BlockHitResult blockHit = (BlockHitResult)hitResult;
               this.m_8060_(blockHit);
               BlockPos blockpos = blockHit.m_82425_();
               this.m_9236_().m_220407_(GameEvent.f_157777_, blockpos, Context.m_223719_(this, this.m_9236_().m_8055_(blockpos)));
               if (!this.canPassThroughBlocks) {
                  this.m_142687_(RemovalReason.KILLED);
               }
            }
         } catch (ComponentNotRegisteredException ex) {
            ex.printStackTrace();
            this.m_142687_(RemovalReason.DISCARDED);
         }

      }
   }

   public void onProjectileCollision(NuProjectileEntity self, NuProjectileEntity other) {
      if (self.getOwner() instanceof LivingEntity && other.getOwner() instanceof LivingEntity) {
         float ownerDamage = self.getDamage();
         float targetDamage = other.getDamage();
         boolean isOwnerDamageLarger = ownerDamage >= targetDamage;
         boolean isTargetDamageLarger = targetDamage >= ownerDamage;
         if (isOwnerDamageLarger) {
            other.m_146870_();
         }

         if (isTargetDamageLarger) {
            self.m_146870_();
         }

      }
   }

   public boolean shouldRemoveInProtectedArea() {
      Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)this.m_9236_()).getProtectedArea((int)this.m_20185_(), (int)this.m_20186_(), (int)this.m_20189_());
      if (area.isPresent()) {
         AbilityCore<?> core = (AbilityCore)this.parent.map((abl) -> abl.getCore()).orElse((Object)null);
         if (core == null) {
            return false;
         }

         if (!((ProtectedArea)area.get()).canUseAbility(core)) {
            this.m_142687_(RemovalReason.DISCARDED);
            return true;
         }
      }

      return false;
   }

   public boolean canHurtInProtectedArea(EntityHitResult result) {
      Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)this.m_9236_()).getProtectedArea((int)this.m_20185_(), (int)this.m_20186_(), (int)this.m_20189_());
      if (area.isPresent()) {
         if (result.m_82443_() != null) {
            if (result.m_82443_() instanceof Player && ((ProtectedArea)area.get()).canHurtPlayers()) {
               return true;
            }

            if (!(result.m_82443_() instanceof Player) && ((ProtectedArea)area.get()).canHurtEntities()) {
               return true;
            }
         }

         AbilityCore<?> parent = (AbilityCore)this.parent.map((abl) -> abl.getCore()).orElse((Object)null);
         if (parent == null) {
            return false;
         } else {
            boolean isWhitelisted = ServerConfig.isAbilityProtectionWhitelisted(parent);
            return isWhitelisted;
         }
      } else {
         return true;
      }
   }

   public void m_5790_(EntityHitResult hitResult) {
      if (this.canHurtInProtectedArea(hitResult) && this.damageSource != null && this.damageSourceHandler != null) {
         Entity var3 = hitResult.m_82443_();
         if (var3 instanceof NuProjectileEntity) {
            NuProjectileEntity other = (NuProjectileEntity)var3;
            this.onProjectileCollision(this, other);
         }

         this.triggerEntityHitEvents(hitResult);
         this.m_9236_().m_214171_(GameEvent.f_157777_, hitResult.m_82450_(), Context.m_223719_(this, (BlockState)null));
         var3 = hitResult.m_82443_();
         if (var3 instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)var3;
            this.damageSource = (DamageSource)(this.parent.isPresent() ? ModDamageSources.getInstance().ability(this, this.getOwner(), ((IAbility)this.parent.get()).getCore()) : ModDamageSources.getInstance().projectile(this, this.getOwner()));
            this.damageSourceHandler = (IDamageSourceHandler)this.damageSource;
            livingTarget.m_6469_(this.damageSource, this.damage);
            if (this.knockback > 0) {
               double d0 = Math.max((double)0.0F, (double)1.0F - livingTarget.m_21133_(Attributes.f_22278_));
               Vec3 vec3 = this.m_20184_().m_82542_((double)1.0F, (double)0.0F, (double)1.0F).m_82541_().m_82490_((double)this.knockback * 0.6 * d0);
               if (vec3.m_82556_() > (double)0.0F) {
                  livingTarget.m_5997_(vec3.f_82479_, 0.1, vec3.f_82481_);
               }
            }
         }

         this.defaultOnHitBlock(hitResult);
         if (!this.canPassThroughEntities && this.f_19797_ > 2) {
            this.m_142687_(RemovalReason.KILLED);
         }

      } else {
         this.m_6074_();
      }
   }

   public void defaultOnHitBlock(EntityHitResult hitResult) {
      Direction dir = Direction.m_122366_(hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
      this.m_8060_(new BlockHitResult(hitResult.m_82450_(), dir, hitResult.m_82443_().m_20183_(), false));
   }

   public void m_8060_(BlockHitResult hitResult) {
      this.triggerBlockHitEvents(hitResult);
      super.m_8060_(hitResult);
   }

   public void triggerEntityHitEvents(EntityHitResult hitResult) {
      if (this.onHitEntityEvents.countEventsInPool() > 0L) {
         this.onHitEntityEvents.dispatch((event) -> event.onHitEntity(hitResult));
      }

   }

   public void triggerBlockHitEvents(BlockHitResult hitResult) {
      if (this.onHitBlockEvents.countEventsInPool() > 0L) {
         this.onHitBlockEvents.dispatch((event) -> event.onHitBlock(hitResult));
      }

   }

   public boolean hasLineOfSight(Entity target) {
      if (target.m_9236_() != this.m_9236_()) {
         return false;
      } else {
         Vec3 startVec = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
         Vec3 targetVec = new Vec3(target.m_20185_(), target.m_20188_(), target.m_20189_());
         double xSize = this.getEntityCollisionBox().m_82362_();
         double ySize = this.getEntityCollisionBox().m_82376_();
         double zSize = this.getEntityCollisionBox().m_82385_();
         double isWithinRange = Math.sqrt(xSize * xSize + ySize * ySize + zSize * zSize);
         if (targetVec.m_82554_(startVec) > isWithinRange) {
            return false;
         } else {
            return this.m_9236_().m_45547_(new ClipContext(startVec, targetVec, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
         }
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.getOwner() != null;
      buffer.writeBoolean(hasOwner);
      if (this.self != null && hasOwner) {
         buffer.writeInt(this.self.m_19879_());
      }

      buffer.writeInt(this.maxLife);
      buffer.writeInt(this.sourceHakiNature.ordinal());
      buffer.writeInt(this.sourceElement.ordinal());
      int sourceTypes = this.sourceType.size();
      buffer.writeInt(sourceTypes);

      for(SourceType type : this.sourceType) {
         buffer.writeInt(type.ordinal());
      }

      buffer.writeDouble(this.entityCollisionBox.m_82362_());
      buffer.writeDouble(this.entityCollisionBox.m_82376_());
      buffer.writeDouble(this.entityCollisionBox.m_82385_());
      buffer.writeDouble(this.blockCollisionBox.m_82362_());
      buffer.writeDouble(this.blockCollisionBox.m_82376_());
      buffer.writeDouble(this.blockCollisionBox.m_82385_());
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.self = owner;
            this.m_5602_(owner);
         }
      }

      this.setMaxLife(buffer.readInt());
      this.sourceHakiNature = SourceHakiNature.values()[buffer.readInt()];
      this.sourceElement = SourceElement.values()[buffer.readInt()];
      int sourceTypes = buffer.readInt();
      this.sourceType = new HashSet();

      for(int i = 0; i < sourceTypes; ++i) {
         this.sourceType.add(SourceType.values()[buffer.readInt()]);
      }

      this.setEntityCollisionSize(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
      this.setBlockCollisionSize(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
   }

   public void m_5602_(@Nullable Entity owner) {
      super.m_5602_(owner);
      if (owner != null) {
         this.ownerUUID = owner.m_20148_();
      }

   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.ownerUUID != null && this.self == null && this.ownerUUID != null) {
         Level var2 = this.m_9236_();
         if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            Entity entity = serverLevel.m_8791_(this.ownerUUID);
            if (entity instanceof LivingEntity) {
               this.self = (LivingEntity)entity;
            }
         }
      }

      return (LivingEntity)super.m_19749_();
   }

   protected void m_8097_() {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected float m_6380_(Pose pose, EntityDimensions dimensions) {
      return 0.0F;
   }

   public boolean m_6128_() {
      return true;
   }

   protected float m_7139_() {
      return this.gravity;
   }

   public void setGravity(float gravity) {
      this.gravity = gravity;
   }

   public int getLife() {
      return this.life;
   }

   public void setLife(int life) {
      this.life = life;
   }

   public int getMaxLife() {
      return this.maxLife;
   }

   public void setMaxLife(int life) {
      this.maxLife = life;
      this.setLife(this.maxLife);
   }

   public boolean isPhysical() {
      return this.sourceType.contains(SourceType.PHYSICAL);
   }

   public void setPhysical() {
      if (!this.sourceType.contains(SourceType.PHYSICAL)) {
         this.sourceType.add(SourceType.PHYSICAL);
         this.sourceType.remove(SourceType.UNKNOWN);
      }
   }

   public void setFist() {
      if (!this.sourceType.contains(SourceType.FIST)) {
         this.sourceType.add(SourceType.FIST);
         this.sourceType.remove(SourceType.UNKNOWN);
      }
   }

   public void setProjectile() {
      if (!this.sourceType.contains(SourceType.PROJECTILE)) {
         this.sourceType.add(SourceType.PROJECTILE);
         this.sourceType.remove(SourceType.UNKNOWN);
      }
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public void setArmorPiercing(float piercing) {
      if (!this.m_9236_().f_46443_) {
         this.damageSourceHandler.addAbilityPiercing(piercing, (AbilityCore)this.getParent().map((abl) -> abl.getCore()).orElse((Object)null));
      }
   }

   public float getDamage() {
      return this.damage;
   }

   public void setKnockback(int knockback) {
      this.knockback = knockback;
   }

   public DamageSource getDamageSource() {
      return this.damageSource;
   }

   public SourceElement getSourceElement() {
      return this.sourceElement;
   }

   public SourceHakiNature getSourceHakiNature() {
      return this.sourceHakiNature;
   }

   public Set<SourceType> getSourceTypes() {
      return this.sourceType;
   }

   public void setPassThroughEntities() {
      this.setPassThroughEntities(true);
   }

   public void setPassThroughEntities(boolean pass) {
      this.canPassThroughEntities = pass;
   }

   public boolean canPassThroughEntities() {
      return this.canPassThroughEntities;
   }

   public void setPassThroughBlocks() {
      this.setPassThroughBlocks(true);
   }

   public void setPassThroughBlocks(boolean pass) {
      this.canPassThroughBlocks = pass;
   }

   public boolean canPassThroughBlocks() {
      return this.canPassThroughBlocks;
   }

   public AABB getBlockCollisionBox() {
      return this.blockCollisionBox;
   }

   public AABB getEntityCollisionBox() {
      return this.entityCollisionBox;
   }

   public void setEntityCollisionSize(double val) {
      this.setEntityCollisionSize(val, val, val);
   }

   public void setEntityCollisionSize(double x, double y, double z) {
      this.entityCollisionBox = new AABB(-x / (double)2.0F, -y / (double)2.0F, -z / (double)2.0F, x / (double)2.0F, y / (double)2.0F, z / (double)2.0F);
   }

   public void setEntityCollisionSizeAndUpdate(double val) {
      this.setEntityCollisionSizeAndUpdate(val, val, val);
   }

   public void setEntityCollisionSizeAndUpdate(double x, double y, double z) {
      this.setEntityCollisionSize(x, y, z);
      if (!this.m_9236_().f_46443_ && this.getOwner() != null) {
         ModNetwork.sendToAllTracking(new SUpdateProjEntityCollisionBox(this.m_19879_(), this.entityCollisionBox.m_82362_(), this.entityCollisionBox.m_82376_(), this.entityCollisionBox.m_82385_()), this);
      }

   }

   public void setBlockCollisionSize(double val) {
      this.setBlockCollisionSize(val, val, val);
   }

   public void setBlockCollisionSize(double x, double y, double z) {
      this.blockCollisionBox = new AABB(-x / (double)2.0F, -y / (double)2.0F, -z / (double)2.0F, x / (double)2.0F, y / (double)2.0F, z / (double)2.0F);
   }

   public void setBlockCollisionSizeAndUpdate(double val) {
      this.setBlockCollisionSizeAndUpdate(val, val, val);
   }

   public void setBlockCollisionSizeAndUpdate(double x, double y, double z) {
      this.setBlockCollisionSize(x, y, z);
      if (!this.m_9236_().f_46443_ && this.getOwner() != null) {
         ModNetwork.sendToAllTracking(new SUpdateProjBlockCollisionBox(super.m_19879_(), this.blockCollisionBox.m_82362_(), this.blockCollisionBox.m_82376_(), this.blockCollisionBox.m_82385_()), this);
      }

   }

   public boolean isAffectedByHardening() {
      return this.getHakiNature() == SourceHakiNature.HARDENING || this.getHakiNature() == SourceHakiNature.SPECIAL;
   }

   public boolean isAffectedByImbuing() {
      return this.getHakiNature() == SourceHakiNature.IMBUING || this.getHakiNature() == SourceHakiNature.SPECIAL;
   }

   public SourceHakiNature getHakiNature() {
      return this.sourceHakiNature;
   }

   public void setUnavoidable() {
      this.damageSourceHandler.setUnavoidable();
   }

   public Optional<IAbility> getParent() {
      return this.parent;
   }

   @FunctionalInterface
   public interface IOnHitBlockEvent {
      void onHitBlock(BlockHitResult var1);
   }

   @FunctionalInterface
   public interface IOnHitEntityEvent {
      void onHitEntity(EntityHitResult var1);
   }

   @FunctionalInterface
   public interface IOnTickEvent {
      void onTick();
   }
}
