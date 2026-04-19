package xyz.pixelatedw.mineminenomi.entities.mobs.worldgov;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DespawnAfterTimeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunTowardsLocationGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class CelestialDragonEntity extends OPEntity {
   private static final byte MOUNT_EVENT = 100;
   private static final byte UNMOUNT_EVENT = 101;
   private Color hairColor;
   private boolean ridingSlave;
   private boolean secondChance;
   private boolean gotHitOnce;

   public CelestialDragonEntity(EntityType<CelestialDragonEntity> type, Level world) {
      super(type, world, MobsHelper.CELESTIAL_DRAGONS_TEXTURES);
      this.hairColor = Color.BLACK;
      this.ridingSlave = true;
      this.secondChance = false;
      this.gotHitOnce = false;
   }

   public void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.6));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Villager.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      EntityStatsCapability.get(this).ifPresent((props) -> props.setFaction((Faction)ModFactions.WORLD_GOVERNMENT.get()));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)30.0F).m_22268_(Attributes.f_22279_, (double)0.2F).m_22268_(Attributes.f_22281_, 0.1).m_22268_(Attributes.f_22276_, (double)20.0F).m_22268_(Attributes.f_22284_, (double)1.0F);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      spawnData = super.m_6518_(world, difficulty, reason, spawnData, dataTag);
      float r = this.m_217043_().m_188501_();
      float g = this.m_217043_().m_188501_();
      float b = this.m_217043_().m_188501_();
      Color color = new Color(r, g, b);
      this.hairColor = color;
      ItemStack bubbleStack = ((Item)ModArmors.CELESTIAL_DRAGON_BUBBLE.get()).m_7968_();
      IMultiChannelColorItem.dyeArmor(bubbleStack, 0, this.hairColor.getRGB());
      this.m_8061_(EquipmentSlot.HEAD, bubbleStack);
      this.mountSlave();
      return spawnData;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (!super.m_6469_(source, amount)) {
         return false;
      } else {
         Entity attacker = source.m_7640_();
         if (attacker != null && attacker instanceof Player) {
            Player player = (Player)attacker;
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props == null) {
               return true;
            }

            if (!this.gotHitOnce) {
               props.alterBounty(10000L, StatChangeSource.KILL_NPC);
               this.gotHitOnce = true;
               if (props.isMarine() || props.isBountyHunter()) {
                  props.alterLoyalty((double)-100.0F, StatChangeSource.KILL_NPC);
               }

               if (EntitySelector.f_20406_.test(player)) {
                  EventsWorldData.get().addSpecialBusterCall((ServerLevel)this.m_9236_(), player);
               }
            }

            double knockbackDistance = (double)0.5F;
            double knockbackHeight = (double)0.0F;
            if (HakiHelper.hasHardeningActive(player, false, false)) {
               knockbackDistance += 0.2;
            }

            if (HakiHelper.hasAdvancedBusoActive(player)) {
               ++knockbackDistance;
            }

            if (HakiHelper.hasInfusionActive(player)) {
               ++knockbackDistance;
            }

            if (knockbackDistance > (double)1.0F) {
               knockbackHeight = (double)0.5F;
               this.unmountSlave(player);
               player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.IMPACT_FRAME.get(), 2, 0));
            }

            Vec3 push = player.m_20154_().m_82520_((double)0.0F, knockbackHeight, (double)0.0F).m_82542_(knockbackDistance, (double)1.0F, knockbackDistance);
            AbilityHelper.setDeltaMovement(this, push);
         }

         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
            this.ridingSlave = true;
            break;
         case 101:
            this.f_20919_ = 0;
            this.ridingSlave = false;
      }

      super.m_7822_(id);
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("hitOnce", this.gotHitOnce);
      nbt.m_128379_("ridingSlave", this.ridingSlave);
      nbt.m_128405_("hairColor", this.hairColor.getRGB());
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.gotHitOnce = nbt.m_128471_("hitOnce");
      this.ridingSlave = nbt.m_128471_("ridingSlave");
      this.hairColor = new Color(nbt.m_128451_("hairColor"));
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeInt(this.hairColor.getRGB());
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.hairColor = new Color(buffer.readInt());
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      if (!this.ridingSlave) {
         this.m_9236_().m_7605_(this, (byte)101);
      }

   }

   public void m_6667_(DamageSource cause) {
      LivingEntity attacker = this.m_21232_();
      if (attacker != null && attacker.m_6084_() && this.ridingSlave) {
         this.unmountSlave(attacker);
      }

      if (!this.secondChance && this.m_21223_() <= 0.0F) {
         this.m_21153_(1.0F);
         this.f_20919_ = 0;
         this.secondChance = true;
      } else {
         super.m_6667_(cause);
      }
   }

   public void mountSlave() {
      this.ridingSlave = true;
      this.m_9236_().m_7605_(this, (byte)100);
   }

   public void unmountSlave(LivingEntity attacker) {
      if (this.ridingSlave) {
         this.ridingSlave = false;
         this.m_9236_().m_7605_(this, (byte)101);
         this.spawnFreedSlave(attacker);
      }
   }

   private void spawnFreedSlave(LivingEntity attacker) {
      Villager slave = new Villager(EntityType.f_20492_, this.m_9236_());
      slave.m_20219_(this.m_20182_());
      slave.f_21345_.m_148105_().clear();
      int xOffset = 50 + this.f_19796_.m_188503_(50);
      if (this.f_19796_.m_188499_()) {
         xOffset *= -1;
      }

      int zOffset = 50 + this.f_19796_.m_188503_(50);
      if (this.f_19796_.m_188499_()) {
         zOffset *= -1;
      }

      Vec3 runLocation = this.m_20182_().m_82520_((double)xOffset, (double)0.0F, (double)zOffset);
      slave.f_21345_.m_25352_(0, new RunTowardsLocationGoal(slave, (double)1.5F, runLocation));
      slave.f_21345_.m_25352_(0, new DespawnAfterTimeGoal(slave, 40));
      this.m_9236_().m_7967_(slave);
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(attacker).orElse((Object)null);
      if (props != null) {
         props.addFreedSlaves(1);
         if (props.isRevolutionary()) {
            props.alterLoyalty((double)0.5F, StatChangeSource.KILL_NPC);
         }
      }

   }

   public boolean isRidingSlave() {
      return this.ridingSlave;
   }

   public Color getHairColor() {
      return this.hairColor;
   }

   public boolean m_6785_(double distance) {
      return distance > (double)1024.0F;
   }
}
