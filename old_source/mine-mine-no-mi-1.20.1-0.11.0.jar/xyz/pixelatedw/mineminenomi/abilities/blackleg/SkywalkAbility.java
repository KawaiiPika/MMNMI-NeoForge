package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SkywalkAbility extends Ability {
   private static final int MAX_JUMPS = 6;
   private static final float SHORT_COOLDOWN_PER_STACK = 10.0F;
   private static final float LONG_COOLDOWN_PER_STACK = 50.0F;
   public static final RegistryObject<AbilityCore<SkywalkAbility>> INSTANCE = ModRegistry.registerAbility("skywalk", "Skywalk", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to kick the air beneath them and launch themselves into the air", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, SkywalkAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).setNodeFactories(SkywalkAbility::createNode).build("mineminenomi");
   });
   private final PoolComponent poolComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final StackComponent stackComponent;
   private final AnimationComponent animationComponent;
   private boolean hasFallDamage;
   private boolean hadGravity;
   private int noGravityTime;

   public SkywalkAbility(AbilityCore<SkywalkAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GEPPO_LIKE, new AbilityPool[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
      this.stackComponent = new StackComponent(this, 6);
      this.animationComponent = new AnimationComponent(this);
      this.hasFallDamage = true;
      this.hadGravity = false;
      this.noGravityTime = 8;
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.damageTakenComponent, this.stackComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      int stacksUsed = 1;
      if (this.noGravityTime <= 0) {
         this.hadGravity = !entity.m_20068_();
      }

      Vec3 movement = entity.m_20154_().m_82541_();
      if (entity.m_20069_()) {
         movement = movement.m_82490_((double)2.5F);
         stacksUsed = this.stackComponent.getStacks();
      } else {
         movement = movement.m_82490_(entity.m_20096_() ? (double)2.0F : (double)1.5F);
      }

      entity.m_20242_(true);
      this.noGravityTime = 8;
      if (entity.m_146909_() < -40.0F) {
         movement = movement.m_82520_((double)0.0F, -(movement.f_82480_ - movement.f_82480_ / (double)2.0F), (double)0.0F);
      }

      AbilityHelper.setDeltaMovement(entity, movement.f_82479_, movement.f_82480_, movement.f_82481_);
      this.stackComponent.addStacks(entity, this, -stacksUsed);
      this.hasFallDamage = false;
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GEPPO_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.75F + this.random.nextFloat() / 3.0F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEPPO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.animationComponent.start(entity, ModAnimations.GEPPO, 7);
      if (this.stackComponent.getStacks() <= 0) {
         super.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
         this.stackComponent.setStacks(entity, this, 6);
      } else {
         this.cooldownComponent.startCooldown(entity, 10.0F);
      }

   }

   public void tickEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && !this.hasFallDamage && this.stackComponent.getStacks() < this.stackComponent.getDefaultStacks() && entity.m_20096_() && entity.m_9236_().m_46467_() > this.getLastUseGametime() + 10L) {
         this.resetStacks(entity);
      }

      if (!entity.m_9236_().f_46443_ && this.noGravityTime-- <= 0 && this.hadGravity) {
         entity.m_20242_(false);
      }

   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.resetStacks(entity);
         return 0.0F;
      } else {
         return damage;
      }
   }

   private void resetStacks(LivingEntity entity) {
      if (this.stackComponent.getStacks() != this.stackComponent.getDefaultStacks()) {
         this.cooldownComponent.stopCooldown(entity);
         this.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
      }

      this.stackComponent.setStacks(entity, this, 6);
      this.hasFallDamage = true;
   }

   private float getCooldownTicks() {
      return (float)(this.stackComponent.getDefaultStacks() - this.stackComponent.getStacks()) * 50.0F;
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
      nbt.m_128379_("hadGravity", this.hadGravity);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
      this.hadGravity = nbt.m_128471_("hadGravity");
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-12.0F, 12.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ConcasseAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
