package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KamisoriAbility extends Ability {
   private static final float HOLD_TIME = 40.0F;
   private static final float COOLDOWN = 300.0F;
   private static final int DORIKI = 1750;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<KamisoriAbility>> INSTANCE = ModRegistry.registerAbility("kamisori", "Kamisori", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A combination of Geppo and Soru, where the user uses Soru in a zigzag motion in midair, allowing extremely fast movements in three dimensions.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, KamisoriAbility::new)).addDescriptionLine(desc).setNodeFactories(KamisoriAbility::createNode).build("mineminenomi");
   });
   private final PoolComponent poolComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final ContinuousComponent continuousComponent;
   private final Interval zigzagInterval;
   private boolean hasFallDamage;
   private boolean isZig;

   public KamisoriAbility(AbilityCore<KamisoriAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GEPPO_LIKE, new AbilityPool[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
      this.continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
      this.zigzagInterval = Interval.startAtMax(4);
      this.hasFallDamage = true;
      this.isZig = true;
      super.addComponents(this.poolComponent, this.damageTakenComponent, this.continuousComponent);
      super.setOGCD();
      super.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      super.addUseEvent(this::onUseEvent);
      super.addTickEvent(this::onTickEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.hasFallDamage = false;
      this.isZig = true;
      this.zigzagInterval.restartIntervalToMax();
      this.continuousComponent.triggerContinuity(entity, 40.0F);
   }

   public void onTickEvent(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (!level.f_46443_ && !this.hasFallDamage && entity.m_20096_() && !this.continuousComponent.isContinuous() && level.m_46467_() > super.getLastUseGametime() + 10L) {
         this.hasFallDamage = true;
      }

   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.zigzagInterval.canTick()) {
         Vec3 velocity = VectorHelper.getRelativeOffset(Vec3.f_82478_, entity.m_146908_(), entity.m_146909_(), new Vec3((double)0.0F, this.isZig ? (double)1.0F : (double)-1.0F, (double)1.0F));
         AbilityHelper.setDeltaMovement(entity, velocity, true);
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), this.zigzagInterval.getInterval(), 0, false, false));
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GEPPO_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.75F + super.random.nextFloat() / 3.0F);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEPPO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         this.isZig = !this.isZig;
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if ((!this.hasFallDamage || this.continuousComponent.isContinuous()) && damageSource == entity.m_269291_().m_268989_()) {
         this.continuousComponent.stopContinuity(entity);
         return 0.0F;
      } else {
         return damage;
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode kamisori = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(6.0F, -6.0F));
      kamisori.addPrerequisites(((AbilityCore)GeppoAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)SoruAbility.INSTANCE.get()).getNode(entity));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)1750.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      kamisori.setUnlockRule(unlockCondition, unlockAction);
      return kamisori;
   }
}
