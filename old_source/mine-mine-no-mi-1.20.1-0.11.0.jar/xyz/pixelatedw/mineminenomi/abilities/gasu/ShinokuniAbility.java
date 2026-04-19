package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShinokuniAbility extends MorphAbility {
   private static final int HOLD_TIME = 600;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<ShinokuniAbility>> INSTANCE = ModRegistry.registerAbility("shinokuni", "Shinokuni", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms into a gas giant releasing an area of effect gas near the user based on whatever %s the user holds when transforming.", new Object[]{Items.f_42589_}), ImmutablePair.of("If %s is active the nearby entities will instead get affected by whatever effect the user has at the moment. Allies will receive benefic effects while enemies will receive negative effects.", new Object[]{INSTANCE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShinokuniAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 300.0F), ContinuousComponent.getTooltip(600.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier HEALTH_BOOST;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   public Potion effect;

   public ShinokuniAbility(AbilityCore<ShinokuniAbility> core) {
      super(core);
      Predicate<LivingEntity> isMorphed = (entity) -> this.morphComponent.isMorphed();
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_BOOST, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphed);
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(this::stopContinuityEvent);
   }

   public void startContinuityEvent(LivingEntity player, IAbility ability) {
      List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyLiving(player.m_20182_(), player.m_9236_(), (double)20.0F, ModEntityPredicates.getEnemyFactions(player));
      this.effect = PotionUtils.m_43579_(player.m_21205_());
      if (!this.effect.equals(Potions.f_43598_)) {
         player.m_21205_().m_41764_(player.m_21205_().m_41613_() - 1);
         list.forEach((target) -> this.applyEffects(player, target));
      }

   }

   public void duringContinuityEvent(LivingEntity player, IAbility ability) {
      if (this.effect != null && !this.effect.equals(Potions.f_43598_)) {
         if (this.continuousComponent.getContinueTime() % 100.0F == 0.0F) {
            for(LivingEntity target : WyHelper.getNearbyLiving(player.m_20182_(), player.m_9236_(), (double)20.0F, ModEntityPredicates.getEnemyFactions(player))) {
               this.applyEffects(player, target);
            }
         }

         if (this.continuousComponent.getContinueTime() % 2.0F == 0.0F) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SHINOKUNI.get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_());
         }

      }
   }

   public void applyEffects(LivingEntity entity, LivingEntity target) {
      for(MobEffectInstance inst : this.effect.m_43488_()) {
         boolean isAlly = ModEntityPredicates.getFriendlyFactions(entity).test(target);
         if (isAlly && inst.m_19544_().m_19486_()) {
            target.m_7292_(new MobEffectInstance(inst.m_19544_(), inst.m_19557_(), inst.m_19564_()));
         }

         if (!isAlly && !inst.m_19544_().m_19486_()) {
            target.m_7292_(new MobEffectInstance(inst.m_19544_(), inst.m_19557_(), inst.m_19564_()));
         }
      }

   }

   protected void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      this.effect = null;
   }

   public float getCooldownTicks() {
      int cooldown = (int)(100L + Math.round((double)this.continuousComponent.getContinueTime() / (double)3.0F));
      return (float)cooldown;
   }

   public float getContinuityHoldTime() {
      return 600.0F;
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.SHINOKUNI.get();
   }

   static {
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Shinokuni Reach Modifier", (double)5.0F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Shinokuni Knockback Resistance Modifier", (double)2.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Shinokuni Toughness Modifier", (double)2.0F, Operation.ADDITION);
      HEALTH_BOOST = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Shinokuni Health Modifier", (double)20.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Shinokuni Strength Modifier", (double)7.0F, Operation.ADDITION);
   }
}
