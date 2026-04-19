package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ito.KumoNoSugakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.OverheatAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.TamaitoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BlackKnightEntity extends CloneEntity {
   private static final UUID OVERHEAT_COOLDOWN_MODIFIER_UUID = UUID.fromString("1c155dbe-ddc4-4a0c-a189-eb617e0ad55b");
   private static final UUID TAMAITO_COOLDOWN_MODIFIER_UUID = UUID.fromString("01afb328-256f-46d6-a240-ddb1112466a5");

   public BlackKnightEntity(EntityType<BlackKnightEntity> type, Level world) {
      super(type, world);
   }

   public BlackKnightEntity(Level world, LivingEntity owner) {
      super((EntityType)ModMobs.BLACK_KNIGHT.get(), world, owner);
      if (world != null && !world.f_46443_) {
         this.copyOwnerEquipment();
         this.setUseOwnerTexture();
         this.m_6858_(false);
         this.setMaxAliveTicks(-1);
         this.disablePermaPanic();
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)10.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)2.0F);
         ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
         ProjectileAbilityWrapperGoal<OverheatAbility> overheatWrapper = new ProjectileAbilityWrapperGoal<OverheatAbility>(this, (AbilityCore)OverheatAbility.INSTANCE.get());
         ((CooldownComponent)((OverheatAbility)overheatWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).get()).getBonusManager().addBonus(OVERHEAT_COOLDOWN_MODIFIER_UUID, "Overheat Cooldown Modifier", BonusOperation.MUL, 2.0F);
         ProjectileAbilityWrapperGoal<TamaitoAbility> tamaitoWrapper = new ProjectileAbilityWrapperGoal<TamaitoAbility>(this, (AbilityCore)TamaitoAbility.INSTANCE.get());
         ((CooldownComponent)((TamaitoAbility)tamaitoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).get()).getBonusManager().addBonus(TAMAITO_COOLDOWN_MODIFIER_UUID, "Tamaito Cooldown Modifier", BonusOperation.MUL, 2.0F);
         this.f_21345_.m_25352_(1, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KumoNoSugakiAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, overheatWrapper);
         this.f_21345_.m_25352_(2, tamaitoWrapper);
         AbilityHelper.forceCooldownOnAllAbilities(this, 40);
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new OpenDoorGoal(this, false));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)10.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22278_, (double)1.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      Level level = super.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.f_19797_ % 20 == 0) {
            this.copyOwnerEquipment();
         }

      }
   }

   protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
   }

   public void setOwner(LivingEntity owner) {
      super.setOwner(owner);
      this.unlockHakiOfOwner(owner);
   }
}
