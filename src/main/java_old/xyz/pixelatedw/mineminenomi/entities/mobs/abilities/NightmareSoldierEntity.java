package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.entities.IRandomizedTexture;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class NightmareSoldierEntity extends CloneEntity implements IRandomizedTexture {
   private static final int RESPAWN_TIMER = 600;
   private boolean respawning;
   private int respawnTimer;
   private boolean canDieTick;
   private ResourceLocation currentTexture;

   public NightmareSoldierEntity(EntityType<NightmareSoldierEntity> type, Level world) {
      super(type, world);
   }

   public NightmareSoldierEntity(Level world, LivingEntity owner) {
      super((EntityType)ModMobs.NIGHTMARE_SOLDIER.get(), world, owner);
      if (world != null && !world.f_46443_) {
         this.m_6858_(false);
         this.setMaxAliveTicks(-1);
         int id = this.f_19796_.m_188503_(MobsHelper.NIGHTMARE_SOLDIER_TEXTURES.length);
         this.currentTexture = MobsHelper.NIGHTMARE_SOLDIER_TEXTURES[id];
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22276_, (double)100.0F).m_22268_(Attributes.f_22278_, (double)0.5F).m_22268_(Attributes.f_22284_, (double)0.0F);
   }

   public boolean m_6469_(DamageSource damageSource, float damageValue) {
      if (damageSource.m_269533_(DamageTypeTags.f_268738_)) {
         this.canDieTick = true;
         return super.m_6469_(damageSource, damageValue);
      } else {
         if (damageSource.m_7640_() != null) {
            if (damageSource.m_7640_().m_6095_().m_204039_(ModTags.Entities.KAIROSEKI)) {
               this.canDieTick = true;
               this.m_6469_(ModDamageSources.getInstance().devilsCurse(), this.m_21233_());
               return false;
            }

            if (this.respawning && damageSource.m_7640_() instanceof Player) {
               return false;
            }
         }

         if (damageSource.m_7639_() != null) {
            if (damageSource instanceof AbilityDamageSource && ((AbilityDamageSource)damageSource).getElement() == SourceElement.WATER) {
               this.canDieTick = true;
               this.m_6469_(ModDamageSources.getInstance().devilsCurse(), this.m_21233_());
               return false;
            }

            if (damageSource.m_7639_() instanceof Player && damageSource.m_7639_() == this.getOwner()) {
               return false;
            }
         }

         return super.m_6469_(damageSource, damageValue);
      }
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         if (this.getOwner() == null || !this.getOwner().m_6084_()) {
            this.m_146870_();
            return;
         }

         if (this.canDieTick) {
            this.m_6469_(ModDamageSources.getInstance().devilsCurse(), this.m_21233_());
         } else if (!this.canDieTick && this.m_21223_() <= 0.0F) {
            this.m_21153_(1.0F);
            this.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 600, 0));
            this.respawning = true;
            this.respawnTimer = 600;
         }

         if (this.respawning) {
            if (this.respawnTimer > 0) {
               --this.respawnTimer;
            } else {
               this.m_21195_((MobEffect)ModEffects.UNCONSCIOUS.get());
               this.m_21153_(this.m_21233_());
               this.respawning = false;
            }
         }

         if (this.m_6084_() && (this.getFluidTypeHeight((FluidType)ForgeMod.WATER_TYPE.get()) > 0.9 || this.m_21023_((MobEffect)ModEffects.WET.get()))) {
            this.m_6469_(ModDamageSources.getInstance().devilsCurse(), this.m_21233_());
         }
      }

      super.m_8119_();
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      int len = this.currentTexture.toString().length();
      buffer.writeInt(len);
      buffer.m_130072_(this.currentTexture.toString(), len);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      int len = buffer.readInt();
      this.currentTexture = ResourceLocation.parse(buffer.m_130136_(len));
   }

   public void setOwner(LivingEntity owner) {
      super.setOwner(owner);
   }

   public ResourceLocation getTexture() {
      return this.currentTexture == null ? MobsHelper.NIGHTMARE_SOLDIER_TEXTURES[0] : this.currentTexture;
   }
}
