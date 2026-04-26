package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class OTatsumakiAbility extends Ability {
   private static final int HOLD_TIME = 60;
   private static final float COOLDOWN = 240.0F;
   private static final float DAMAGE = 30.0F;
   private static final float RANGE = 5.5F;
   private static final int DORIKI = 5000;
   private static final int WEAPON_MASTERY_POINTS = 20;
   private final Interval damageInterval = new Interval(15);
   public static final RegistryObject<AbilityCore<OTatsumakiAbility>> INSTANCE = ModRegistry.registerAbility("o_tatsumaki", "O Tatsumaki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By spinning, the user creates a small tornado, which slashes and weakens nearby opponents", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, OTatsumakiAbility::new)).setIcon(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/tatsu_maki.png")).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(30.0F), RangeComponent.getTooltip(5.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setNodeFactories(OTatsumakiAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::onStartContinuousEvent).addTickEvent(100, this::onTickContinuousEvent).addEndEvent(100, this::onEndContinuousEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public OTatsumakiAbility(AbilityCore<OTatsumakiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 60.0F);
   }

   private void onStartContinuousEvent(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      stack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      this.damageInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.BODY_ROTATION_WIDE_ARMS);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
   }

   private void onEndContinuousEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private void onTickContinuousEvent(LivingEntity entity, IAbility ability) {
      if (this.damageInterval.canTick()) {
         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 5.5F)) {
            this.dealDamageComponent.hurtTarget(entity, target, 30.0F);
            if (!entity.m_9236_().f_46443_) {
               WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
            }
         }
      }

      entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 5, 1, false, false));
      if (!entity.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TATSU_MAKI.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         if (this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
         }
      }

   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode tatsuMaki = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-5.0F, -3.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      tatsuMaki.setUnlockRule(unlockCondition, unlockAction);
      return tatsuMaki;
   }
}
