package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class WanderingDugongEntity extends AbstractDugongEntity implements IEntityAdditionalSpawnData {
   private int headScarId = 0;
   private int chestScarId = 0;
   private int armsScarId = 0;
   private int tailScarId = 0;

   public WanderingDugongEntity(EntityType<? extends WanderingDugongEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.headScarId = this.f_19796_.m_188503_(MobsHelper.DUGONG_HEAD_SCARS_TEXTURES.length);
         this.chestScarId = this.f_19796_.m_188503_(MobsHelper.DUGONG_CHEST_SCARS_TEXTURES.length);
         this.armsScarId = this.f_19796_.m_188499_() ? this.f_19796_.m_188503_(MobsHelper.DUGONG_ARMS_SCARS_TEXTURES.length) : -1;
         this.tailScarId = this.f_19796_.m_188499_() ? this.f_19796_.m_188503_(MobsHelper.DUGONG_TAIL_SCARS_TEXTURES.length) : -1;
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(45 + this.m_217043_().m_188503_(10)));
            props.setKenbunshokuHakiExp((float)(15 + this.m_217043_().m_188503_(10)));
         });
         this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.2F, true));
         Predicate<LivingEntity> dugongCheck = (target) -> {
            if (target instanceof AbstractDugongEntity) {
               return target instanceof WanderingDugongEntity;
            } else {
               return true;
            }
         };
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, OPEntity.class, true, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, PathfinderMob.class, 10, true, true, dugongCheck));
         WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 3);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()), 2);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 2);
         goals.pickN(this.m_217043_(), 2).forEach((goal) -> this.f_21345_.m_25352_(2, (Goal)goal.get()));
         MobsHelper.getBasicHakiAbilities(this, 80).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22281_, (double)10.0F).m_22268_(Attributes.f_22277_, (double)40.0F).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(60, 100)).m_22268_(Attributes.f_22284_, WyHelper.randomWithRange(4, 8));
   }

   public void m_21828_(Player player) {
      if (this.f_19796_.m_188503_(64) == 0) {
         super.m_21828_(player);
      }

   }

   public int getHeadScarId() {
      return this.headScarId;
   }

   public int getChestScarId() {
      return this.chestScarId;
   }

   public int getArmsScarId() {
      return this.armsScarId;
   }

   public int getTailScarId() {
      return this.tailScarId;
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob ageable) {
      return null;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.headScarId);
      buffer.writeInt(this.chestScarId);
      buffer.writeInt(this.armsScarId);
      buffer.writeInt(this.tailScarId);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.headScarId = buffer.readInt();
      this.chestScarId = buffer.readInt();
      this.armsScarId = buffer.readInt();
      this.tailScarId = buffer.readInt();
   }

   public static boolean checkSpawnRules(EntityType<WanderingDugongEntity> entity, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      BlockPos worldSpawn = new BlockPos(world.m_6106_().m_6789_(), world.m_6106_().m_6527_(), world.m_6106_().m_6526_());
      return pos.m_123314_(worldSpawn, (double)512.0F) ? false : Mob.m_217057_(entity, world, spawnType, pos, random);
   }
}
