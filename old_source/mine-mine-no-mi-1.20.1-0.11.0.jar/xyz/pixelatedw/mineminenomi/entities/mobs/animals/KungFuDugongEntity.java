package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class KungFuDugongEntity extends AbstractDugongEntity {
   public KungFuDugongEntity(EntityType<? extends KungFuDugongEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)1.0F);
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(25 + this.m_217043_().m_188503_(10)));
            props.setKenbunshokuHakiExp((float)(15 + this.m_217043_().m_188503_(10)));
         });
         this.m_21051_(Attributes.f_22276_).m_22100_(this.generateRandomHealth());
         this.m_21051_(Attributes.f_22284_).m_22100_(this.generateRandomArmor());
         this.m_21051_(Attributes.f_22279_).m_22100_(this.generateRandomSpeed());
         this.m_21051_(Attributes.f_22281_).m_22100_(this.generateRandomAttack());
         this.f_21345_.m_25352_(0, new BreedGoal(this, (double)1.0F));
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.1F, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
         MobsHelper.getBasicHakiAbilities(this, 10).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
      }

   }

   public @Nullable SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      if (spawnData == null) {
         spawnData = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      this.m_21051_(Attributes.f_22276_).m_22100_(this.generateRandomHealth());
      this.m_21051_(Attributes.f_22284_).m_22100_(this.generateRandomArmor());
      this.m_21051_(Attributes.f_22281_).m_22100_(this.generateRandomAttack());
      this.m_21051_(Attributes.f_22279_).m_22100_(this.generateRandomSpeed());
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      return super.m_6518_(level, difficulty, reason, spawnData, dataTag);
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      KungFuDugongEntity offspring = new KungFuDugongEntity((EntityType)ModMobs.KUNG_FU_DUGONG.get(), world);
      this.setOffspringAttributes(mate, offspring);
      if (this.hasHakiLearned() && mate instanceof AbstractDugongEntity dugong) {
         if (dugong.hasHakiLearned()) {
            MobsHelper.getBasicHakiAbilities(offspring, 80).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         }
      }

      return offspring;
   }

   protected void setOffspringAttributes(AgeableMob mate, KungFuDugongEntity offspring) {
      double hp = this.m_21172_(Attributes.f_22276_) + mate.m_21172_(Attributes.f_22276_) + this.generateRandomHealth();
      offspring.m_21051_(Attributes.f_22276_).m_22100_(hp / (double)3.0F);
      double armor = this.m_21172_(Attributes.f_22284_) + mate.m_21172_(Attributes.f_22284_) + this.generateRandomArmor();
      offspring.m_21051_(Attributes.f_22284_).m_22100_(armor / (double)3.0F);
      double speed = this.m_21172_(Attributes.f_22279_) + mate.m_21172_(Attributes.f_22279_) + this.generateRandomSpeed();
      offspring.m_21051_(Attributes.f_22279_).m_22100_(speed / (double)3.0F);
      double damage = this.m_21172_(Attributes.f_22281_) + mate.m_21172_(Attributes.f_22281_) + this.generateRandomAttack();
      offspring.m_21051_(Attributes.f_22281_).m_22100_(damage / (double)3.0F);
   }

   protected double generateRandomHealth() {
      return (double)30.0F + this.f_19796_.m_188500_() * (double)20.0F;
   }

   protected double generateRandomArmor() {
      return (double)2.0F + this.f_19796_.m_188500_() * (double)2.0F;
   }

   protected double generateRandomSpeed() {
      return 0.28 + this.f_19796_.m_188500_() * 0.05;
   }

   protected double generateRandomAttack() {
      return (double)5.0F + this.f_19796_.m_188500_() * (double)3.0F;
   }
}
