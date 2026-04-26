package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.santoryu.TatsuMakiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TatsuMakiAbility extends Ability {
   private static final float CHARGE_TIME = 20.0F;
   private static final float COOLDOWN = 240.0F;
   public static final float RANGE = 5.5F;
   private static final int DORIKI = 5000;
   private static final int WEAPON_MASTERY_POINTS = 20;
   private final Interval soundInterval = new Interval(5);
   public static final RegistryObject<AbilityCore<TatsuMakiAbility>> INSTANCE = ModRegistry.registerAbility("tatsu_maki", "Tatsu Maki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By spinning, the user creates a small tornado, which slashes and weakens nearby opponents", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, TatsuMakiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT, SourceType.SLASH).setSourceElement(SourceElement.AIR).setNodeFactories(TatsuMakiAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public TatsuMakiAbility(AbilityCore<TatsuMakiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.soundInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.BODY_ROTATION_WIDE_ARMS);
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (!level.f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TATSU_MAKI.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         if (this.soundInterval.canTick()) {
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + super.random.nextFloat() / 4.0F);
         }

      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      TatsuMakiProjectile proj = (TatsuMakiProjectile)this.projectileComponent.getNewProjectile(entity);
      this.projectileComponent.shoot(proj, entity, -90.0F, 0.0F, 2.0F, 0.0F);
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private TatsuMakiProjectile createProjectile(LivingEntity entity) {
      SourceType type = AbilityUseConditions.requiresSword(entity, this).isSuccess() ? SourceType.SLASH : SourceType.BLUNT;
      if (type == SourceType.SLASH) {
         ItemStack stack = entity.m_21205_();
         stack.m_41622_(1, entity, (e) -> e.m_21166_(EquipmentSlot.MAINHAND));
      }

      TatsuMakiProjectile proj = new TatsuMakiProjectile(entity.m_9236_(), entity, this, type);
      proj.m_20219_(entity.m_20182_());
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode tatsuMaki = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-5.0F, -3.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SWORDSMAN.get()).and(DorikiUnlockCondition.atLeast((double)5000.0F)).and(TrainingPointsUnlockCondition.weaponMastery((double)20.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.weaponMastery(20).andThen(UnlockAbilityAction.unlock(INSTANCE));
      tatsuMaki.setUnlockRule(unlockCondition, unlockAction);
      return tatsuMaki;
   }
}
