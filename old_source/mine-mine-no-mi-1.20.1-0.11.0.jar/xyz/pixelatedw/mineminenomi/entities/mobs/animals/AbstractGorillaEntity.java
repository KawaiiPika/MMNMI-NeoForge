package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsBase;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public abstract class AbstractGorillaEntity extends PathfinderMob implements IEntityAdditionalSpawnData {
   private float size;
   private static final float MIN_SIZE = 2.5F;
   private static final float MAX_SIZE = 3.5F;

   protected AbstractGorillaEntity(EntityType<? extends AbstractGorillaEntity> entity, Level world) {
      super(entity, world);
      if (world != null && !world.f_46443_) {
         this.m_8061_(EquipmentSlot.MAINHAND, this.getRandomAxe(MobsHelper.GORILLA_AXES));
         double attackDamage = this.m_21051_(Attributes.f_22281_).m_22115_();
         double maxHealth = this.m_21051_(Attributes.f_22276_).m_22115_();
         this.m_21051_(Attributes.f_22281_).m_22100_(attackDamage + (double)(this.getSize() > 1.0F ? this.getSize() : 0.0F));
         this.m_21051_(Attributes.f_22276_).m_22100_(maxHealth + (double)(this.getSize() * 10.0F));
         this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)(0.0F + this.getSize() * 0.5F));
         this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(this).orElse(new EntityStatsBase(this));
         props.setDoriki((double)1500.0F + WyHelper.randomWithRange((RandomSource)world.m_213780_(), 0, 500));
         props.setBelly(0L);
         this.f_21345_.m_25352_(0, new FloatGoal(this));
         this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
         if (this.f_19796_.m_188501_() < 0.6F) {
            this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         }

         this.f_21345_.m_25352_(0, new ImprovedMeleeAttackGoal(this, (double)1.25F, true));
         if (this.f_19796_.m_188499_()) {
            this.f_21345_.m_25352_(2, new JumpAbilityWrapperGoal(this, (AbilityCore)SlamAbility.INSTANCE.get()));
         } else {
            this.f_21345_.m_25352_(2, new JumpAbilityWrapperGoal(this, (AbilityCore)BellyFlopAbility.INSTANCE.get()));
         }

         this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
         this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
         this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, OPEntity.class, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Villager.class, true));
         this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Monster.class, true));
         this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, HumandrillEntity.class, true));
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.size = Math.min(2.5F + this.f_19796_.m_188501_(), 3.5F);
   }

   public EntityDimensions m_6972_(Pose pose) {
      float scale = this.getSize() / 1.3F;
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(scale);
      return newSize;
   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("size", this.size);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.size = compound.m_128457_("size");
   }

   public ItemStack getRandomAxe(List<Supplier<? extends Item>> list) {
      return new ItemStack((ItemLike)((Supplier)list.get(this.f_19796_.m_188503_(list.size()))).get());
   }

   public float getSize() {
      return this.size;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12280_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12187_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12180_;
   }

   protected void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_12284_, 0.15F, 1.5F);
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public boolean m_6785_(double p_27598_) {
      return false;
   }

   public float m_6100_() {
      return this.m_6162_() ? 0.5F + this.f_19796_.m_188501_() / 2.0F : 0.2F + this.f_19796_.m_188501_() / 3.0F;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.size);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.size = buffer.readFloat();
      this.m_6210_();
   }
}
