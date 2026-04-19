package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GeppoAbility extends Ability {
   private static final int MIN_JUMPS = 3;
   private static final int MAX_JUMPS = 6;
   private static final float SHORT_COOLDOWN_PER_STACK = 10.0F;
   private static final float LONG_COOLDOWN_PER_STACK = 50.0F;
   private static final int DORIKI = 1250;
   private static final int MARTIAL_ARTS_POINTS = 50;
   private static final AbilityDescriptionLine.IDescriptionLine GEPPO_STACKS = (e, a) -> {
      if (a instanceof GeppoAbility geppo) {
         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_STACKS, geppo.getMaxJumps(e), geppo.getMaxJumps(e));
         return statBuilder.build().getStatDescription();
      } else {
         return null;
      }
   };
   public static final RegistryObject<AbilityCore<GeppoAbility>> INSTANCE = ModRegistry.registerAbility("geppo", "Geppo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user kicks the air beneath them to launch themselves into the air", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, GeppoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).setNodeFactories(GeppoAbility::createNode).build("mineminenomi");
   });
   private final PoolComponent poolComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final StackComponent stackComponent;
   private final AnimationComponent animationComponent;
   private boolean hasFallDamage;

   public GeppoAbility(AbilityCore<GeppoAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GEPPO_LIKE, new AbilityPool[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
      this.stackComponent = new StackComponent(this);
      this.animationComponent = new AnimationComponent(this);
      this.hasFallDamage = true;
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.damageTakenComponent, this.stackComponent, this.animationComponent});
      this.setOGCD();
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setDefaultStacks(this.getMaxJumps(entity));
      int stacksUsed = 1;
      Vec3 look = entity.m_20154_();
      Vec3 speed;
      double ySpeed;
      if (entity.m_20069_()) {
         speed = look.m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
         ySpeed = speed.f_82480_;
         stacksUsed = this.stackComponent.getStacks();
      } else {
         if (entity.m_20096_()) {
            speed = look.m_82542_((double)1.0F, (double)1.0F, (double)1.0F);
            ySpeed = 1.86;
         } else {
            speed = look.m_82542_((double)1.5F, (double)1.5F, (double)1.5F);
            ySpeed = (double)1.25F;
         }

         stacksUsed = 1;
      }

      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, ySpeed, speed.f_82481_);
      this.stackComponent.addStacks(entity, this, -stacksUsed);
      this.hasFallDamage = false;
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GEPPO_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.75F + this.random.nextFloat() / 3.0F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEPPO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.animationComponent.start(entity, ModAnimations.GEPPO, 7);
      if (this.stackComponent.getStacks() <= 0) {
         super.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
         this.stackComponent.setStacks(entity, this, this.getMaxJumps(entity));
      } else {
         super.cooldownComponent.startCooldown(entity, 10.0F);
      }

   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource == entity.m_269291_().m_268989_()) {
         this.resetStacks(entity);
         return 0.0F;
      } else {
         return damage;
      }
   }

   public void tickEvent(LivingEntity entity, IAbility ability) {
      if (this.getLastUseGametime() <= 0L && this.stackComponent.getDefaultStacks() <= 0) {
         this.stackComponent.setDefaultStacks(this.getMaxJumps(entity));
         this.stackComponent.revertStacksToDefault(entity, this);
      }

      if (!entity.m_9236_().f_46443_ && !this.hasFallDamage && this.stackComponent.getStacks() < this.stackComponent.getDefaultStacks() && entity.m_20096_() && entity.m_9236_().m_46467_() > this.getLastUseGametime() + 10L) {
         this.resetStacks(entity);
      }

   }

   private void resetStacks(LivingEntity entity) {
      if (this.stackComponent.getStacks() != this.stackComponent.getDefaultStacks()) {
         this.cooldownComponent.stopCooldown(entity);
         this.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
      }

      this.stackComponent.setStacks(entity, this, this.getMaxJumps(entity));
      this.stackComponent.setDefaultStacks(this.getMaxJumps(entity));
      this.hasFallDamage = true;
   }

   private int getMaxJumps(LivingEntity entity) {
      return 6;
   }

   private float getCooldownTicks() {
      return (float)(this.stackComponent.getDefaultStacks() - this.stackComponent.getStacks()) * 50.0F;
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode geppo = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(8.0F, -2.0F));
      NodeUnlockCondition unlockConditions = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)1250.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      geppo.setUnlockRule(unlockConditions, unlockAction);
      return geppo;
   }
}
