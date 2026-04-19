package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.hana.CampoDeFloresAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CampoDeFloresEntity extends Entity implements TraceableEntity, IEntityAdditionalSpawnData {
   private LivingEntity owner;
   private CampoDeFloresAbility ability;
   private static final TargetPredicate TARGETS_PREDICATE = (new TargetPredicate()).testEnemyFaction().selector((entity) -> AbilityHelper.getDifferenceToFloor(entity) < (double)3.0F);

   public CampoDeFloresEntity(EntityType<? extends CampoDeFloresEntity> type, Level pLevel) {
      super(type, pLevel);
   }

   public CampoDeFloresEntity(Level level, LivingEntity owner, CampoDeFloresAbility ability) {
      super((EntityType)ModProjectiles.CAMPO_DE_FLORES.get(), level);
      this.owner = owner;
      this.ability = ability;
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      super.m_8119_();
      Level level = super.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.f_19797_ > 100) {
            this.m_146870_();
         } else if (this.owner != null && this.owner.m_6084_()) {
            List<LivingEntity> targets = (List)this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.RANGE.get()).map((comp) -> comp.getTargetsInArea(this.owner, this.m_20183_(), 10.0F, TARGETS_PREDICATE)).orElse(new ArrayList());
            DealDamageComponent damageComp = (DealDamageComponent)this.ability.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).orElse((Object)null);
            if (damageComp != null) {
               for(LivingEntity target : targets) {
                  if (damageComp.hurtTarget(this.owner, target, 10.0F)) {
                     target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 20, 0));
                     AbilityHelper.setDeltaMovement(target, (double)0.0F, (double)1.75F, (double)0.0F);
                  }
               }

            }
         } else {
            this.m_146870_();
         }
      }
   }

   public EntityDimensions m_6972_(Pose pose) {
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20390_(20.0F, 1.0F);
      return newSize;
   }

   protected void m_7378_(CompoundTag pCompound) {
   }

   protected void m_7380_(CompoundTag pCompound) {
   }

   @Nullable
   public LivingEntity getOwner() {
      return this.owner;
   }

   public RandomSource getRandom() {
      return this.f_19796_;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.owner != null;
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeInt(this.owner.m_19879_());
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.owner = owner;
         }
      }

   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
