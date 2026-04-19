package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.EatNearbyFishGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class PandaSharkEntity extends WaterAnimal implements IEntityAdditionalSpawnData {
   private float size = 1.0F;

   public PandaSharkEntity(EntityType<? extends PandaSharkEntity> type, Level world) {
      super(type, world);
      this.f_21342_ = new FishSwimMoveController(this);
      this.f_21365_ = new LandFishLookController(this, 10);
      if (world != null && !world.f_46443_) {
         this.size = 1.0F + this.f_19796_.m_188501_() * 1.5F;
         double reach = (double)((1.0F - this.size) * 30.0F);
         this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_(-reach);
         this.m_21051_(Attributes.f_22276_).m_22100_(this.m_21133_(Attributes.f_22276_) * Math.max((double)this.size, (double)0.5F));
         this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new TryFindWaterGoal(this));
      this.f_21345_.m_25352_(0, new AvoidBlockGoal(this, ModTags.Blocks.KAIROSEKI));
      this.f_21345_.m_25352_(0, new AvoidCoatedBoatGoal(this));
      this.f_21345_.m_25352_(0, new EatNearbyFishGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.8F, true));
      this.f_21345_.m_25352_(1, new RandomSwimmingGoal(this, (double)1.0F, 10));
      this.f_21345_.m_25352_(1, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(2, new LookAtPlayerGoal(this, Player.class, 12.0F));
      this.f_21345_.m_25352_(2, new LookAtPlayerGoal(this, Turtle.class, 12.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22266_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22268_(Attributes.f_22279_, (double)1.3F).m_22268_(Attributes.f_22277_, (double)55.0F).m_22268_(Attributes.f_22281_, (double)5.0F).m_22268_(Attributes.f_22276_, (double)20.0F);
   }

   public EntityDimensions m_6972_(Pose pose) {
      float fishScale = this.getSize();
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(fishScale);
      return newSize;
   }

   public float getSize() {
      return this.size;
   }

   public int m_8132_() {
      return 1;
   }

   public int m_8085_() {
      return 1;
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6573_(Player pPlayer) {
      return true;
   }

   protected PathNavigation m_6037_(Level level) {
      return (PathNavigation)(WyHelper.isAprilFirst() ? super.m_6037_(level) : new WaterBoundPathNavigation(this, level));
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

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128350_("size", this.size);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.size = nbt.m_128457_("size");
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

   public static boolean checkSpawnRules(EntityType<PandaSharkEntity> entity, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return pos.m_123342_() >= world.m_5736_() - 10 ? false : world.m_6425_(pos).m_205070_(FluidTags.f_13131_);
   }
}
