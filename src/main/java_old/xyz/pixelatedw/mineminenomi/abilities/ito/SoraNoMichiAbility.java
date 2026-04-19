package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SoraNoMichiAbility extends Ability {
   private static final int JUMPS = 12;
   private static final float SHORT_COOLDOWN_PER_STACK = 10.0F;
   private static final float LONG_COOLDOWN_PER_STACK = 40.0F;
   public static final RegistryObject<AbilityCore<SoraNoMichiAbility>> INSTANCE = ModRegistry.registerAbility("sora_no_michi", "Sora no Michi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user attaches the strings to clouds, allowing them to move through the air", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SoraNoMichiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, StackComponent.getTooltip(12)).build("mineminenomi");
   });
   private final PoolComponent poolComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final StackComponent stackComponent;
   private boolean hasFallDamage;

   public SoraNoMichiAbility(AbilityCore<SoraNoMichiAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GEPPO_LIKE, new AbilityPool[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
      this.stackComponent = new StackComponent(this);
      this.hasFallDamage = true;
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.damageTakenComponent, this.stackComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(this::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setDefaultStacks(this.getMaxJumps(entity));
      if (!entity.m_9236_().f_46443_) {
         entity.m_21011_(InteractionHand.MAIN_HAND, true);
      }

      int stacksUsed = 1;
      if (entity.m_20096_()) {
         Vec3 speed = entity.m_20154_().m_82541_().m_82542_(1.1, (double)1.0F, 1.1);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 2.4, speed.f_82481_);
      } else {
         Vec3 speed = entity.m_20154_().m_82541_().m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 0.8, speed.f_82481_);
      }

      this.stackComponent.addStacks(entity, this, -stacksUsed);
      this.hasFallDamage = false;
      if (this.stackComponent.getStacks() <= 0) {
         this.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
         this.stackComponent.setStacks(entity, this, this.getMaxJumps(entity));
      } else {
         this.cooldownComponent.startCooldown(entity, 10.0F);
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

      this.stackComponent.setStacks(entity, this, this.getMaxJumps(entity));
      this.stackComponent.setDefaultStacks(this.getMaxJumps(entity));
      this.hasFallDamage = true;
   }

   private int getMaxJumps(LivingEntity entity) {
      return 12;
   }

   private float getCooldownTicks() {
      return (float)(this.stackComponent.getDefaultStacks() - this.stackComponent.getStacks()) * 40.0F;
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
   }

   private Result canUse(LivingEntity entity, IAbility ability) {
      return entity.m_20186_() > (double)192.0F ? Result.fail((Component)null) : Result.success();
   }
}
