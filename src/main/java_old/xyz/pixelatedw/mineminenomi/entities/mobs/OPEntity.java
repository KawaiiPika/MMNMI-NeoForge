package xyz.pixelatedw.mineminenomi.entities.mobs;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.IWithHome;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public abstract class OPEntity extends PathfinderMob implements IEntityAdditionalSpawnData, IGoalMemoriesEntity, RangedAttackMob, IWithHome {
   private static final float SPAWNER_DESPAWN_DISTANCE = 40000.0F;
   private final GoalMemories goalMemories;
   protected ResourceLocation[] textures;
   private IEntityStats entityStatData;
   private ResourceLocation currentTexture;
   private boolean isSpawnerChild;
   private Optional<Vec3> homePosition;
   private boolean isInitialized;

   public OPEntity(EntityType<? extends OPEntity> type, Level world) {
      this(type, world, (ResourceLocation[])null);
   }

   public OPEntity(EntityType<? extends OPEntity> type, Level world, ResourceLocation[] textures) {
      super(type, world);
      this.goalMemories = new GoalMemories();
      this.currentTexture = null;
      this.isSpawnerChild = false;
      this.homePosition = Optional.empty();
      this.textures = textures;
      if (world != null && !world.f_46443_) {
         this.chooseTexture();
         this.entityStatData = (IEntityStats)EntityStatsCapability.get(this).orElseThrow();
         this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)0.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22266_(Attributes.f_22281_).m_22266_(Attributes.f_22283_).m_22266_((Attribute)ForgeMod.ENTITY_REACH.get());
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      spawnData = super.m_6518_(world, difficulty, reason, spawnData, dataTag);
      this.m_21559_(false);
      if (this.getEntityStats().getDoriki() > (double)0.0F) {
         this.f_21364_ = (int)(this.getEntityStats().getDoriki() / (double)100.0F);
      }

      if (dataTag != null && dataTag.m_128441_("isSpawned")) {
         this.isSpawnerChild = true;
      }

      this.homePosition = Optional.of(this.m_20182_());
      return spawnData;
   }

   public boolean m_6785_(double distance) {
      return this.isSpawnedViaSpawner() && distance > (double)40000.0F;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      if (reason == MobSpawnType.SPAWNER) {
         return true;
      } else if (world.m_46791_() == Difficulty.PEACEFUL) {
         return false;
      } else if (world.m_46942_(0.0F) > 0.25F && world.m_46942_(0.0F) < 0.8F) {
         return false;
      } else if (!world.m_45527_(pos)) {
         return false;
      } else if (pos.m_123342_() > 150) {
         return false;
      } else {
         boolean isValidSpawn = world.m_8055_(pos.m_7495_()).m_60643_(world, pos.m_7495_(), type);
         return isValidSpawn;
      }
   }

   public void initEntity() {
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         if (!this.isInitialized) {
            this.initEntity();
            this.m_21153_(this.m_21233_());
            this.isInitialized = true;
         }

         if (this.m_20202_() instanceof Boat && this.m_5448_() != null) {
            this.m_8127_();
         }
      }

      super.m_8119_();
   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
   }

   public boolean canDrownInFluidType(FluidType type) {
      return type != ForgeMod.WATER_TYPE.get() || !this.getEntityStats().isFishman();
   }

   public void setTextures(ResourceLocation[] textures) {
      this.textures = textures;
   }

   public void chooseTexture() {
      this.chooseTexture(this.f_19796_);
   }

   public void chooseTexture(RandomSource random) {
      if (this.textures != null && this.textures.length > 0) {
         int id = random.m_188503_(this.textures.length);
         this.setTexture(this.textures[id]);
      }

   }

   public void m_8024_() {
      this.goalMemories.tick();
   }

   public double m_6049_() {
      return -0.35;
   }

   public ResourceLocation getCurrentTexture() {
      return this.currentTexture;
   }

   protected void setTexture(ResourceLocation id) {
      this.currentTexture = id;
   }

   public boolean isSpawnedViaSpawner() {
      return this.isSpawnerChild;
   }

   public Optional<Vec3> getHomePosition() {
      return this.homePosition;
   }

   public GoalMemories getGoalMemories() {
      return this.goalMemories;
   }

   public void m_6504_(LivingEntity target, float velocity) {
      if (!this.m_6117_() && this.canPerformRangedAttack(target)) {
         Projectile proj = this.getRangedProjectile(target);
         if (proj != null) {
            float inaccuracy = Math.max(0.0F, 6.0F - (float)this.m_9236_().m_46791_().m_19028_() * 2.5F);
            double x = target.m_20185_() - this.m_20185_();
            double y = target.m_20227_(0.3) - proj.m_20186_();
            double z = target.m_20189_() - this.m_20189_();
            double d = Math.sqrt(x * x + z * z);
            proj.m_6686_(x, y + d * 0.05, z, 2.5F, inaccuracy);
            this.m_9236_().m_7967_(proj);
            ModGunItem.playShootSound(this.m_21205_(), this);
         }
      }
   }

   @Nullable
   public Projectile getRangedProjectile(LivingEntity target) {
      return null;
   }

   public boolean canPerformRangedAttack(LivingEntity target) {
      return false;
   }

   public ItemStack getRandomSword(List<Supplier<? extends Item>> list) {
      return this.getRandomSword(this.f_19796_, list);
   }

   public ItemStack getRandomSword(RandomSource random, List<Supplier<? extends Item>> list) {
      return new ItemStack((ItemLike)((Supplier)list.get(random.m_188503_(list.size()))).get());
   }

   public boolean isAboveNormalDifficulty() {
      return this.m_9236_().m_46791_().m_19028_() > Difficulty.NORMAL.m_19028_();
   }

   public IEntityStats getEntityStats() {
      if (this.entityStatData == null) {
         this.entityStatData = (IEntityStats)EntityStatsCapability.get(this).orElseThrow();
      }

      return this.entityStatData;
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      if (this.getCurrentTexture() != null) {
         nbt.m_128359_("texture", this.getCurrentTexture().toString());
      }

   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      if (nbt.m_128441_("texture")) {
         this.setTexture(ResourceLocation.parse(nbt.m_128461_("texture")));
      }

   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.currentTexture != null);
      if (this.currentTexture != null) {
         buffer.m_130085_(this.currentTexture);
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasTexture = buffer.readBoolean();
      if (hasTexture) {
         this.currentTexture = buffer.m_130281_();
      }

   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
