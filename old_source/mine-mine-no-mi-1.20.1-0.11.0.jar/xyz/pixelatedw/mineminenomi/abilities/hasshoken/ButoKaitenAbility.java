package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hasshoken.ButoKaitenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class ButoKaitenAbility extends Ability {
   private static final float HOLD_TIME = 100.0F;
   private static final float COOLDOWN = 200.0F;
   private static final float AOE_DAMAGE = 10.0F;
   private static final float PROJECTILE_DAMAGE = 25.0F;
   public static final RegistryObject<AbilityCore<ButoKaitenAbility>> INSTANCE = ModRegistry.registerAbility("buto_kaiten", "Buto Kaiten", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user plants their head into the ground and does a head stand, put their hands together and legs apart, and spins like a top.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ButoKaitenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(100.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).setNodeFactories(ButoKaitenAbility::createNodeDefault, ButoKaitenAbility::createNodeRanbu).setUnlockCheck(ButoKaitenAbility::canUnlock).build("mineminenomi");
   });
   private static final int DEFAULT_DORIKI = 7500;
   private static final int RANBU_DORIKI = 8750;
   private static final int MARTIAL_ARTS_POINTS = 50;
   private static final Component BUTO_KAITEN_RANBU_NAME;
   private static final ResourceLocation BUTO_KAITEN_ICON;
   private static final ResourceLocation BUTO_KAITEN_RANBU_ICON;
   private final AltModeComponent<Mode> altModeComponent;
   private final AnimationComponent animationComponent;
   private final ContinuousComponent continuousComponent;
   private final RangeComponent rangeComponent;
   private final ProjectileComponent projectileComponent;
   private final ExplosionComponent explosionComponent;
   private final DealDamageComponent dealDamageComponent;
   private final Interval soundInterval;
   private final Interval kickInterval;
   private Vec3 direction;

   public ButoKaitenAbility(AbilityCore<ButoKaitenAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, ButoKaitenAbility.Mode.DEFAULT)).addChangeModeEvent(this::onAltModeChange);
      this.animationComponent = new AnimationComponent(this);
      this.continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
      this.rangeComponent = new RangeComponent(this);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.explosionComponent = new ExplosionComponent(this);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.soundInterval = new Interval(5);
      this.kickInterval = new Interval(20);
      this.direction = Vec3.f_82478_;
      super.addComponents(this.altModeComponent, this.animationComponent, this.continuousComponent, this.rangeComponent, this.projectileComponent, this.explosionComponent, this.dealDamageComponent);
      super.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      super.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      super.addContinueUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.direction = entity.m_20154_();
      this.animationComponent.start(entity, ModAnimations.HEAD_STAND_SPIN);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (!level.f_46443_ && this.soundInterval.canTick()) {
         level.m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
      }

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, entity.m_20205_() / 2.0F + 4.0F)) {
         if (this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
            AbilityHelper.setDeltaMovement(target, target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_((double)2.0F).m_82520_((double)0.0F, (double)0.5F, (double)0.0F));
         }
      }

      if (this.kickInterval.canTick()) {
         this.projectileComponent.shoot(entity);
      }

      if (this.altModeComponent.getCurrentMode() == ButoKaitenAbility.Mode.RANBU) {
         this.direction = entity.m_20154_();
      }

      Vec3 velocity = this.direction.m_82541_().m_82490_(0.2);
      AbilityHelper.setDeltaMovement(entity, new Vec3(velocity.f_82479_, entity.m_20184_().f_82480_, velocity.f_82481_), true);
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private ButoKaitenProjectile createProjectile(LivingEntity entity) {
      ButoKaitenProjectile proj = new ButoKaitenProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(25.0F);
      return proj;
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == ButoKaitenAbility.Mode.RANBU && !ability.getCore().getNode(entity, 1).isUnlocked(entity)) {
         return false;
      } else {
         if (mode == ButoKaitenAbility.Mode.DEFAULT) {
            super.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
            super.setDisplayIcon(BUTO_KAITEN_ICON);
         } else if (mode == ButoKaitenAbility.Mode.RANBU) {
            super.setDisplayName(BUTO_KAITEN_RANBU_NAME);
            super.setDisplayIcon(BUTO_KAITEN_RANBU_ICON);
         }

         return true;
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128347_("xDirection", this.direction.f_82479_);
      nbt.m_128347_("zDirection", this.direction.f_82481_);
   }

   public void loadAdditional(CompoundTag nbt) {
      double xDirection = nbt.m_128459_("xDirection");
      double zDirection = nbt.m_128459_("zDirection");
      this.direction = new Vec3(xDirection, (double)0.0F, zDirection);
   }

   private static AbilityNode createNodeDefault(LivingEntity entity) {
      AbilityNode butoKaiten = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), BUTO_KAITEN_ICON, new AbilityNode.NodePos(-15.0F, 0.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)7500.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      butoKaiten.addPrerequisites(((AbilityCore)HasshokenPassiveBonusesAbility.INSTANCE.get()).getNode(entity));
      butoKaiten.setUnlockRule(unlockCondition, unlockAction);
      return butoKaiten;
   }

   private static AbilityNode createNodeRanbu(LivingEntity entity) {
      AbilityNode butoKaitenRanbu = new AbilityNode(BUTO_KAITEN_RANBU_NAME, BUTO_KAITEN_RANBU_ICON, new AbilityNode.NodePos(-15.0F, 3.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)8750.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50);
      butoKaitenRanbu.addPrerequisites(((AbilityCore)INSTANCE.get()).getNode(entity));
      butoKaitenRanbu.setUnlockRule(unlockCondition, unlockAction);
      return butoKaitenRanbu;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return ((AbilityCore)INSTANCE.get()).getNode(entity).isUnlocked(entity);
   }

   static {
      BUTO_KAITEN_RANBU_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "buto_kaiten_ranbu", "Buto Kaiten: Ranbu"));
      BUTO_KAITEN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/buto_kaiten.png");
      BUTO_KAITEN_RANBU_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/buto_kaiten_ranbu.png");
   }

   private static enum Mode {
      DEFAULT,
      RANBU;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{DEFAULT, RANBU};
      }
   }
}
