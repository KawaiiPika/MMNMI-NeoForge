package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.TailSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.EatNearbyFishGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class SeaCowEntity extends TamableAnimal implements IEntityAdditionalSpawnData {
   private static final UUID TAIL_SPIN_RANGE_BONUS = UUID.fromString("3da76e8f-b539-4a87-b764-8edab430cd77");
   private static final AttributeModifier SWIM_SPEED_MOD;
   private TargetPredicate tailSpinAOECheck;
   private float size;
   private static final float MIN_SIZE = 2.0F;
   private static final float MAX_SIZE = 3.5F;
   private final SeaCowPartEntity head = new SeaCowPartEntity(this, "head", 2.5F, 2.5F);
   private final SeaCowPartEntity tail = new SeaCowPartEntity(this, "tail", 3.0F, 3.0F);
   private final SeaCowPartEntity[] subEntities;
   private MoveControl groundMovementController;
   private MoveControl waterMovementController;
   private LookControl groundLookControl;
   private LookControl waterLookControl;
   private PathNavigation groundNavigator;
   private PathNavigation waterNavigator;
   private final NPCPhaseManager phaseManager = new NPCPhaseManager(this);
   private NPCPhase<SeaCowEntity> groundPhase;
   private NPCPhase<SeaCowEntity> waterPhase;
   private DashAbilityWrapperGoal<TackleAbility> tackleWrapper;

   public SeaCowEntity(EntityType<? extends SeaCowEntity> type, Level world) {
      super(type, world);
      this.subEntities = new SeaCowPartEntity[]{this.head, this.tail};
      this.tailSpinAOECheck = TargetPredicate.DEFAULT_AREA_CHECK.selector((entity) -> {
         if (this.m_5448_() != null && this.m_5448_() == entity) {
            return true;
         } else {
            return !(entity instanceof YagaraBullEntity) && !(entity instanceof SeaCowEntity) && !(entity instanceof PandaSharkEntity) && !(entity instanceof Dolphin);
         }
      });
      this.groundMovementController = new MoveControl(this);
      this.waterMovementController = new FishSwimMoveController(this);
      this.groundLookControl = new LookControl(this);
      this.waterLookControl = new LandFishLookController(this, 10);
      this.groundNavigator = new GroundPathNavigation(this, world);
      this.waterNavigator = new WaterBoundPathNavigation(this, world);
      this.m_20234_(f_19843_.getAndAdd(this.subEntities.length + 1) + 1);
      if (world != null && !world.f_46443_) {
         this.size = Math.min(2.0F + this.f_19796_.m_188501_(), 3.5F);
         this.groundPhase = new SimplePhase<SeaCowEntity>("Ground Phase", this, this::startGroundPhaseEvent);
         this.waterPhase = new SimplePhase<SeaCowEntity>("Water Phase", this, this::startWaterPhaseEvent);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)4.0F);
         CloseMeleeAbilityWrapperGoal<TailSpinAbility> tailSpinWrapper = new CloseMeleeAbilityWrapperGoal<TailSpinAbility>(this, (AbilityCore)TailSpinAbility.INSTANCE.get());
         ((TailSpinAbility)tailSpinWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.RANGE.get()).ifPresent((comp) -> {
            comp.setAreaCheck(this.tailSpinAOECheck);
            comp.getBonusManager().addBonus(TAIL_SPIN_RANGE_BONUS, "Tail Spin Range Bonus", BonusOperation.MUL, 4.0F);
         });
         this.tackleWrapper = new DashAbilityWrapperGoal<TackleAbility>(this, (AbilityCore)TackleAbility.INSTANCE.get());
         this.f_21345_.m_25352_(0, new AvoidBlockGoal(this, ModTags.Blocks.KAIROSEKI));
         this.f_21345_.m_25352_(0, new AvoidCoatedBoatGoal(this));
         this.f_21345_.m_25352_(0, new ImprovedMeleeAttackGoal(this, (double)1.25F, true));
         this.f_21345_.m_25352_(0, new EatNearbyFishGoal(this));
         this.f_21345_.m_25352_(1, new BreedGoal(this, (double)1.0F));
         this.f_21345_.m_25352_(2, tailSpinWrapper);
         this.f_21345_.m_25352_(2, this.tackleWrapper);
         this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
         this.groundPhase.addGoal(2, new JumpAbilityWrapperGoal(this, (AbilityCore)BellyFlopAbility.INSTANCE.get()));
         this.groundPhase.addGoal(4, new RandomStrollGoal(this, 0.8));
         this.waterPhase.addGoal(0, new TryFindWaterGoal(this));
         this.waterPhase.addGoal(4, new RandomSwimmingGoal(this, (double)1.0F, 10));
         this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
         this.phaseManager.setPhase(this.groundPhase);
      }

   }

   public void m_20234_(int pId) {
      super.m_20234_(pId);

      for(int i = 0; i < this.subEntities.length; ++i) {
         this.subEntities[i].m_20234_(pId + i + 1);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22266_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22278_, (double)0.7F).m_22268_(Attributes.f_22279_, (double)0.27F).m_22268_(Attributes.f_22277_, (double)70.0F).m_22268_(Attributes.f_22276_, (double)100.0F);
   }

   public void m_8107_() {
      super.m_8107_();
      Vec3[] avector3d = new Vec3[this.subEntities.length];

      for(int j = 0; j < this.subEntities.length; ++j) {
         avector3d[j] = new Vec3(this.subEntities[j].m_20185_(), this.subEntities[j].m_20186_(), this.subEntities[j].m_20189_());
      }

      float headRot = Mth.m_14179_(1.0F, this.f_20885_, this.f_20883_);
      float rot = headRot * ((float)Math.PI / 180F);
      float x = Mth.m_14031_(rot);
      float z = Mth.m_14089_(rot);
      this.tickPart(this.head, (double)(-x * 3.5F), (double)1.5F, (double)(z * 3.5F));
      this.tickPart(this.tail, (double)(x * 4.0F), (double)-0.25F, (double)(-z * 4.0F));

      for(int l = 0; l < this.subEntities.length; ++l) {
         this.subEntities[l].f_19854_ = avector3d[l].f_82479_;
         this.subEntities[l].f_19855_ = avector3d[l].f_82480_;
         this.subEntities[l].f_19856_ = avector3d[l].f_82481_;
         this.subEntities[l].f_19790_ = avector3d[l].f_82479_;
         this.subEntities[l].f_19791_ = avector3d[l].f_82480_;
         this.subEntities[l].f_19792_ = avector3d[l].f_82481_;
      }

      if (!this.m_9236_().f_46443_ && this.m_6084_()) {
         if (this.m_20069_()) {
            this.phaseManager.setPhase(this.waterPhase);
         } else {
            this.phaseManager.setPhase(this.groundPhase);
         }
      }

   }

   public void m_8024_() {
      this.phaseManager.tick();
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11830_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_11832_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11831_;
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public float m_6100_() {
      return this.m_6162_() ? (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2F + 1.0F : (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2F + 0.5F;
   }

   protected PathNavigation m_6037_(Level level) {
      return new WaterBoundPathNavigation(this, level);
   }

   public void m_7023_(Vec3 moveVector) {
      if (this.m_21515_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), moveVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82490_(0.9));
         if (this.m_5448_() == null) {
            AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)0.0F, -0.005, (double)0.0F));
         }
      } else {
         super.m_7023_(moveVector);
      }

   }

   public int m_8132_() {
      return this.phaseManager.getCurrentPhase() != null && this.phaseManager.getCurrentPhase().equals(this.waterPhase) ? 1 : super.m_8132_();
   }

   public int m_8085_() {
      return this.phaseManager.getCurrentPhase() != null && this.phaseManager.getCurrentPhase().equals(this.waterPhase) ? 1 : super.m_8085_();
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6063_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return super.m_6469_(source, amount);
   }

   public boolean hurt(SeaCowPartEntity part, DamageSource source, float amount) {
      if (!this.m_9236_().f_46443_ && part.equals(this.head) && ((TackleAbility)this.tackleWrapper.getAbility()).isContinuous()) {
         amount *= 1.5F;
         ((TackleAbility)this.tackleWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.stopContinuity(this));
      }

      return this.m_6469_(source, amount);
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public EntityDimensions m_6972_(Pose pose) {
      float scale = this.getSize() * (this.m_146764_() < 0 ? 0.5F : 1.0F);
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(scale);
      return newSize;
   }

   private void tickPart(SeaCowPartEntity part, double offsetX, double offsetY, double offsetZ) {
      part.m_6034_(this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("size", this.size);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.size = compound.m_128457_("size");
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.size);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.size = buffer.readFloat();
      this.m_6210_();
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public float getSize() {
      return this.size;
   }

   public boolean isMultipartEntity() {
      return true;
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.subEntities;
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      SeaCowEntity offspring = (SeaCowEntity)((EntityType)ModMobs.SEA_COW.get()).m_20615_(world);
      return offspring;
   }

   private void startWaterPhaseEvent(SeaCowEntity entity) {
      this.f_21342_ = this.waterMovementController;
      this.f_21365_ = this.waterLookControl;
      this.f_21344_.m_26573_();
      this.f_21344_ = this.waterNavigator;
      AttributeInstance attr = this.m_21051_(Attributes.f_22279_);
      if (attr != null && !attr.m_22109_(SWIM_SPEED_MOD)) {
         attr.m_22118_(SWIM_SPEED_MOD);
      }

   }

   private void startGroundPhaseEvent(SeaCowEntity entity) {
      this.f_21342_ = this.groundMovementController;
      this.f_21365_ = this.groundLookControl;
      this.f_21344_.m_26573_();
      this.f_21344_ = this.groundNavigator;
      AttributeInstance attr = this.m_21051_(Attributes.f_22279_);
      if (attr != null) {
         attr.m_22130_(SWIM_SPEED_MOD);
      }

   }

   public static boolean checkSpawnRules(EntityType<SeaCowEntity> entity, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return world.m_8055_(pos.m_7495_()).m_60819_().m_205070_(FluidTags.f_13131_);
   }

   static {
      SWIM_SPEED_MOD = new AttributeModifier(UUID.fromString("7da9447a-362c-4c83-bdde-32e07a939b7e"), "Swim Speed Bonus", (double)1.0F, Operation.ADDITION);
   }

   private class SeaCowPartEntity extends PartEntity<SeaCowEntity> {
      public final SeaCowEntity parentMob;
      public final String name;
      private final EntityDimensions size;

      public SeaCowPartEntity(SeaCowEntity parent, String name, float width, float height) {
         super(parent);
         this.size = EntityDimensions.m_20395_(width, height);
         this.m_6210_();
         this.parentMob = parent;
         this.name = name;
      }

      protected void m_8097_() {
      }

      protected void m_7378_(CompoundTag pCompound) {
      }

      protected void m_7380_(CompoundTag pCompound) {
      }

      public boolean m_6087_() {
         return true;
      }

      public boolean m_6469_(DamageSource source, float amount) {
         return this.m_6673_(source) ? false : this.parentMob.hurt(this, source, amount);
      }

      public boolean m_7306_(Entity entity) {
         return this == entity || this.parentMob == entity;
      }

      public EntityDimensions m_6972_(Pose pPose) {
         return this.size;
      }
   }
}
