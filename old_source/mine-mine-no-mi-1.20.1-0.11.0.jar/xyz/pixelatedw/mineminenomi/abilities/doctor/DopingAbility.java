package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DopingAbility extends PunchAbility {
   private static final int COOLDOWN = 500;
   private static final int EFFECT_TIME = 200;
   public static final RegistryObject<AbilityCore<DopingAbility>> INSTANCE = ModRegistry.registerAbility("doping", "Doping", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Injects the target with special medicine that boosts their physical powers.", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: User injects themselves", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DopingAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.FIST, SourceType.FRIENDLY).setNodeFactories(DopingAbility::createNode).build("mineminenomi");
   });

   public DopingAbility(AbilityCore<DopingAbility> core) {
      super(core);
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         this.applyEffect(entity, entity);
      }
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.applyEffect(entity, target);
      return true;
   }

   private void applyEffect(LivingEntity entity, LivingEntity target) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.TENSION_HORMONE.get(), 200, 1));
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FIRST_AID.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      this.continuousComponent.stopContinuity(entity);
   }

   public float getPunchCooldown() {
      return 500.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-15.0F, -2.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
