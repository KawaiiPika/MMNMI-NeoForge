package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DamageAbsorptionAbility extends Ability {
   private static final UUID DAMAGE_BONUS_UUID = UUID.fromString("9f88f925-1627-4ae8-98e0-6e45c588d70b");
   private static final float HOLD_TIME = 100.0F;
   private static final float COOLDOWN = 300.0F;
   private static final float RANGE = 10.0F;
   public static final RegistryObject<AbilityCore<DamageAbsorptionAbility>> INSTANCE = ModRegistry.registerAbility("damage_absorption", "Damage Absorption", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to absorb all the incoming damage granting a damage buff at the end based on the total damage received.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DamageAbsorptionAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::duringContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private float prevHealth;
   private int hits;
   private float receivedDamage;

   public DamageAbsorptionAbility(AbilityCore<DamageAbsorptionAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      AttributeInstance attr = entity.m_21051_(Attributes.f_22281_);
      if (attr != null) {
         attr.m_22120_(DAMAGE_BONUS_UUID);
      }

      this.hits = 0;
      this.prevHealth = entity.m_21223_();
      this.receivedDamage = 0.0F;
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 20, 0));
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.f_19797_ % 10 == 0) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 20, 0));
      }

      if (entity.m_21223_() < this.prevHealth) {
         ++this.hits;
         this.receivedDamage += this.prevHealth - entity.m_21223_();
         this.prevHealth = entity.m_21223_();
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F)) {
         Vec3 dirVec = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)3.5F, (double)1.0F, (double)3.5F);
         AbilityHelper.setDeltaMovement(target, dirVec.f_82479_, (double)0.0F, dirVec.f_82481_);
      }

      if (!entity.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BERSERKER_POWERUP.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      AttributeInstance attr = entity.m_21051_(Attributes.f_22281_);
      if (attr != null) {
         attr.m_22120_(DAMAGE_BONUS_UUID);
         attr.m_22118_(this.getModifier());
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private AttributeModifier getModifier() {
      this.receivedDamage = Math.min(this.receivedDamage, 30.0F) / 5.0F;
      return new AttributeModifier(DAMAGE_BONUS_UUID, "Damage Absorption Bonus", (double)this.receivedDamage, Operation.ADDITION);
   }

   public int getHits() {
      return this.hits;
   }

   public float getReceivedDamage() {
      return this.receivedDamage;
   }
}
