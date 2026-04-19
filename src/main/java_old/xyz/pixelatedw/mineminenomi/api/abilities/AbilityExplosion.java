package xyz.pixelatedw.mineminenomi.api.abilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AbilityExplosion extends Explosion {
   private Level level;
   private Entity exploder;
   private @Nullable LivingEntity spawner;
   private Optional<IAbility> ability = Optional.empty();
   private double explosionX;
   private double explosionY;
   private double explosionZ;
   private float explosionSize;
   private final RandomSource random = RandomSource.m_216327_();
   private ParticleEffect<?> particles;
   private ParticleEffect.Details particleDetails;
   private ExplosionDamageCalculator damageCalculator;
   private final List<BlockPos> affectedBlockPositions = Lists.newArrayList();
   private final Map<Player, Vec3> playerKnockbackMap = Maps.newHashMap();
   private List<RemovedBlock> affectedBlockStates = Lists.newArrayList();
   private boolean canStartFireAfterExplosion = false;
   private boolean canDestroyBlocks = true;
   private boolean canDropBlocksAfterExplosion = false;
   private boolean canDamageEntities = true;
   private boolean checkForFaction = true;
   private boolean canDamageOwner = false;
   private boolean canDamageOnce = true;
   private boolean canProduceExplosionSound = true;
   private boolean protectOwnerFromFalling = false;
   private boolean canCauseKnockback = true;
   private boolean addRemovedBlocksToList = false;
   private boolean hasBlockLimit = false;
   private float staticDamage = 0.0F;
   private float staticBlockResistance = 0.0F;
   private int explodedBlocksLimit;
   private int size = 52;
   private int explodedBlocks;
   public ArrayList<Entity> immuneEntities = new ArrayList();
   public ArrayList<Entity> damagedEntities = new ArrayList();
   public IOnBlockDestroyed onBlockDestroyedEvent = (hitPos) -> {
   };
   public IOnEntityHit onEntityHitEvent = (entityx) -> true;

   public AbilityExplosion(Entity entity, @Nullable IAbility ability, double posX, double posY, double posZ, float power) {
      super(entity.m_9236_(), entity, (DamageSource)null, (ExplosionDamageCalculator)null, posX, posY, posZ, power, false, BlockInteraction.DESTROY);
      this.level = entity.m_9236_();
      this.ability = Optional.ofNullable(ability);
      this.exploder = entity;
      this.explosionSize = power;
      this.explosionX = posX;
      this.explosionY = posY;
      this.explosionZ = posZ;
      this.damageCalculator = new EntityBasedExplosionDamageCalculator(entity);
      this.particles = (ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get();
      this.particleDetails = new CommonExplosionParticleEffect.Details((double)power);
   }

   public void setExplosionPos(double posX, double posY, double posZ) {
      this.explosionX = posX;
      this.explosionY = posY;
      this.explosionZ = posZ;
   }

   public void setExplosionSize(float explosionSize) {
      this.explosionSize = explosionSize;
   }

   public void setHasBlockLimit(boolean hasBlockLimit) {
      this.hasBlockLimit = hasBlockLimit;
   }

   public double getStaticDamage() {
      return (double)this.staticDamage;
   }

   public void setStaticDamage(float damage) {
      this.staticDamage = damage;
   }

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public double getStaticBlockResistance() {
      return (double)this.staticBlockResistance;
   }

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public void setStaticBlockResistance(float damage) {
      this.staticBlockResistance = damage;
   }

   public void setDamageOwner(boolean damageOwner) {
      this.canDamageOwner = damageOwner;
   }

   public void setDamageEntities(boolean damageEntities) {
      this.canDamageEntities = damageEntities;
   }

   public void ignoreFactionChecks() {
      this.checkForFaction = false;
   }

   public void setDropBlocksAfterExplosion(boolean canDrop) {
      this.canDropBlocksAfterExplosion = canDrop;
   }

   public void setFireAfterExplosion(boolean hasFire) {
      this.canStartFireAfterExplosion = hasFire;
   }

   public void setDestroyBlocks(boolean canDestroyBlocks) {
      this.canDestroyBlocks = canDestroyBlocks;
   }

   public void setSmokeParticles(@Nullable ParticleEffect<?> particle) {
      this.setSmokeParticles(particle, (ParticleEffect.Details)null);
   }

   public void setSmokeParticles(ParticleEffect<?> commonExplosion, ParticleEffect.Details details) {
      this.particles = commonExplosion;
      this.particleDetails = details;
   }

   public boolean getDamageOnce() {
      return this.canDamageOnce;
   }

   public void setDamageOnce(boolean flag) {
      this.canDamageOnce = flag;
   }

   public void addRemovedBlocksToList() {
      this.addRemovedBlocksToList = true;
   }

   public void setProtectOwnerFromFalling(boolean flag) {
      this.protectOwnerFromFalling = flag;
   }

   public boolean hasSmokeParticles() {
      return this.particles != null;
   }

   public void setExplosionSound(boolean hasSound) {
      this.canProduceExplosionSound = hasSound;
   }

   public void disableExplosionKnockback() {
      this.canCauseKnockback = false;
   }

   public int getExplodedBlocks() {
      return this.explodedBlocks;
   }

   public void setSpawner(@Nullable LivingEntity spawner) {
      this.spawner = spawner;
   }

   public void m_46061_() {
      if (!ForgeEventFactory.onExplosionStart(this.level, this)) {
         this.level.m_220400_(this.exploder, GameEvent.f_157812_, new Vec3(this.explosionX, this.explosionY, this.explosionZ));
         Set<BlockPos> set = Sets.newHashSet();
         if ((float)(this.size + 4) > this.explosionSize) {
            this.size = Math.max((int)(this.explosionSize + 4.0F), 16);
         }

         BlockPos.MutableBlockPos foundBlockPos = new BlockPos.MutableBlockPos();

         for(int j = 0; j < this.size; ++j) {
            for(int k = 0; k < this.size; ++k) {
               for(int l = 0; l < this.size; ++l) {
                  if (j == 0 || j == this.size - 1 || k == 0 || k == this.size - 1 || l == 0 || l == this.size - 1) {
                     double d0 = (double)((float)j / (float)(this.size - 1) * 2.0F - 1.0F);
                     double d1 = (double)((float)k / (float)(this.size - 1) * 2.0F - 1.0F);
                     double d2 = (double)((float)l / (float)(this.size - 1) * 2.0F - 1.0F);
                     double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                     d0 /= d3;
                     d1 /= d3;
                     d2 /= d3;
                     float power = this.explosionSize * (0.7F + this.level.f_46441_.m_188501_() * 0.6F);
                     double eX = this.explosionX;
                     double eY = this.explosionY;
                     double eZ = this.explosionZ;

                     for(float f1 = 0.3F; power > 0.0F; power -= 0.22500001F) {
                        foundBlockPos.m_122169_(eX, eY, eZ);
                        BlockState blockState = this.level.m_8055_(foundBlockPos);
                        FluidState ifluidstate = this.level.m_6425_(foundBlockPos);
                        if (!this.level.m_46739_(foundBlockPos) || !ifluidstate.m_76178_()) {
                           break;
                        }

                        if (!blockState.m_60795_() || !ifluidstate.m_76178_()) {
                           float blockExplosionResistance = Math.max(blockState.getExplosionResistance(this.level, foundBlockPos, this), ifluidstate.getExplosionResistance(this.level, foundBlockPos, this));
                           if (this.exploder != null) {
                              blockExplosionResistance = this.exploder.m_7077_(this, this.level, foundBlockPos, blockState, ifluidstate, blockExplosionResistance);
                           }

                           power -= (blockExplosionResistance + 0.3F) * 0.3F;
                        }

                        if (power > 0.0F && (this.exploder == null || this.exploder.m_7349_(this, this.level, foundBlockPos, blockState, power)) && !blockState.m_204336_(ModTags.Blocks.KAIROSEKI)) {
                           set.add(foundBlockPos.m_7949_());
                        }

                        eX += d0 * (double)0.3F;
                        eY += d1 * (double)0.3F;
                        eZ += d2 * (double)0.3F;
                     }
                  }
               }
            }
         }

         this.affectedBlockPositions.addAll(set);
         this.affectedBlockStates.clear();
         float size = this.explosionSize * 2.0F;
         Vec3 explosionPos = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
         Entity entity = this.getExploder();
         List<Entity> list;
         if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            list = TargetHelper.<Entity>getEntitiesInArea(this.level, living, (double)size, (TargetPredicate)null, Entity.class);
         } else {
            list = TargetHelper.<Entity>getEntitiesInArea(this.level, (LivingEntity)null, BlockPos.m_274561_(this.explosionX, this.explosionY, this.explosionZ), (double)size, (TargetPredicate)null, Entity.class);
         }

         if (this.canDamageOwner) {
            list.add(this.getExploder());
         }

         list = (List)list.stream().filter((e) -> {
            if (e instanceof LivingEntity living) {
               DamageSource source = this.m_46077_();
               if (source.m_7640_() != null) {
                  return living.m_142582_(source.m_7640_());
               }
            }

            return true;
         }).collect(Collectors.toList());
         ForgeEventFactory.onExplosionDetonate(this.level, this, list, (double)size);
         if (this.canDamageEntities) {
            for(int k2 = 0; k2 < list.size(); ++k2) {
               entity = (Entity)list.get(k2);
               if (!entity.m_6128_() && this.onEntityHitEvent.canHit(entity)) {
                  double distance = entity.m_20238_(explosionPos) / (double)(this.explosionSize * this.explosionSize);
                  if (distance <= (double)1.0F) {
                     double xDistance = entity.m_20185_() - this.explosionX;
                     double yDistance = entity.m_20186_() + (double)entity.m_20192_() - this.explosionY;
                     double zDistance = entity.m_20189_() - this.explosionZ;
                     double squareDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance);
                     if (squareDistance != (double)0.0F) {
                        xDistance /= squareDistance;
                        yDistance /= squareDistance;
                        zDistance /= squareDistance;
                        double blockDensity = (double)Explosion.m_46064_(explosionPos, entity);
                        double power = ((double)1.0F - distance) * blockDensity;
                        if (this.staticDamage > 0.0F) {
                           if (entity.m_6469_(this.m_46077_(), this.staticDamage)) {
                              this.damagedEntities.add(entity);
                           }
                        } else {
                           float damage = (float)((power * power + power) / (double)2.0F * (double)7.0F * (double)size + (double)1.0F);
                           if (entity.m_6469_(this.m_46077_(), damage)) {
                              this.damagedEntities.add(entity);
                           }
                        }

                        double blastDamageReduction = power;
                        if (entity instanceof LivingEntity) {
                           blastDamageReduction = ProtectionEnchantment.m_45135_((LivingEntity)entity, power);
                        }

                        if (this.canCauseKnockback) {
                           AbilityHelper.setDeltaMovement(entity, entity.m_20184_().m_82520_(xDistance * blastDamageReduction, yDistance * blastDamageReduction, zDistance * blastDamageReduction));
                           if (entity instanceof Player) {
                              Player playerEntity = (Player)entity;
                              if (!playerEntity.m_5833_() && (!playerEntity.m_7500_() || !playerEntity.m_150110_().f_35935_)) {
                                 this.playerKnockbackMap.put(playerEntity, new Vec3(xDistance * power, yDistance * power, zDistance * power));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (this.canProduceExplosionSound) {
            this.level.m_6263_((Player)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.f_11913_, SoundSource.BLOCKS, 3.0F + this.explosionSize, (1.0F + (this.level.f_46441_.m_188501_() - this.level.f_46441_.m_188501_()) * 0.2F) * 0.7F);
            this.level.m_6263_((Player)null, this.explosionX, this.explosionY, this.explosionZ, (SoundEvent)ModSounds.GENERIC_EXPLOSION_SHORT.get(), SoundSource.BLOCKS, 3.0F + this.explosionSize, (1.0F + (this.level.f_46441_.m_188501_() - this.level.f_46441_.m_188501_()) * 0.2F) * 0.7F);
         }

         if (!this.level.f_46443_ && this.hasSmokeParticles()) {
            WyHelper.spawnParticleEffect(this.particles, this.exploder, this.explosionX, this.explosionY, this.explosionZ, this.particleDetails);
         }

         if (!this.level.f_46443_ && this.canDestroyBlocks && ServerConfig.isAbilityGriefingEnabled()) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList();

            for(BlockPos blockpos : this.affectedBlockPositions) {
               if (this.hasBlockLimit && this.explodedBlocks >= this.explodedBlocksLimit) {
                  break;
               }

               BlockState blockState = this.level.m_8055_(blockpos);
               FluidState fluidState = this.level.m_6425_(blockpos);
               if (!fluidState.m_192917_(Fluids.f_76193_) && !fluidState.m_192917_(Fluids.f_76192_) || ServerConfig.getDestroyWater()) {
                  boolean blockIsKairoseki = blockState.m_204336_(ModTags.Blocks.KAIROSEKI);
                  boolean blockIsRestricted = blockState.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
                  boolean hardBlockRestriction = false;
                  boolean inProtectedAreaFlag = ProtectedAreasData.get((ServerLevel)this.level).isInsideRestrictedArea(blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_());
                  boolean canGrief = NuWorld.canMobGrief(this.getExploder());
                  boolean fallingProtection = true;
                  if (this.protectOwnerFromFalling && this.exploder != null) {
                     fallingProtection = Math.sqrt(this.exploder.m_20275_((double)blockpos.m_123341_(), this.exploder.m_20186_(), (double)blockpos.m_123343_())) > (double)1.5F;
                  }

                  if (!blockState.m_60795_() && !blockIsKairoseki && !blockIsRestricted && !hardBlockRestriction && fallingProtection && canGrief) {
                     Level tileentity = this.level;
                     if (tileentity instanceof ServerLevel) {
                        ServerLevel serverLevel = (ServerLevel)tileentity;
                        if (blockState.canDropFromExplosion(this.level, blockpos, this)) {
                           if (this.canDropBlocksAfterExplosion) {
                              BlockEntity tileentity = blockState.m_155947_() ? this.level.m_7702_(blockpos) : null;
                              LootParams.Builder builder = (new LootParams.Builder(serverLevel)).m_287286_(LootContextParams.f_81460_, Vec3.m_82512_(blockpos)).m_287286_(LootContextParams.f_81463_, ItemStack.f_41583_).m_287289_(LootContextParams.f_81462_, tileentity).m_287289_(LootContextParams.f_81455_, this.exploder);
                              builder.m_287286_(LootContextParams.f_81464_, this.explosionSize);
                              blockState.m_287290_(builder).forEach((itemStack) -> m_46067_(objectarraylist, itemStack, blockpos));
                           }

                           if (this.addRemovedBlocksToList) {
                              this.affectedBlockStates.add(new RemovedBlock(blockpos, blockState));
                           }
                        }
                     }

                     this.onBlockDestroyedEvent.onBlockDestroyed(blockpos);
                     blockState.onBlockExploded(this.level, blockpos, this);
                     ++this.explodedBlocks;
                  }

                  if (this.canStartFireAfterExplosion && this.level.m_8055_(blockpos).m_60795_() && this.level.m_8055_(blockpos.m_7495_()).m_60804_(this.level, blockpos.m_7495_()) && this.random.m_188503_(5) == 0 && !inProtectedAreaFlag) {
                     this.level.m_46597_(blockpos, BaseFireBlock.m_49245_(this.level, blockpos));
                  }
               }
            }
         }

         LivingEntity var36 = this.spawner;
         if (var36 instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)var36;
            if (StructuresHelper.isInsideShip((ServerLevel)this.level, BlockPos.m_274446_(explosionPos))) {
               ModAdvancements.SUBTLE_TWEAKS.trigger(serverPlayer);
            }
         }

      }
   }

   private static void m_46067_(ObjectArrayList<Pair<ItemStack, BlockPos>> drops, ItemStack itemStack, BlockPos pos) {
      int i = drops.size();

      for(int j = 0; j < i; ++j) {
         Pair<ItemStack, BlockPos> pair = (Pair)drops.get(j);
         ItemStack itemstack = (ItemStack)pair.getLeft();
         if (ItemEntity.m_32026_(itemstack, itemStack)) {
            ItemStack itemstack1 = ItemEntity.m_32029_(itemstack, itemStack, 16);
            drops.set(j, Pair.of(itemstack1, (BlockPos)pair.getRight()));
            if (itemStack.m_41619_()) {
               return;
            }
         }
      }

      drops.add(Pair.of(itemStack, pos));
   }

   public Map<Player, Vec3> m_46078_() {
      return this.playerKnockbackMap;
   }

   public List<BlockPos> m_46081_() {
      return this.affectedBlockPositions;
   }

   public List<RemovedBlock> getBlownStates() {
      return this.affectedBlockStates;
   }

   public static record RemovedBlock(BlockPos pos, BlockState state) {
   }

   @FunctionalInterface
   public interface IOnBlockDestroyed {
      void onBlockDestroyed(BlockPos var1);
   }

   @FunctionalInterface
   public interface IOnEntityHit {
      boolean canHit(Entity var1);
   }
}
